package UI;
import java.util.Scanner;

public class SignUpUI {
    private final Scanner scannerSignUp;

    public SignUpUI(){
        this.scannerSignUp = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("----------------SignUpSystem-----------------");
        System.out.println("What type of account you want to create?\n" +
                "0 -> Organizer\n1 -> Attendee\n2 -> Speaker");
    }

    public void informInValidChoice(){
        System.out.println("Please select one type from below:\n"
                + "0 -> Organizer\n1 -> Attendee\n2 -> Speaker");
    }

    public void informValidUsername(){
        System.out.println("Nice name!");
    }

    public void informInvalidUsername(){
        System.out.println("I'm sorry but it seems someone already took this name.\n" +
                "Try a different name.\n");
    }

    public void informTwoInputsNotMatch(){
        System.out.println("Not match! Set your password again.\n");
    }

    public void finishSignUp(){
        System.out.println("Perfect! Now you can log in to your account.");
    }

    public String requestUserType(){
        return scannerSignUp.nextLine();
    }

    public String requestUsername(){
        System.out.println("Please tell me what username you would like to use:");
        return scannerSignUp.nextLine();
    }

    public String requestPassword(){
        System.out.println("Please set your password:");
        return scannerSignUp.nextLine();
    }

    public String confirmPassword(){
        System.out.println("Repeat your password:");
        return scannerSignUp.nextLine();
    }
}
