package Presenters;
import java.util.Scanner;

public class SignUpP extends Presenter{
    public SignUpP(ITextUI textUI){
        super(textUI);
    }


    public void startup(){
        printText("----------------SignUpSystem-----------------");
        printText("What type of account you want to create?\n" +
                "0 -> Organizer\n1 -> Attendee\n2 -> Speaker\n3 -> VIP");
    }

    public void informValidUsername(){
        printText("Nice name!");
    }

    public void informInvalidUsername(){
        printText("I'm sorry but it seems someone already took this name.\n" +
                "Try a different name.\n");
    }

    public void informTwoInputsNotMatch(){
        printText("Not match! Set your password again.\n");
    }

    public void finishSignUp(){
        printText("Perfect! Now you can log in to your account.");
    }

    public String requestUsername(){
        return requestInput("Please tell me what username you would like to use:");
    }

    public String requestPassword(){
        return requestInput("Please set your password:");
    }

    public String confirmPassword(){
        return requestInput("Repeat your password:");
    }
}
