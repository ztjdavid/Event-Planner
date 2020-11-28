package Gateways;

import org.ini4j.*;
import java.io.File;
import java.io.IOException;

public class UserGateway {
    Ini iniFile;
    public UserGateway(String pathname) throws IOException{
        this.iniFile = new Ini(new File(pathname));
    }

    public void writeData() throws IOException{
        iniFile.put("0", "Name", "Eric");
        iniFile.store();
    }

    public void loadData(){
        String name = iniFile.get("0", "Name", String.class);
        System.out.println(name);
    }




}
