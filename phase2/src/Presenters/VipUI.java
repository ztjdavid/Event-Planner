package Presenters;

public class VipUI extends Presenter{

    public VipUI(ITextUI textUI){
        super(textUI);
    }


    public void startup() {
        printText("----------------VIPSystem-----------------\nHi, " +
                "VIP! Would you like to\n1 -> View, Attend, Leave event\n2 -> Send message \n3 -> Logout");
    }


    public void informinvalidchoice(){
        printText("Invalid Choice. Please try again.");
    }

    public void signUpTalk(){
        printText("----------------Signing up a talk-----------------\n" +
                "Please enter the id of the talk that you want to sign up");
    }

    public void signUpSuc(){
        printText("You have signed up a new talk.");
    }

    public void cancelTalk(){
        printText("----------------Cancelling a talk-----------------\n" +
                "Please enter the id of the talk that you want to cancel");
    }

    public void cancelSuc(){
        printText("You have canceled one of your talks.");
    }

    public void msgSelect(){
        printText("----------------Message Interface-----------------\nHi, " +
                "Would you like to\n1 -> Send Message to a attendee\n2 -> Send a message to the speaker of a talk\n" +
                "3 -> View your inbox\n" +
                "4 -> Read your replies and send message to repliers\n" +
                "5 -> Read your messages and reply to senders\n" +
                "6 -> Go back.");////////////ERICMODIFY
    }

    public void eventmain(){
        printText("----------------Event Interface-----------------\nHi, " +
                "Would you like to\n1 -> Sign Up a New Event\n2 -> View All My Event\n" +
                "3 -> Leave an Event\n" +
                "4 -> Go back");

    }
    public void eventselect(){
        printText("----------------Sign Up Interface-----------------\nHi, " +
                "Which type of event you would like to register\n1 -> Talk\n2 -> Discussion\n" +
                "3 -> Party\n" +
                "4 -> VIP Talk\n" +
                "5 -> VIP Discussion\n" +
                "6 -> VIP Party\n" + "7 -> Go back"
                );////////////ERICMODIFY
    }
    /////////////

    public void show(String a){printText(a);}

    public void informEnteringText(){
        printText("Please Enter Your Message.\n " +
                "(End editing by typing a single \"end\" in a new line.)");
    }
    public String getLineTxt(){
        return requestInput();
    }

    public void messagesend(){ printText("Message sent\n"); }

    public void askForBack(){
        requestInput("\nPress enter to go back.");
    }
    /////////ERICMODIFY
    public String getrequest(int s){
        switch (s){
            case 1:
                printText("Please Enter Your Response.");
                break;
            case 2:
                printText("Please Enter Your Response(Enter -1 to go back.)");
                break;
            case 3:
                printText("Please Enter The Valid ID of the Event(Enter -1 to go back.): ");
                break;
            case 4:
                printText("Please Enter The Valid ID of one of your Event(Enter -1 to go back.): ");
                break;
        }
        return requestInput();
    }


    public void announcereply(){printText("Please enter the id of the message " +
            "if you want to reply to.");
    }

    public void announcemsg(){printText("Please enter the id of the replier " +
            "if you want to message to.");
    }

    //////////LouisaModify
    public void signUpVipTalk(){
        printText("This is an Vip Event.");
    }

    public void informNotVip(){
        printText("You do not have the permission to sign up for a VIP event.");
    }


}

