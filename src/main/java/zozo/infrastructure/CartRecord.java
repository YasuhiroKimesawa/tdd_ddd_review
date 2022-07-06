package zozo.infrastructure;

public class CartRecord {
  private String id;
  private String userAccountId;
  private int upperLimit;

  public CartRecord(String id, String userAccountId, int upperLimit) {
    this.id = id;
    this.userAccountId = userAccountId;
    this.upperLimit = upperLimit;
  }

  public String getId() {
    return id;
  }

  public String getUserAccountId() {
    return userAccountId;
  }

  public int getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(int upperLimit) {
    this.upperLimit = upperLimit;
  }
}
