package Controller;
import Presenters.SignUpUI;
import UseCase.*;
import java.util.ArrayList;


import java.util.Arrays;

public class SignUpSystem {
    protected AccountManager accM;
    protected SignUpUI signUpUI;
    protected StrategyManager strategyM;

    public SignUpSystem(AccountManager accM, SignUpUI signUpUI, StrategyManager strategyM) {
        this.accM = accM;
        this.signUpUI = signUpUI;
        this.strategyM = strategyM;
    }

    /**
     * Run the sign up block to create a new account.
     */
    public void run() {
        String username;
        String password;
        int userType;
        signUpUI.startup();
        userType = chooseType();
        username = createUsername();
        password = createPassword();
        accM.createAccount(username, password, userType);

        signUpUI.finishSignUp();

    }


    private int chooseType(){
        ArrayList<Integer> validTypes = new ArrayList<>(Arrays.asList(0,1,2));
        String userInput;
        int type = -1;
        boolean valid = false;
        while(!valid){
            userInput = signUpUI.requestUserType();
            if (!strategyM.isValidChoice(userInput, validTypes))
                signUpUI.informInValidChoice();
            else {
                valid = true;
                type = Integer.parseInt(userInput);}
        }
        return type;
    }

    private String createUsername(){
        boolean succeed = false;
        String userInput;
        do{
            userInput = signUpUI.requestUsername();
            if (!accM.existsUsername(userInput)) {
                signUpUI.informValidUsername();
                succeed = true;
            } else signUpUI.informInvalidUsername();
        }while (!succeed);
        return userInput;
    }

    private String createPassword(){
        String userInput1;
        String userInput2;
        boolean succeed = false;
        do{
            userInput1 = signUpUI.requestPassword();
            userInput2 = signUpUI.confirmPassword();
            if (userInput1.equals(userInput2)) succeed = true;
            else signUpUI.informTwoInputsNotMatch();
        }while (!succeed);
        return userInput1;
    }


}
