package UI;
import Presenters.ITextUI;

import java.util.Scanner;

public class TextUI implements ITextUI {
    private Scanner scanner = new Scanner(System.in);
    public TextUI(){}

    @Override
    public void print(String text) {
        System.out.println(text);
    }

    @Override
    public String request() {
        return scanner.nextLine();
    }
}
