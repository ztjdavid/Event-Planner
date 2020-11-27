package Presenters;

import java.util.ArrayList;

public class Presenter {
    public ITextUI textUI;

    public Presenter(ITextUI UI){
        this.textUI = UI;
    }


    public void printText(String txt) {
        textUI.print(txt);
    }

    public String requestInput(){
        return textUI.request();
    }

    public int chooseOption(ArrayList<Integer> choiceList, String requestInfo,
                            String invalidInfo){
        boolean exit = false;
        String userInput;
        do{
            printText(requestInfo);
            userInput = requestInput();
            if (!isValidChoice(userInput, choiceList)) printText(invalidInfo);
            else exit = true;
        }while(!exit);
        return Integer.parseInt(userInput);
    }

    public int chooseOption(ArrayList<Integer> choiceList, String requestInfo,
                            String invalidInfo, String successInfo){
        boolean exit = false;
        String userInput;
        do{
            printText(requestInfo);
            userInput = requestInput();
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
}
