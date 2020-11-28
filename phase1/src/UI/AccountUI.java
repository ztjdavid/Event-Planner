package UI;

import Entity.Account;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountUI {
    private final Scanner accountUI;


    public AccountUI(){
        this.accountUI = new Scanner(System.in);
    }

    public void startup(){
        System.out.println("----------------AccountSystem-----------------\nHi, " +
                "Initializing database.");
    }

    public void setupDataBase(){
        System.out.println("----------------Initializing the database-----------------");
    }

    public String requestFilePath(){
        System.out.println("Please provide the file path to the account data file you want to create:");
        return accountUI.nextLine();
    }

    public void finishSetUp(){
        System.out.println("Accounts successfully created from file.");
    }

    public void displayAllAccounts(ArrayList<Account> lst){
        StringBuilder accountData;
        accountData = new StringBuilder("These are the all the accounts in the system. ");
        for(Account acc : lst) {
            int accountID = acc.getUserId();
        }
        System.out.println(accountData);
    }

}

