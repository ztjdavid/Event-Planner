package Controller;
import UseCase.*;
import java.util.ArrayList;


import java.util.Arrays;
import java.util.Scanner;

public class SignUpSystem {
    private final ArrayList<Integer> USER_TYPES = new ArrayList<>(Arrays.asList(0,1,2));
    protected AccountManager acM;
    protected OrganizerManager oM;
    protected SpeakerManager sM;

    public SignUpSystem() {
        this.acM = new AccountManager();
        this.oM = new OrganizerManager();
        this.sM = new SpeakerManager();
    }


    public void run() {
        Scanner scannerSignUp = new Scanner(System.in);
        String username;
        String password;
        int userType;

        System.out.println("----------------SignUpSystem-----------------");
        System.out.println("What type of account you want to create?\n" +
                "0 -> Organizer\n1 -> Attendee\n2 -> Speaker");

        userType = chooseType(scannerSignUp);
        username = createUsername(scannerSignUp);
        password = createPassword(scannerSignUp);

        switch (userType) {
            case 0:
                oM.createAccount(username, password);
                break;
            case 2:
                sM.createAccount(username, password);
                break;
        }

        System.out.println("Perfect! Now you can log in to your account.");
    }


    private int chooseType(Scanner scannerSignUp){
        String userInput;
        int type = -1;
        boolean valid = false;
        while(!valid){
            userInput = scannerSignUp.nextLine();
            if (!isValidType(userInput))
                System.out.println("Please select one type from below:\n"
                + "0 -> Organizer\n1 -> Attendee\n2 -> Speaker");
            else {
                valid = true;
                type = Integer.parseInt(userInput);}
        }
        return type;
    }

    private boolean isValidType(String userInput){
        int num;
        try{
            num = Integer.parseInt(userInput);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return USER_TYPES.contains(num);
    }

    private String createUsername(Scanner scannerSignUp){
        boolean succeed = false;
        String userInput = "TBD";
        while (!succeed) {
            System.out.println("Please tell me what username you would like to use:");
            userInput = scannerSignUp.nextLine();
            if (!acM.existsUsername(userInput)) {
                System.out.println("Nice name!");
                succeed = true;
            } else System.out.println("I'm sorry but it seems someone has taken this name.\n" +
                    "Try a different name.\n");
        }
        return userInput;
    }

    private String createPassword(Scanner scannerSignUp){
        String userInput1 = "TBD";
        String userInput2;
        boolean succeed = false;
        while (!succeed){
            System.out.println("Please set your password:");
            userInput1 = scannerSignUp.nextLine();
            System.out.println("Repeat your password:");
            userInput2 = scannerSignUp.nextLine();
            if (userInput1.equals(userInput2)) succeed = true;
            else System.out.println("Not match! Set your password again.\n");
        }
        return userInput1;
    }


}
