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

    /**
     * Get the application ID
     * @return The integer of the application ID
     */
    public int getAppId(){ return this.applicatorId; }

    /**
     * Get the applicator ID
     * @return The integer of the applicator ID
     */
    public int getApplicatorId(){return this.applicatorId; }

    /**
     * Get the applicator's name
     * @return The String of the applicator's name
     */
    public String getApplicatorName() { return applicatorName; }

    /**
     * Get the new username for the applicator
     * @return The String of the new username for the applicator
     */
    public String getNewUsername(){ return this.newUsername; }

    /**
     * Get the new password for the applicator
     * @return The String of the new password for the applicator
     */
    public String getNewPassword(){ return  this.newPassword;}

    /**
     * Get the applicator's description of why he/she want to submit the application
     * @return The String of the applicator's description of reasoning
     */
    public String getDescription(){return this.description;}

    /**
     * Show the status of the application
     * @return true iff the application has been approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Get the new username for the applicator
     * @param  newUsername the new username for the applicator
     */
    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    /**
     * Get the new username for the applicator
     * @param  newPassword the new password for the applicator
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Set the status of the application
     * @param approved true -> approved; false -> not yet approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
