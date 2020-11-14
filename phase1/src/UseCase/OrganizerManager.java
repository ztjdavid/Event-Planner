package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;

/**
 * The OrganizerManager class implements all functionalities of an organizer.
 */
public class OrganizerManager extends AccountManager{

    public OrganizerManager(){
        super();
    }

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0;
    }

    /**
     * Change the given organizer's username.
     * @param organizer The organizer who want to change username.
     * @param username The username that the organizer wants to change into.
     */
    public void changeUsername(Organizer organizer, String username){organizer.setUsername(username);}

    /**
     * Change the given organizer's password
     * @param organizer The organizer who want to change password.
     * @param password The password that the organizer wants to change into.
     */
    public void changePassword(Organizer organizer, String password){organizer.setPassword(password);}

    /**
     * Check if the current login account can message a given account.
     * @param organizer The organizer who want to send message.
     * @param other Another account that the organizer is going to message(Attendee or Speaker).
     * @param message The Message the organizer is going to send.
     */
    public void sendMessage(Organizer organizer, Account other, Message message){
        if (this.messageable(other)){
            organizer.addSentMessage(message.getmessageid());
            other.addInbox(message.getmessageid());
            System.out.println("Your message has been sent successfully.");

        }else{
            System.out.println("Your message cannot be sent.");
        }
    }

    /**
     * Message all the attendee of the given talk.
     * @param organizer The organizer who want to send message.
     * @param accountList Hash Map of all accounts.
     * @param talk The selected talk.
     * @param message The Message the organizer is going to send.
     */
    public void messageAllAttendee(Organizer organizer, HashMap<Integer, Account> accountList, Talk talk, Message message){
        ArrayList<Integer> Attendance = talk.getAttendeeId();
        int numberAttendee = 0;
        for (Account value : accountList.values()){
            if (value.getUserType() == 1 && Attendance.contains(value.getUserId())){
                organizer.addSentMessage(message.getmessageid());
                value.addInbox(message.getmessageid());
                numberAttendee ++;
            }
        }
        if (numberAttendee == 0){System.out.println("No Attendee is attending the selected talk.");}
        else if (numberAttendee > 0){System.out.println("Message has been sent to all Attendee of the selected talk.");}
    }

    /**
     * Message all the Speaker.
     * @param organizer The organizer who want to send message.
     * @param accountList Hash Map of all accounts.
     * @param message The Message the organizer is going to send.
     */
    public void messageAllSpeaker(Organizer organizer, HashMap<Integer, Account> accountList, Message message){
        int numberSpeaker = 0;
        for (Account value : accountList.values()) {
            if (value.getUserType() == 2) {
                organizer.addSentMessage(message.getmessageid());
                value.addInbox(message.getmessageid());
                numberSpeaker++;
            }
        }
        if (numberSpeaker == 0){System.out.println("There is no Speaker.");}
        else if (numberSpeaker > 0){System.out.println("Message has been sent to all Speakers.");}
    }

}
