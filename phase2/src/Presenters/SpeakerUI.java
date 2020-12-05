package Presenters;

public class SpeakerUI extends Presenter{

    public SpeakerUI(ITextUI textUI) {
        super(textUI);
    }


    public void startup(){
        printText("----------------SpeakerSystem-----------------\n" +
                "Hi, Speaker! Would you like to\n1 -> Read Your Talks\n2 -> Message\n3 -> Logout");
    }

    public String getrequest(int s){
        switch (s){
            case 1:
                printText("Please Enter Your Response.");
                break;
            case 2:
                printText("Please Enter Your Response(Enter -1 to go back.)");
                break;
        }
        return requestInput();
    }

    public void informinvalidchoice(){
        printText("Invalid Choice. Please try again.");

    }
    public void messaging(){
        printText("----------------Messaging-----------------");
        printText("Hi, Speaker! Would you like to:");
        printText("1 -> Message to an attendee.\n" +
                            "2 -> Message to attendees in one talk\n" +
                            "3 -> Message all attendees who register your talks\n" +
                            "4 -> Read your replies and send message to repliers\n" +
                            "5 -> Read your messages and reply to senders\n"+
                            "6 -> Read all unread message.\n"+
                "6 -> Go back.");
    }

    public void eventdb(){
        printText("---------------Event-------------\nHi Speaker! Would you like to:" +
                "\n1 -> Read all information about your events" +
                "\n2 -> Your Request\n3 -> Go back");
    }
    public void requestdb(){
        printText("--------------Your Request------------\nHi Speaker! Would you like to:" +
                "\n1 -> Read all my requests with status" +
                "\n2 -> Send new request\n3 -> Go back");
    }


    public void informEnteringText(){
        printText("Please Enter Your Message.\n " +
                "(End editing by typing a single \"end\" in a new line.)");
    }

    public String getrequest2(){
        printText("Please Enter the ID of ONE Event. Enter -1 to cancel and go back. ");
        return requestInput();
    }

    public void messagesend(){
        printText("Message Send\n");

    }
    public void noattendees(){
        printText("No Attendees");

    }
    public void stopmessaging(){
        printText("Stop Messaging");
    }

    public void stoprequest(){printText("Stop Request");}

    public void show(String a){printText(a + "\n");}

    public void announcereply(){printText("Please enter the id of the message " +
            "if you want to reply to.");
    }

    public void announcemsg(){printText("Please enter the id of the replier " +
            "if you want to message to.");
    }
    ///// Louisa added
    public void annouceUnread(){printText("Please enter the id of the message" +
            "if you want to read.");}

     public void unreadSuccess(int messageId){printText("Your message with id: " + messageId +
             " has been succesfully read!");}
    /////

    public void askForBack(){
        requestInput("\nPress enter to go back.");
    }

    public String confirmMsgAll(){
        printText("Are you sure to message all attendees in this system?" +
                "\nEnter 1 to confirm, 0 to cancel and go back.(Irreversible once confirmed.)");
        return requestInput();
    }

    public String getLineTxt(){
        return requestInput();
    }


}
