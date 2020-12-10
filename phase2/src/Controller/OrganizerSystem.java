package Controller;
import Presenters.OrganizerPresenter;
import UseCase.*;


public class OrganizerSystem {
    protected AccountManager accM;
    protected MessageManager MsgM;
    protected StrategyManager strategyM;
    protected OrganizerPresenter organizerPresenter;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;
    protected EventManager eventM;
    protected RoomManager roomM;
    protected OrganizerSystemHandler oh;
    protected RequestManager rqstM;
    protected ApplicationManager appM;

    public OrganizerSystem(AccountManager accM, MessageManager MsgM, OrganizerPresenter organizerPresenter, StrategyManager strategyM,
                           OrganizerManager ognM, SpeakerManager spkM, EventManager eventM, RoomManager roomM, RequestManager rqstM, ApplicationManager appM) {
        this.accM = accM;
        this.MsgM = MsgM;
        this.strategyM = strategyM;
        this.organizerPresenter = organizerPresenter;
        this.ognM = ognM;
        this.spkM = spkM;
        this.eventM = eventM;
        this.roomM = roomM;
        this.oh = new OrganizerSystemHandler(accM, MsgM, strategyM, ognM, spkM, eventM, roomM, organizerPresenter, rqstM, appM);
        this.rqstM = rqstM;
        this.appM = appM;
    }

    /**
     * Run Organizer System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            organizerPresenter.startup();
            userChoice = organizerPresenter.chooseOption(oh.getChoiceList(7),
                    "Please Choose a Dashboard:", "Invalid Choice! Please Try Again:");
            enterBranch(userChoice);
        } while (userChoice != 7);
    }

    //Helper methods:
    /**
     * Enter the Branch according to user's choice in the startup menu.
     * @param userChoice an int chosen by user.
     */
    private void enterBranch(int userChoice) {
        switch (userChoice) {
            case 1:
                msgDashboard();
                break;
            case 2:
                speakerDashboard();
                break;
            case 3:
                talkDashBoard();
                break;
            case 4:
                roomDashBoard();
                break;
            case 5:
                requestDashBoard();
            case 6:
                applicationDashBoard();
                break;
            case 7:
                break;
        }
    }

    private void applicationDashBoard(){
        int userInput;
        do{
            organizerPresenter.messaging5();
            userInput = organizerPresenter.chooseOption(oh.getChoiceList(3),
                    "Please Choose a Option:", "Invalid Choice! Please Try Again:");
            ApplicationOp(userInput);
        }while(userInput != 3);
    }

    private void ApplicationOp(int userInput){
        switch (userInput){
            case 1:
                oh.readApplication();
                break;
            case 2:
                oh.markApplication();
                break;
            default:
                break;
        }
    }

    private void requestDashBoard(){
        int userInput;
        do{
            organizerPresenter.messaging1();
            userInput = organizerPresenter.chooseOption(oh.getChoiceList(3),
                    "Please Choose a Option:", "Invalid Choice! Please Try Again:");
            requestOp(userInput);
        }while(userInput != 3);
    }

    private void requestOp(int userInput) {
        switch (userInput) {
            case 1:
                oh.readAllRequest();
                break;
            case 2:
                oh.changeStatus();
                break;
            default:
                break;
        }
    }

    /**
     * Run Room dashboard, user can choose according to messaging4 options.
     */
    private void roomDashBoard(){
        int userInput;
        do{
            organizerPresenter.messaging4();
            userInput = organizerPresenter.chooseOption(oh.getChoiceList(3),
                    "Please Choose a Option:", "Invalid Choice! Please Try Again:");
            roomOp(userInput);
        }while(userInput != 3);
    }

    /**
     * Enter the Room Operation according to user input from the Room dashboard.
     * @param userInput an int chosen by user.
     */
    private void roomOp(int userInput){
        switch (userInput){
            case 1:
                oh.doCreateRoom();
                break;
            case 2:
                oh.readAllRooms();
                break;
            default:
                break;
        }
    }

    /**
     * Run Event dashboard, user can choose according to messaging3 options.
     */
    private void talkDashBoard(){
        int userInput;
        do{
            organizerPresenter.messaging3();
            userInput = organizerPresenter.chooseOption(oh.getChoiceList(6),
                    "Please Choose a Option:", "Invalid Choice! Please Try Again:");
            tlkOp(userInput);
        }while(userInput != 6);
    }

    /**
     * Enter the Event Operation according to user input from the Event dashboard.
     * @param userInput an int chosen by user.
     */
    private void tlkOp(int userInput){
        switch (userInput){
            case 1:
                oh.doCreateTalk();
                organizerPresenter.askForBack();
                break;
            case 2:
                oh.doCreateDiscussion();
                organizerPresenter.askForBack();
                break;
            case 3:
                oh.doCreateParty();
                organizerPresenter.askForBack();
                break;
            case 4:
                oh.doCancelEvent();
                organizerPresenter.askForBack();
                break;
            case 5:
                oh.readAllEvents();
                organizerPresenter.askForBack();
                break;
            case 6:
                break;
        }
    }

    /**
     * Run Speaker dashboard, user can choose according to messaging2 options.
     */
    private void speakerDashboard() {
        int userInput;
        do {
            organizerPresenter.messaging2();
            userInput = organizerPresenter.chooseOption(oh.getChoiceList(5),
                    "Please Choose a Option:", "Invalid Choice! Please Try Again:");
            sprOp(userInput);
        } while (userInput != 5);
    }

    /**
     * Enter the Speaker Operation according to user input from the Speaker dashboard.
     * @param userInput an int chosen by user.
     */
    private void sprOp(int userInput) {
        switch (userInput) {
            case 1:
                oh.doCreateSpeaker();
                break;
            case 2:
                oh.doCreateAttendee();
                break;
            case 3:
                oh.doCreateVIP();
                break;
            case 4:
                oh.readAllAccounts();
                break;
            case 5:
                break;
        }
    }

    private void msgDashboard() {
        int userInput;
        do {
            organizerPresenter.messaging();
            userInput = organizerPresenter.chooseOption(oh.getChoiceList(8),
                    "Please Choose a Option:", "Invalid Choice! Please Try Again:");
            messageOp(userInput);
        } while (userInput != 8);
    }

    private void messageOp(int userInput) {
        switch (userInput) {
            case 1:
                oh.messageToAttendee();
                break;
            case 2:
                oh.messageToSpeaker();
                break;
            case 3:
                oh.messageToAllSpeaker();
                break;
            case 4:
                oh.messageToAllAttendee();
                break;
            case 5:
                oh.replyToMsg();
                break;
            case 6:
                oh.readAllMsg();
                break;
            case 7:
                oh.readArchivedMsg();
                break;
            case 8:
                break;
        }
    }


}