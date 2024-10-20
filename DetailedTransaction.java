package spendreport;

import java.util.Objects;

/** A detailed transaction with zip code. */
public final class DetailedTransaction {

    private long accountId;
    private long timestamp;
    private double amount;
    private String zipCode; // include zipcode information

    public DetailedTransaction() {}

    public DetailedTransaction(long accountId, long timestamp, double amount, String zipCode) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.zipCode = zipCode; // include zipcode information
    }

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // create get and set for zipcode
    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            DetailedTransaction that = (DetailedTransaction) o;
            return this.accountId == that.accountId
                    && this.timestamp == that.timestamp
                    && Double.compare(that.amount, this.amount) == 0
                    && this.zipCode.equals(that.zipCode); // include zipcode check
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.accountId, this.timestamp, this.amount, this.zipCode});
    }

    public String toString() {
        return "DetailedTransaction{"
                + "accountId=" + accountId
                + ", timestamp=" + timestamp
                + ", amount=" + amount
                + ", zipCode=" + zipCode // include zipcode
                + '}';
    }
}
