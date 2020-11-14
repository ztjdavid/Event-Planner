package UI;

import Entity.Account;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountUI {
    private final Scanner input;

    public AccountUI(){
        this.input = new Scanner(System.in);
    }

    public void startup(){
        System.out.println("----------------AccountSystem-----------------\nHi, " +
                "Administrator, Would you like to\n1 -> SetupDatabase\n2 -> UpdateDatabase\n3 -> ViewDatabase\n4 -> Change User" +
                " Password\n5 -> Change User UserName\n6 -> Quit");
    }

    public void setupDataBase(){
        System.out.println("----------------Initializing the database-----------------");
        // create the json file
    }

    public void reportDataBase(){
        System.out.println("----------------Accessing the database-----------------");
        // write data to json then convert to csv?
    }

    public void displayAllAccounts(ArrayList<Account> lst){
        StringBuilder a;
        a = new StringBuilder("These are the all the accounts in the system. ");
        for(Account acc : lst) {
            int accountID = acc.getUserId();
        }
        System.out.println(a);
    }

    public void changepassword(){
        System.out.println("----------------Accessing the database-----------------\nPlease enter the id of the account you want to change");
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");
    }


}
