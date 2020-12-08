package Gateways;

import java.util.ArrayList;
import java.util.HashMap;

public class GatewayHandler {
    public GatewayHandler(){}

    public ArrayList<Integer> listDecoder(String str)throws NumberFormatException{
        ArrayList<Integer> result = new ArrayList<>();
        if (str.equals("")) return result;

        String[] strArray = str.split(",");
        for (String s: strArray) result.add(Integer.parseInt(s));
        return result;
    }

    public HashMap<Integer, Integer> mapAssemble(ArrayList<Integer> key, ArrayList<Integer> value){
        HashMap<Integer, Integer> result = new HashMap<>();
        if (key.size() == 0) return result;
        for (int i = 0; i < key.size(); i++) result.put(key.get(i), value.get(i));
        return result;
    }

}
