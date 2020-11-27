package Controller;
import UI.SignInUI;
import UseCase.*;

public class SignInSystem {
    protected AccountManager accM;
    protected SignInUI signInUI;

    public SignInSystem(AccountManager accM, SignInUI signInUI){
        this.accM = accM;
        this.signInUI = signInUI;
    }

    /**
     * Run the Sign in block to log in an account.
     * @return An integer representing the userId of login account.
     */
    public int run(){
        boolean isValid = false;
        String username;
        String password;
        signInUI.startup();

        while(!isValid){
            username = confirmUsername();
            password = signInUI.requestPassword();
            if (accM.loginAccount(username, password)) isValid = true;
            else signInUI.informLoginFailed();
        }
        return accM.getCurrAccount().getUserType();
    }


    //Helper method:

    private String confirmUsername(){
        boolean valid = false;
        String username;
        do{
            username = signInUI.requestUsername();
            if (accM.existsUsername(username)) valid = true;
            else signInUI.informAccountNotExist();
        }while (!valid);
        return username;
    }
}
