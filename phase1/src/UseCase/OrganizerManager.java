package UseCase;
import Entity.*;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.omg.PortableInterceptor.INACTIVE;

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
     * @param message_txt The string of the message the organizer is going to send.
     */
    public void sendMessage(Organizer organizer, Account other, String message_txt){
        if (this.messageable(other)){
            /* Message message = new Message(MessageManager.allmessage.size(), organizer.getUserId(), other.getUserId(),
                    message_txt); */
            Message message = MessageManager.createmessage(organizer.getUserId(), other.getUserId(), message_txt);
            organizer.addSentMessage(message.getmessageid());
            other.addInbox(message.getmessageid());
            System.out.println("Your message has been sent successfully.");

        }
        else if (!this.messageable(other)){
            System.out.println("Your message cannot be sent.");
        }
    }

    /**
     * Message all the attendee of the given talk.
     * @param organizer The organizer who want to send message.
     * @param accountList Hash Map of all accounts.
     * @param talk The selected talk.
     * @param message_txt The string of the message the organizer is going to send.
     */
    public void messageAllAttendee(Organizer organizer, HashMap<Integer, Account> accountList, Talk talk, String message_txt){
        ArrayList<Integer> Attendance = talk.getAttendeeId();
        int numberAttendee = 0;
        for (Account value : accountList.values()){
            if (value.getUserType() == 1 && Attendance.contains(value.getUserId())){
                Message message = MessageManager.createmessage(organizer.getUserId(), value.getUserId(), message_txt);
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
     * @param message_txt The string of the message the organizer is going to send.
     */
    public void messageAllSpeaker(Organizer organizer, HashMap<Integer, Account> accountList, String message_txt){
        int numberSpeaker = 0;
        for (Account value : accountList.values()) {
            if (value.getUserType() == 2) {
                Message message = MessageManager.createmessage(organizer.getUserId(), value.getUserId(), message_txt);
                organizer.addSentMessage(message.getmessageid());
                value.addInbox(message.getmessageid());
                numberSpeaker++;
            }
        }
        if (numberSpeaker == 0){System.out.println("There is no Speaker.");}
        else if (numberSpeaker > 0){System.out.println("Message has been sent to all Speakers.");}
    }

    /**
     * Remove a specific Talk from the Talk list.
     * @param talk The Talk that want to be removed.
     * @param talkList Hash Map of all Talks.
     * @return true iff the Talk has been removed.
     */
    public boolean removeTalk(Talk talk, HashMap<Integer, Talk> talkList){
        if (talkList.containsKey(talk.getTalkId())){
            talkList.remove(talk.getTalkId(), talk);
            /** TalkManager.totalTalkCount - 1; 怎么给total talk count — 1？？*/
            System.out.println("The Talk has been successfully removed.");
            return true;
        }else{
            System.out.println("No such Talk has been found.");
            return false;
        }
    }

    /**
     * Add a specific Talk with checking conflicts.
     * @param talk The Talk that want to be removed.
     * @param talkList Hash Map of all Talks.
     * @return true iff the Talk has been added.
     */
    public boolean addTalk(Talk talk, HashMap<Integer, Talk> talkList){
        ArrayList<Integer> startTime = new ArrayList<>();
        for(Talk value : talkList.values()){
            startTime.add(value.getStartTime());
        }
        if (! startTime.contains(talk.getStartTime()) && ! talk.getRoomId().isOccupiedAt(talk.getStartTime())){ /**这里要怎么通过room id来get room，是不是需要新写一个room manager？*/
            talkList.put(talk.getTalkId(), talk);
            System.out.println("The Talk has been successfully added.");
            return true;
        }else{
            System.out.println("There is a conflict with the time.");
            return false;
        }
    }

    /**
     * Reschedule a specific Talk with checking conflicts.
     * @param talk The Talk that want to be removed.
     * @param talkList Hash Map of all Talks.
     * @param startTime Start time of the Talk.
     * @param room  Room of the Talk.
     * @param speaker Speaker of the Talk.
     * @param title Title of the Talk.
     * @return true iff the Talk has been rescheduled.
     */
    public boolean rescheduleTalk(Talk talk, HashMap<Integer, Talk> talkList, int startTime, Room room, Speaker speaker, String title){
        if(!room.isOccupiedAt(startTime) /**并且speaker在这个时间是空闲的*/){
            talk.setRoomId(room.getRoomId());
            talk.setStartTime(startTime);
            talk.setSpeaker(speaker.getUserId());
            talk.setTalkTitle(title);
            System.out.println("The Talk has been successfully rescheduled.");
            return true;
        }else{
            System.out.println("The Talk cannot be rescheduled due to certain conflicts.");
            return false;
        }
    }

    /**
     * Create a speaker.
     * @param username The username of the Speaker.
     * @param password The password of the Speaker.
     * @param userType If it's a speaker, the number should be 2.
     * @param accountList List of all Accounts.
     * @return true iff the Speaker has been created successfully.
     */
    public boolean createSpeaker(String username, String password, int userType, HashMap<Integer, Account> accountList){
        if(userType != 2){
            return false;
        }else if(accountList.containsKey(accountList.size())){
            System.out.println("The Speaker already exists. ");
            return false;
        }else{
            Speaker newSpeaker = new Speaker(username, password, accountList.size());
            accountList.put(accountList.size(), newSpeaker);
            /**需要给Speaker创建talk list么？*/
            System.out.println("A new Speaker has been created.");
            return true;
            /**怎么把speaker放到speaker的hashmap里？*/
        }
    }

    /**
     * Schedule a speaker.
     * @param speaker The Speaker who is scheduling.
     * @param talk The talk the Speaker is going to be scheduled.
     * @param room The room the Speaker is going to have the talk.
     * @return true iff the Speaker has been scheduled successfully.
     */
    public boolean scheduleSpeaker(Speaker speaker, Talk talk, Room room){
        if(!room.isOccupiedAt(talk.getStartTime()) && !speaker.getTalkList().contains(talk.getTalkId())){
            for(int i = 0; i < speaker.getTalkList().size(); i++){
                if(TalkManager.getTalk(speaker.getTalkList().get(i)).getStartTime() == talk.getStartTime()){
                    return false;
                }/**查看Speaker现有talk的时间和当前talk是否会重合*/
            }
            talk.setSpeaker(speaker.getUserId());
            room.scheduleTalk(talk.getTalkId(), talk.getStartTime());
            System.out.println("The speaker has been successfully scheduled.");
            return true;
        }else{
            System.out.println("The Speaker cannot be scheduled due to conflicts.");
            return false;
        }
    }

    /**
     * Reschedule a speaker.
     * @param speaker The Speaker who is scheduling.
     * @param currentTalk The talk the Speaker is currently scheduled.
     * @param rescheduleTalk The talk the Speaker is going to be rescheduled.
     * @param rescheduleRoom The room the Speaker is going to be rescheduled to have the talk.
     * @return true iff the Speaker has been rescheduled successfully.
     */
    public boolean rescheduleSpeaker(Speaker speaker, Talk currentTalk, Talk rescheduleTalk, Room rescheduleRoom){
        for(int i = 0; i < speaker.getTalkList().size(); i++){
            if(speaker.getTalkList().get(i) == currentTalk.getRoomId()){
                speaker.getTalkList().remove(i);
            }
        }
        return OrganizerManager.scheduleSpeaker(speaker, rescheduleTalk, rescheduleRoom);
    }






}
