package Gateways;

import java.util.ArrayList;
import java.util.HashMap;

public class GatewayHandler {
    public GatewayHandler(){}

    /**
     * Strategy to decode a string into an array list.
     * @param str string
     * @return a list of integer generated from given string.
     * @throws NumberFormatException
     */
    public ArrayList<Integer> listDecoder(String str)throws NumberFormatException{
        ArrayList<Integer> result = new ArrayList<>();
        if (str.equals("")) return result;

        String[] strArray = str.split(",");
        for (String s: strArray) result.add(Integer.parseInt(s));
        return result;
    }

    /**
     * Strategy to assemble two array lists into a hashmap.
     * @param key an arraylist representing keys.
     * @param value an arraylist representing values.
     * @return a hashmap generated from the two array lists
     */
    public HashMap<Integer, Integer> mapAssemble(ArrayList<Integer> key, ArrayList<Integer> value){
        HashMap<Integer, Integer> result = new HashMap<>();
        if (key.size() == 0) return result;
        for (int i = 0; i < key.size(); i++) result.put(key.get(i), value.get(i));
        return result;
    }

}
