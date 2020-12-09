package Presenters;
import java.util.Scanner;

public class StartP extends Presenter{
    public StartP(ITextUI ui){super(ui); }


    public void startup(){
       printText("Hi, user! Would you like to\n1 -> Signin\n2 -> Signup\n3 -> End Program");
    }

    public void informInvalidInput(){
        printText("Please select one operation from below:\n1 -> login\n2 -> signup\n3 -> End Program");
    }

    public void informQuiting(){
        printText("Ending the program...");
    }

    public String requestModeSelection(){
        return requestInput();
    }
}
