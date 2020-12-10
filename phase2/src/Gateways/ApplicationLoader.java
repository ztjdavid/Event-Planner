package Gateways;

import UseCase.*;
import UseCase.IGateWay.IEventGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ApplicationLoader {
    private Ini iniFile;
    private ApplicationManager appM;
    private GatewayHandler gH = new GatewayHandler();

    public ApplicationLoader(String pathname, ApplicationManager appM)throws IOException{
        this.appM = appM;
        this.iniFile = new Ini(new File(pathname));
    }

    /**
     * Load all application data.
     * @throws NumberFormatException
     */
    public void loadData()throws NumberFormatException{
       Set<String> idSet = iniFile.keySet();
       for (String ID: idSet){
           int id = Integer.parseInt(ID);
           //get all variables
           int applicatorId = iniFile.get(ID, "ApplicatorId", int.class);
           String applicatorName = iniFile.get(ID, "ApplicatorName");
           String newUsername = iniFile.get(ID, "NewUsername");
           String newPassword = iniFile.get(ID, "NewPassword");
           String description = iniFile.get(ID, "Description");
           boolean approved = iniFile.get(ID, "Approved", boolean.class);
           // create application
           appM.scanInApplication(id, applicatorId, applicatorName, description);
           // set other variables
           appM.setInfo(id, approved, newUsername, newPassword);

       }


    }
}
