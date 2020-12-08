package Presenters;
import java.util.Scanner;

public class SignInP extends Presenter{
    public SignInP(ITextUI textUI){ super(textUI);}


    public void startup(){
        printText("----------------SignInSystem-----------------");
    }

    public String requestUsername(){
        return requestInput("Please enter your username:");
    }

    public String requestPassword(){
        return requestInput("Please enter your password:");
    }

    public void informAccountNotExist(){
       printText("Username does not exist.\n");
    }

    public void informLoginFailed(){
        printText(
                "###########################################################\n" +
                "#    Incorrect username or password. Please try again.    #\n" +
                "###########################################################");
    }


}
