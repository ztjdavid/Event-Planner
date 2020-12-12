package Presenters;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A general presenter class with basic methods. All other presenter classes extend it.
 */
public class Presenter {
    public ITextUI textUI;

    public Presenter(ITextUI UI){
        this.textUI = UI;
    }




    /**
     * Print text on screen(Console).
     * @param txt String being printed.
     */
    public void printText(String txt) {
        textUI.print(txt);
    }

    /**
     * Ask user for input.
     * @return User's input in next line.
     */
    public String requestInput(){
        return textUI.request();
    }

    /**[Overload]
     * Ask user for input.
     * @param requestInfo Message printed before user giving input.
     * @return User's input in next line.
     */
    public String requestInput(String requestInfo){
        printText(requestInfo);
        return requestInput();
    }

    /**
     * Ask user for a numeric input. If not, keep requesting.
     * @param requestInfo Message printed before user giving input.
     * @return Integer which matches user's input.
     */
    public int requestNumInput(String requestInfo){
        int num = -2;
        boolean exit;
        do{
            exit = true;
            String userInput = requestInput(requestInfo);
            try{
                num = Integer.parseInt(userInput);
            }catch (NumberFormatException nfe){
                exit = false;
            }
        }while(!exit);
        return num;
    }

    /**
     * Ask user to press Enter.
     */
    public void pause(){
        printText("Press Enter to continue.");
        requestInput();
    }

    /**
     * Ask user to choose an option(numeric).
     * @param choiceList Valid options.
     * @param requestInfo Message printed before user giving input.
     * @param invalidInfo Message printed if user gives an invalid choice.
     * @return User's choice.
     */
    public int chooseOption(ArrayList<Integer> choiceList, String requestInfo,
                            String invalidInfo){
        boolean exit = false;
        String userInput;
        do{
            userInput = requestInput(requestInfo);
            if (!isValidChoice(userInput, choiceList)) printText(invalidInfo);
            else exit = true;
        }while(!exit);
        return Integer.parseInt(userInput);
    }

    /**[Overload]
     * Ask user to choose an option(numeric).
     * @param choiceList Valid options.
     * @param requestInfo Message printed before user giving input.
     * @param invalidInfo Message printed if user gives an invalid choice.
     * @param successInfo Message printed if user gives an valid choice.
     * @return User's choice.
     */
    public int chooseOption(ArrayList<Integer> choiceList, String requestInfo,
                            String invalidInfo, String successInfo){
        boolean exit = false;
        String userInput;
        do{
            userInput = requestInput(requestInfo);
            if (!isValidChoice(userInput, choiceList)) printText(invalidInfo);
            else{
                printText(successInfo);
                exit = true;
            }
        }while(!exit);
        return Integer.parseInt(userInput);
    }

    /**
     * Check if user's input is valid according to the given choice list.
     * @param userInput String representation of user's input.
     * @param choiceList an arraylist of choices as integer.
     * @return true iff the choice list contains the user's input.
     */
    public boolean isValidChoice(String userInput, ArrayList<Integer> choiceList){
        int num;
        try{
            num = Integer.parseInt(userInput);
        }
        catch (Exception e){
            return false;
        }
        return choiceList.contains(num);
    }

    /**
     * Get the reponce of the requesting information.
     * @param requestInfo The information that is requesting.
     * @return the entered information.
     */
    public String enterMessage(String requestInfo) {
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        printText(requestInfo);
        do {
            String line = requestInput();
            if (line.equals("end")) exit = true;
            else {
                a.append(line);
                a.append("\n");
            }
        } while (!exit);
        return a.toString();
    }
}
