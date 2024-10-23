package spendreport;

import java.util.Objects;

/** 
 * A detailed transaction class that includes zip code information 
 * along with account id, timestamp, and amount.
 */
public final class DetailedTransaction {

    private long accountId;
    private long timestamp;
    private double amount;
    private String zipCode; // include zipcode information

    // Default constructor
    public DetailedTransaction() {}

    // Constructor to initialize all properties including zip code
    public DetailedTransaction(long accountId, long timestamp, double amount, String zipCode) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.zipCode = zipCode; // include zipcode information
    }

    // Getters and Setters for each property
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

    public String getZipCode() {
        return this.zipCode; // Getter for zip code
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode; // Setter for zip code
    }

    // Override equals method to include zip code comparison
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            DetailedTransaction that = (DetailedTransaction) o;
            return this.accountId == that.accountId
                    && this.timestamp == that.timestamp
                    && Double.compare(that.amount, this.amount) == 0
                    && this.zipCode.equals(that.zipCode); // Include zip code in equality check
        } else {
            return false;
        }
    }

    // make hashCode include zip code
    public int hashCode() {
        return Objects.hash(new Object[]{this.accountId, this.timestamp, this.amount, this.zipCode});
    }

    // make toString include zip code
    public String toString() {
        return "DetailedTransaction{"
                + "accountId=" + accountId
                + ", timestamp=" + timestamp
                + ", amount=" + amount
                + ", zipCode=" + zipCode // include zipcode
                + '}';
    }
}
