package Controller;
import UseCase.*;


import java.util.Scanner;

public class SignUpSystem {
    protected AccountManager acM;
    protected OrganizerManager oM;
    protected SpeakerManager sM;

    public SignUpSystem() {
        this.acM = new AccountManager();
        this.oM = new OrganizerManager();
        this.sM = new SpeakerManager();
    }


    public void run() {
        boolean succeed = false;
        Scanner scannerSignUp = new Scanner(System.in);
        String username = "TBD";
        String password;
        int userType;

        System.out.println("----------------SignUpSystem-----------------");
        System.out.println("What type of account you would like to use?\n" +
                "0 -> Organizer\n1 -> Attendee\n2 -> Speaker");
        userType = Integer.parseInt(scannerSignUp.nextLine());
        while (!succeed) {
            System.out.println("Please tell me what username you would like to use:");
            username = scannerSignUp.nextLine();
            if (!acM.duplicatedUsername(username)) {
                System.out.println("Nice name!");
                succeed = true;
            } else System.out.println("I'm sorry but it seems someone has taken this name.\n" +
                    "Try a different name.\n");
        }
        System.out.println("Now please set your password:");
        password = scannerSignUp.nextLine();

        switch (userType) {
            case 0:
                oM.createAccount(username, password);
            case 2:
                sM.createAccount(username, password);
        }

        System.out.println("Perfect! Now you can log in to your account.");
    }
}
