package Entity;

import java.util.ArrayList;

public class TestRun {
    public static ArrayList<Integer> listDecoder(String str){
        ArrayList<Integer> result = new ArrayList<>();
        if (str.equals("")) return result;

        String[] strArray = str.split(",");
        for (String s: strArray) result.add(Integer.parseInt(s));
        return result;
    }

        public static void main(String[] args) {
            String a = "0,1,2,3,";
            ArrayList<Integer> b = listDecoder(a);
            for (Integer i: b){
                System.out.println(i);
            }
        }

}
