package Controller;
import UseCase.*;
import java.util.Scanner;

public class LoginSystem {
    protected AccountManager aM;

    public LoginSystem(){
        this.aM = new AccountManager();
    }
    public int run(){
        boolean isValid = false;
        Scanner scannerLogin = new Scanner(System.in);
        String username;
        String password;
        System.out.println("----------------LoginSystem-----------------");
        while(!isValid){
            System.out.println("Please enter your username:");
            username = scannerLogin.nextLine();
            System.out.println("Please enter your password:");
            password = scannerLogin.nextLine();
            isValid = aM.loginAccount(username, password);
            if (!isValid) System.out.println(
                    "###########################################################\n" +
                    "#    Incorrect username or password. Please try again.    #\n" +
                    "###########################################################");
        }
        return aM.getCurrAccountId();
    }
}
