package spendreport;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.flink.walkthrough.common.entity.Transaction;

class DetailedTransactionIterator implements Iterator<DetailedTransaction>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Timestamp INITIAL_TIMESTAMP = Timestamp.valueOf("2019-01-01 00:00:00");
    private static final long SIX_MINUTES = 360000L;
    private final boolean bounded;
    private int index = 0;
    private long timestamp;
    private static List<Transaction> data = Arrays.asList(new Transaction(1L, 0L, 188.23), new Transaction(2L, 0L, 374.79), new Transaction(3L, 0L, 112.15), new Transaction(4L, 0L, 478.75), new Transaction(5L, 0L, 208.85), new Transaction(1L, 0L, 379.64), new Transaction(2L, 0L, 351.44), new Transaction(3L, 0L, 320.75), new Transaction(4L, 0L, 259.42), new Transaction(5L, 0L, 273.44), new Transaction(1L, 0L, 267.25), new Transaction(2L, 0L, 397.15), new Transaction(3L, 0L, 0.219), new Transaction(4L, 0L, 231.94), new Transaction(5L, 0L, 384.73), new Transaction(1L, 0L, 419.62), new Transaction(2L, 0L, 412.91), new Transaction(3L, 0L, 0.77), new Transaction(4L, 0L, 22.1), new Transaction(5L, 0L, 377.54), new Transaction(1L, 0L, 375.44), new Transaction(2L, 0L, 230.18), new Transaction(3L, 0L, 0.8), new Transaction(4L, 0L, 350.89), new Transaction(5L, 0L, 127.55), new Transaction(1L, 0L, 483.91), new Transaction(2L, 0L, 228.22), new Transaction(3L, 0L, 871.15), new Transaction(4L, 0L, 64.19), new Transaction(5L, 0L, 79.43), new Transaction(1L, 0L, 56.12), new Transaction(2L, 0L, 256.48), new Transaction(3L, 0L, 148.16), new Transaction(4L, 0L, 199.95), new Transaction(5L, 0L, 252.37), new Transaction(1L, 0L, 274.73), new Transaction(2L, 0L, 473.54), new Transaction(3L, 0L, 119.92), new Transaction(4L, 0L, 323.59), new Transaction(5L, 0L, 353.16), new Transaction(1L, 0L, 211.9), new Transaction(2L, 0L, 280.93), new Transaction(3L, 0L, 347.89), new Transaction(4L, 0L, 459.86), new Transaction(5L, 0L, 82.31), new Transaction(1L, 0L, 373.26), new Transaction(2L, 0L, 479.83), new Transaction(3L, 0L, 454.25), new Transaction(4L, 0L, 83.64), new Transaction(5L, 0L, 292.44));
    private static final List<String> ZIP_CODES = Arrays.asList("01003", "02115", "78712"); // add list of zipcodes
    private static final Random RANDOM = new Random(); // to sample uniformly random from zip codes

    static DetailedTransactionIterator bounded() {
        return new DetailedTransactionIterator(true);
    }

    static DetailedTransactionIterator unbounded() {
        return new DetailedTransactionIterator(false);
    }

    private DetailedTransactionIterator(boolean bounded) {
        this.bounded = bounded;
        this.timestamp = INITIAL_TIMESTAMP.getTime();
    }

    @Override
    public boolean hasNext() {
        if (this.index < data.size()) {
            return true;
        } else if (!this.bounded) {
            this.index = 0;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DetailedTransaction next() {
        // reusing tutorial transaction data, so extract accountId and amount from each transaction
        Transaction transaction = (Transaction) data.get(this.index++);
        long accountId = transaction.getAccountId();
        double amount = transaction.getAmount();
        // Choose uniformly random from ZIP_CODES, ["01003", "02115", "78712"]
        String zipCode = ZIP_CODES.get(RANDOM.nextInt(ZIP_CODES.size()));

        // wrap accountId, timestamp, amount, and zipCode in a DetailedTransaction
        DetailedTransaction detailedTransaction = new DetailedTransaction(accountId, this.timestamp, amount, zipCode);

        // System.out.println(detailedTransaction);

        this.timestamp += SIX_MINUTES;
        return detailedTransaction;
    }
    /*
    private static List<Transaction> data =
            Arrays.asList(
                    new Transaction(3, 0L, 0.80),
                    new Transaction(3, 0L, 0.80),
                    new Transaction(3, 0L, 871.15));
     */
}

