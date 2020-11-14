package Controller;
import Entity.*;
import UseCase.*;
import UI.AccountUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AccountSystem {
    protected AccountManager accountM;
    protected LoginManager loginM;
    protected AccountUI accountUI;
//    protected MessageManager MsgM;
//    protected Organizer currOrganizer;
//    protected Attendee currAttendee;


    public AccountSystem(AccountManager accountM, LoginManager loginM) {
        this.accountM = accountM;
        this.loginM = loginM;
    }

    public void run() throws FileNotFoundException {
        accountUI.startup();
        String filePath = accountUI.requestFilePath();
        readFromFile(filePath);
        accountUI.finishSetUp();
    }

    public void readFromFile(String filePath) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] accountData;

        while (scanner.hasNextLine()){
            accountData = scanner.nextLine().split(",");
            String username = accountData[0];
            String password = accountData[1];
            int userType = Integer.parseInt(accountData[2]);
            loginM.createAccount(username, password, userType);
        }

        scanner.close();
    }

    // Other functionality for this system uncleared. Placed in sub-branch for now.
}
