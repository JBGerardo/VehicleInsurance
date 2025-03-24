package Java_Project.Vehicle_Insurance_Management.model;

public class StripeSession {
    private final String sessionId;
    private final String url;
    private final long amount;

    public StripeSession(String sessionId, String url, long amount) {
        this.sessionId = sessionId;
        this.url = url;
        this.amount = amount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUrl() {
        return url;
    }

    public long getAmount() {
        return amount;
    }
}
