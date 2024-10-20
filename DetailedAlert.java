package spendreport;

import java.util.Objects;

/** A detailed alert with timestamp, amount, and zip in addition to account id. */
public final class DetailedAlert {
    // include timestamp, amount, and zip in addition to account id
    private long accountId;
    private long timestamp;
    private double amount;
    private String zipCode;

    public DetailedAlert() {
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
            DetailedAlert alert = (DetailedAlert) o;
            return this.accountId == alert.accountId
                    && this.timestamp == alert.timestamp
                    && Double.compare(alert.amount, this.amount) == 0
                    && this.zipCode.equals(alert.zipCode);
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.accountId, this.timestamp, this.amount, this.zipCode});
    }

    public String toString() {
        return "DetailedAlert{"
                + "accountId=" + accountId
                + ", timestamp=" + timestamp
                + ", amount=" + amount
                + ", zipCode=" + zipCode
                + '}';
    }
}

