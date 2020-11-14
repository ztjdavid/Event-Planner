package UseCase;

import java.util.ArrayList;

public class StrategyManager {

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


    public boolean isEmptyList(ArrayList<Integer> a) {
        return a.isEmpty();
    }
}
