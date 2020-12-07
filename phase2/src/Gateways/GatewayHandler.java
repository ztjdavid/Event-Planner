package Gateways;

import java.util.ArrayList;

public class GatewayHandler {
    public GatewayHandler(){}

    public ArrayList<Integer> listDecoder(String str)throws NumberFormatException{
        ArrayList<Integer> result = new ArrayList<>();
        if (str.equals("")) return result;

        String[] strArray = str.split(",");
        for (String s: strArray) result.add(Integer.parseInt(s));
        return result;
    }

}
