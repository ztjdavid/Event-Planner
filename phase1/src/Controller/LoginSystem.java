package Controller;
import UseCase.*;
import java.util.Scanner;

public class LoginSystem {
    protected AccountManager acM;

    public LoginSystem(){
        this.acM = new AccountManager();
    }
    public int run(){
        boolean isValid = false;
        Scanner scannerLogin = new Scanner(System.in);
        String username;
        String password;
        System.out.println("----------------LoginSystem-----------------");
        while(!isValid){
            username = verifyUsername(scannerLogin);
            System.out.println("Please enter your password:");
            password = scannerLogin.nextLine();
            isValid = acM.loginAccount(username, password);
            if (!isValid) System.out.println(
                    "###########################################################\n" +
                    "#    Incorrect username or password. Please try again.    #\n" +
                    "###########################################################");
        }
        return acM.getCurrAccountId();
    }


    private String verifyUsername(Scanner scannerLogin){
        boolean valid = false;
        String userInput = "TBD";
        while (!valid){
            System.out.println("Please enter your username:");
            userInput = scannerLogin.nextLine();
            if (acM.existsUsername(userInput)) valid = true;
            else System.out.println("Username does not exist.\n");
        }
        return userInput;
    }
}
