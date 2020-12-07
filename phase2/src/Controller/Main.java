package Controller;

import UI.TextUI;

public class Main {
    public static void main(String[] args) {
        TextUI txtUI = new TextUI();
        AppSystem starApp = new AppSystem(txtUI);
        starApp.run();
    }
}
