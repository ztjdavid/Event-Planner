package UseCase;

import Entity.Application;
import UseCase.IGateWay.IApplicationGateWay;

import java.io.IOException;
import java.util.HashMap;

public class ApplicationManager {
    private HashMap<Integer, Application> appList = new HashMap<>();
    private int nextId = 0;
    private IApplicationGateWay gateWay;

    public ApplicationManager(IApplicationGateWay g){this.gateWay = g;}

    public Application getAppWithId(int id) {return this.appList.get(id);}

    /**
     * Create Application.
     * @param applicatorId id
     * @param applicatorName name
     * @param description description
     * @return
     */
    public int createApplication(int applicatorId, String applicatorName, String description){
        int id = nextId;
        createHelper(id, applicatorId, applicatorName, description);
        try{
            gateWay.writeNewApplication(id, applicatorId, applicatorName, description);
        }catch (IOException ignored){}
        nextId += 1;
        return id;
    }

    /**
     * Used in gateway to scan a new application.
     * @param appId id
     * @param applicatorId applicator's id
     * @param applicatorName applicator's name
     * @param description description
     */
    public void scanInApplication(int appId, int applicatorId, String applicatorName, String description){
        createHelper(appId, applicatorId, applicatorName, description);
        nextId = appId + 1;
    }

    /**
     * Set the new username in an application.
     * @param appId id
     * @param username username
     */
    public void setNewUsername(int appId, String username){
        Application a = getAppWithId(appId);
        a.setNewUsername(username);
        try{
            gateWay.updateNewUsername(appId, username);
        }catch (IOException ignored){}
    }

    /**
     * Set the new password in an application.
     * @param appId id
     * @param passW password
     */
    public void setNewPassword(int appId, String passW){
        Application a = getAppWithId(appId);
        a.setNewUsername(passW);
        try{
            gateWay.updateNewPassword(appId, passW);
        }catch (IOException ignored){}
    }

    /**
     * Approve an application.
     * @param appId id
     */
    public void Approve(int appId){
        Application a = getAppWithId(appId);
        a.setApproved(true);
        try{
            gateWay.updateStatus(appId, true);
        }catch (IOException ignored){}
    }

    /**
     * Format a string describing an application to an organizer.
     * @param appId id
     * @return string describing an application.
     */
    public String formatInfoToOrganizer(int appId){
        Application a = getAppWithId(appId);
        StringBuilder s = new StringBuilder();
        s.append("-------------------------------\n");
        s.append("Application id: ").append(a.getAppId()).append("\n");
        s.append("Applicator id: ").append(a.getApplicatorId()).append("\n");
        s.append("ApplicatorName: ").append(a.getApplicatorName()).append("\n");
        s.append("Description: \n").append(a.getDescription()).append("\n");
        return s.toString();
    }

    /**
     * Format a string describing an application to an attendee/vip.
     * @param appId id
     * @return string describing an application.
     */
    public String formatInfoToAttendee(int appId){
        Application a = getAppWithId(appId);
        StringBuilder s = new StringBuilder();
        s.append("##############################\n");
        s.append("Application id: ").append(a.getAppId()).append("\n");
        s.append("Applicator id: ").append(a.getApplicatorId()).append("\n");
        s.append("ApplicatorName: ").append(a.getApplicatorName()).append("\n");
        if (a.isApproved()){
            s.append("Status: Approved\n");
            s.append("New account Username: ").append(a.getNewUsername()).append("\n");
            s.append("New account Password: ").append(a.getNewPassword()).append("\n");
        }else{s.append("Status: Pending\n");}
        return s.toString();
    }

    /**
     * Set some instance variables of an application object. (Used in a gateway class)
     * @param id id
     * @param approved status
     * @param username username
     * @param password password
     */
    public void setInfo(int id, boolean approved, String username, String password){
        Application a = getAppWithId(id);
        a.setApproved(approved);
        a.setNewUsername(username);
        a.setNewPassword(password);
    }

    private void createHelper(int appId, int applicatorId, String applicatorName, String description){
        Application a = new Application(appId, applicatorId, applicatorName, description);
        this.appList.put(appId, a);
    }
}
