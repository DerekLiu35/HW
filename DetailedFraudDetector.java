package spendreport;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class DetailedFraudDetector extends KeyedProcessFunction<Long, DetailedTransaction, DetailedAlert> {

    private static final long serialVersionUID = 1L;

    private static final double SMALL_AMOUNT = 1.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    private transient ValueState<Boolean> flagState;
    private transient ValueState<Long> timerState;
    private transient ValueState<String> zipState; // stores zipCode of the last small transaction

    @Override
    public void open(Configuration parameters) {
        flagState = getRuntimeContext().getState(new ValueStateDescriptor<>("flag", Types.BOOLEAN));
        timerState = getRuntimeContext().getState(new ValueStateDescriptor<>("timer-state", Types.LONG));
        zipState = getRuntimeContext().getState(new ValueStateDescriptor<>("zip-state", Types.STRING));
    }

    @Override
    public void processElement(
            DetailedTransaction transaction,
            Context context,
            Collector<DetailedAlert> collector) throws Exception {

        // Get the current state for the current key
        Boolean lastTransactionWasSmall = flagState.value();
        String lastZipCode = zipState.value(); // get zipCode of the last small transaction

        if (lastTransactionWasSmall != null) {
            /* usual method of small then large amount to trigger alert, but also measure whether
               zipCode of the small and then large transaction are the same */
            if (transaction.getAmount() > LARGE_AMOUNT && transaction.getZipCode().equals(lastZipCode)) {
                //Output an alert downstream
                DetailedAlert alert = new DetailedAlert();
                alert.setAccountId(transaction.getAccountId());
                alert.setTimestamp(transaction.getTimestamp());
                alert.setAmount(transaction.getAmount());
                alert.setZipCode(transaction.getZipCode());
                
                System.out.println(alert);

                // collector.collect(alert);
            }
            // Clean up our state
            cleanUp(context);
        }

        if (transaction.getAmount() < SMALL_AMOUNT) {
            // set the flag to true and update ZipCode
            flagState.update(true);
            zipState.update(transaction.getZipCode()); // maybe I should have a set of zipcodes

            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
            context.timerService().registerProcessingTimeTimer(timer);

            timerState.update(timer);
        }
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<DetailedAlert> out) {
        // remove flag after 1 minute
        flagState.clear();
        zipState.clear();
        timerState.clear();
    }

    private void cleanUp(Context ctx) throws Exception {
        // delete timer
        Long timer = timerState.value();
        ctx.timerService().deleteProcessingTimeTimer(timer);

        // clean up all state
        flagState.clear();
        timerState.clear();
        zipState.clear();
    }
}
