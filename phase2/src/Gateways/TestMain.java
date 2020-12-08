package Gateways;
import Entity.*;

import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        String a = "a,b,c,,,,,,,,,,,,,";
        for (String s: a.split(",")) System.out.println(s);
        System.out.println(a.split(",").length);
    }
}
