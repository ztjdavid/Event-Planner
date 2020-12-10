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

    public void createApplication(int applicatorId, String applicatorName, String description){
        createHelper(nextId, applicatorId, applicatorName, description);
        try{
            gateWay.writeNewApplication(nextId, applicatorId, applicatorName, description);
        }catch (IOException ignored){}
        nextId += 1;
    }

    public void scanInApplication(int appId, int applicatorId, String applicatorName, String description){
        createHelper(appId, applicatorId, applicatorName, description);
        nextId = appId + 1;
    }

    public void setNewUsername(int appId, String username){
        Application a = getAppWithId(appId);
        a.setNewUsername(username);
        try{
            gateWay.updateNewUsername(appId, username);
        }catch (IOException ignored){}
    }

    public void setNewPassword(int appId, String passW){
        Application a = getAppWithId(appId);
        a.setNewUsername(passW);
        try{
            gateWay.updateNewPassword(appId, passW);
        }catch (IOException ignored){}
    }

    public void Approve(int appId){
        Application a = getAppWithId(appId);
        a.setApproved(true);
        try{
            gateWay.updateStatus(appId, true);
        }catch (IOException ignored){}
    }

    public String formatInfoToOrganizer(int appId){
        // 如果需要显示Status和我说下， 我默认只会展示未approved的application.
        Application a = getAppWithId(appId);
        StringBuilder s = new StringBuilder();
        s.append("-------------------------------\n");
        s.append("Application id: ").append(a.getAppId()).append("\n");
        s.append("Applicator id: ").append(a.getApplicatorId()).append("\n");
        s.append("ApplicatorName: ").append(a.getApplicatorName()).append("\n");
       s.append("Description: \n").append(a.getDescription()).append("\n");
        return s.toString();
    }

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
