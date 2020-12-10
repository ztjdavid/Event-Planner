package Gateways;

import UseCase.IGateWay.IApplicationGateWay;
import UseCase.IGateWay.IEventGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ApplicationWriter implements IApplicationGateWay {
    private Ini iniFile;
    private GatewayHandler gH = new GatewayHandler();

    public ApplicationWriter(String pathname)throws IOException{
        this.iniFile = new Ini(new File(pathname));
    }

    /**
     * Write a new application into database.
     * @param appId id
     * @param applicatorId applicator's id
     * @param applicatorName applicator name
     * @param description description
     * @throws IOException If the database file does not exist.
     */
    public void writeNewApplication(int appId, int applicatorId, String applicatorName, String description)
            throws IOException{
        String ID = String.valueOf(appId);
        this.iniFile.put(ID, "ApplicatorId", applicatorId);
        this.iniFile.put(ID, "ApplicatorName", applicatorName);
        this.iniFile.put(ID, "NewUsername", "");
        this.iniFile.put(ID, "NewPassword", "");
        this.iniFile.put(ID, "Description", description);
        this.iniFile.put(ID, "Approved", false);
        this.iniFile.store();
    }

    /**
     * Update key "Approved" in given section(id).
     * @param appId id
     * @param status status
     * @throws IOException
     */
    public void updateStatus(int appId, boolean status)throws IOException{
        String ID = String.valueOf(appId);
        this.iniFile.put(ID, "Approved", status);
        this.iniFile.store();
    }

    /**
     * Update key "NewUsername" in given section(id).
     * @param appId id
     * @param username username
     * @throws IOException
     */
    public void updateNewUsername(int appId, String username)throws IOException{
        String ID = String.valueOf(appId);
        this.iniFile.put(ID, "NewUsername", username);
        this.iniFile.store();
    }

    /**
     * Update key "NewPassword" in given section(id).
     * @param appId id
     * @param passw password
     * @throws IOException
     */
    public void updateNewPassword(int appId, String passw)throws IOException{
        String ID = String.valueOf(appId);
        this.iniFile.put(ID, "NewPassword", passw);
        this.iniFile.store();
    }


}
