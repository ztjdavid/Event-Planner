package Controller;
import Presenters.SignInP;
import UseCase.*;

public class SignInSystem {
    protected AccountManager accM;
    protected SignInP signInP;

    public SignInSystem(AccountManager accM, SignInP signInP){
        this.accM = accM;
        this.signInP = signInP;
    }

    /**
     * Run the Sign in block to log in an account.
     * @return An integer representing the userId of login account.
     */
    public int run(){
        boolean isValid = false;
        String username;
        String password;
        signInP.startup();

        while(!isValid){
            username = confirmUsername();
            password = signInP.requestPassword();
            if (accM.loginAccount(username, password)) isValid = true;
            else signInP.informLoginFailed();
        }
        return accM.getCurrAccount().getUserType();
    }


    //Helper method:

    private String confirmUsername(){
        boolean valid = false;
        String username;
        do{
            username = signInP.requestUsername();
            if (accM.existsUsername(username)) valid = true;
            else signInP.informAccountNotExist();
        }while (!valid);
        return username;
    }
}
