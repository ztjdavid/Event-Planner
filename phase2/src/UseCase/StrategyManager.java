package UseCase;

import java.util.ArrayList;

public class StrategyManager {

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
        catch (NumberFormatException nfe){
            return false;
        }
        return choiceList.contains(num);
    }

    /**
     * Check if the given arraylist is empty.
     * @param a an arraylist of integer.
     * @return true iff the arraylist is empty.
     */
    public boolean isEmptyList(ArrayList<Integer> a) {
        return a.isEmpty();
    }
}
