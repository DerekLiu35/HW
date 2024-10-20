package spendreport;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.flink.annotation.Public;
import org.apache.flink.streaming.api.functions.source.FromIteratorFunction;

/**
 * A stream of detailed transactions with zip code information instead of regular transactions.
 */
@Public
public class DetailedTransactionSource extends FromIteratorFunction<DetailedTransaction> {
    private static final long serialVersionUID = 1L;

    public DetailedTransactionSource() {
        super(new RateLimitedIterator<>(DetailedTransactionIterator.unbounded()));
    }

    private static class RateLimitedIterator<T> implements Iterator<T>, Serializable {
        private static final long serialVersionUID = 1L;
        private final Iterator<T> inner;

        private RateLimitedIterator(Iterator<T> inner) {
            this.inner = inner;
        }

        public boolean hasNext() { return inner.hasNext(); }

        public T next() {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.inner.next();
        }
    }
}

