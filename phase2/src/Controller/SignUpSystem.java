package Controller;
import Presenters.SignUpP;
import UseCase.*;

import java.util.ArrayList;


import java.util.Arrays;

public class SignUpSystem {
    protected AccountManager accM;
    protected SignUpP signUpP;
    protected StrategyManager strategyM;

    public SignUpSystem(AccountManager accM, SignUpP signUpP, StrategyManager strategyM) {
        this.accM = accM;
        this.signUpP = signUpP;
        this.strategyM = strategyM;
    }

    /**
     * Run the sign up block to create a new account.
     */
    public void run() {
        String username;
        String password;
        int userType;
        signUpP.startup();
        userType = chooseType();
        username = createUsername();
        password = createPassword();
        accM.createAccount(username, password, userType);
        signUpP.finishSignUp();

    }


    private int chooseType(){
        ArrayList<Integer> validTypes = new ArrayList<>(Arrays.asList(0,1,2,3));
        String invalidChoice = "Please select one type from below:\n"
                + "0 -> Organizer\n1 -> Attendee\n2 -> Speaker\n3 -> VIP\"";
        return signUpP.chooseOption(validTypes, "Enter UserType:", invalidChoice);
    }

    private String createUsername(){
        boolean succeed = false;
        String userInput;
        do{
            userInput = signUpP.requestUsername();
            if (!accM.existsUsername(userInput)) {
                signUpP.informValidUsername();
                succeed = true;
            } else signUpP.informInvalidUsername();
        }while (!succeed);
        return userInput;
    }

    private String createPassword(){
        String userInput1;
        String userInput2;
        boolean succeed = false;
        do{
            userInput1 = signUpP.requestPassword();
            userInput2 = signUpP.confirmPassword();
            if (userInput1.equals(userInput2)) succeed = true;
            else signUpP.informTwoInputsNotMatch();
        }while (!succeed);
        return userInput1;
    }


}
