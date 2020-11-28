package Presenters;
import java.util.Scanner;

public class SignInUI {
    private final Scanner scannerLogin;

    public SignInUI(){
        this.scannerLogin = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("----------------SignInSystem-----------------");
    }

    public String requestUsername(){
        System.out.println("Please enter your username:");
        return scannerLogin.nextLine();
    }

    public String requestPassword(){
        System.out.println("Please enter your password:");
        return scannerLogin.nextLine();
    }

    public void informAccountNotExist(){
        System.out.println("Username does not exist.\n");
    }

    public void informLoginFailed(){
        System.out.println(
                "###########################################################\n" +
                "#    Incorrect username or password. Please try again.    #\n" +
                "###########################################################");
    }


}
