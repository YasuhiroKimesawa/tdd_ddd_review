package zozo.interface_adaptor.http.controller;

public class CreateCartRequest {
    private String userAccountId;
    private int upperLimit;

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Override
    public String toString() {
        return "CreateCart{" +
                "userAccountId='" + userAccountId + '\'' +
                ", upperLimit=" + upperLimit +
                '}';
    }
}
