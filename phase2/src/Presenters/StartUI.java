package Presenters;
import java.util.Scanner;

public class StartUI {
    private final Scanner scannerStart;

    public StartUI(){
        this.scannerStart = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("Hi, user! Would you like to\n1 -> Signin\n2 -> Signup\n3 -> End Program");
    }

    public void informInvalidInput(){
        System.out.println("Please select one operation from below:\n1 -> login\n2 -> signup\n3 -> End Program");
    }

    public void informQuiting(){
        System.out.println("Ending the program...");
    }

    public String requestModeSelection(){
        return scannerStart.nextLine();
    }
}
