package Controller;
import Presenter.SignInUI;
import UseCase.*;

public class SignInSystem {
    protected LoginManager loginM;
    protected SignInUI signInUI;

    public SignInSystem(LoginManager loginM, SignInUI signInUI){
        this.loginM = loginM;
        this.signInUI = signInUI;
    }
    public int run(){
        boolean isValid = false;
        String username;
        String password;
        signInUI.startup();

        while(!isValid){
            username = confirmUsername();
            password = signInUI.requestPassword();
            if (loginM.loginAccount(username, password)) isValid = true;
            else signInUI.informLoginFailed();
        }
        return loginM.getCurrAccount().getUserType();
    }


    //Helper method:

    private String confirmUsername(){
        boolean valid = false;
        String username;
        do{
            username = signInUI.requestUsername();
            if (loginM.existsUsername(username)) valid = true;
            else signInUI.informAccountNotExist();
        }while (!valid);
        return username;
    }
}
