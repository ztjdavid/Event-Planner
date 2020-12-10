package Entity;

public class Application {
    private int appId;
    private int applicatorId;
    private String applicatorName;
    private String newUsername = "";
    private String newPassword = "";
    private String description;
    private boolean approved = false;

    public Application(int appId, int applicatorId, String applicatorName, String txt){
        this.appId = appId;
        this.applicatorId = applicatorId;
        this.applicatorId = applicatorId;
        this.applicatorName = applicatorName;
        this.description = txt;
    }

    public int getAppId(){ return this.applicatorId; }

    public int getApplicatorId(){return this.applicatorId; }

    public String getApplicatorName() { return applicatorName; }

    public String getNewUsername(){ return this.newUsername; }

    public String getNewPassword(){ return  this.newPassword;}

    public String getDescription(){return this.description;}

    public boolean isApproved() {
        return approved;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
