package Controller;
import Presenters.*;
import UseCase.*;
import Gateways.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AppSystem {
    protected SignInSystem signInS;
    protected SignUpSystem signUpS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected VipSystem vipsystem;
    protected SpeakerSystem speakerS;
    protected SpeakerUI speakerUI;
    protected RoomManager roomM = new RoomManager();
    protected OrganizerPresenter organizerPresenter;
    protected StrategyManager strategyM;
    protected AccountManager accM;
    protected EventManager eventM;
    protected MessageManager MsgM;
    protected AttendeeManager attM = new AttendeeManager();
    protected RequestManager rqstM = new RequestManager();
    protected VIPManager vipM = new VIPManager();
    protected StartUI startUI;
    protected SignInUI signInUI;
    protected SignUpUI signUpUI;
    protected AttendeeUI attUI;
    protected OrganizerManager ognM = new OrganizerManager();
    protected SpeakerManager spkM = new SpeakerManager();
    protected ITextUI textUI;
    protected VipUI vipUI;
    protected VIPsystemhandler vh;
    protected Attendeesystemhandler ah;
    protected OrganizerSystemHandler oh;
    protected SpeakerSystemHandler sh;
    protected UserFileLoader userL;
    protected MsgFileLoader msgL;
    protected EventFileLoader eventL;
    protected UserFileWriter userW;
    protected MsgFileWriter msgW;
    protected EventFileWriter eventW;



    public AppSystem(ITextUI textUI){
        this.textUI = textUI;
        this.startUI = new StartUI();
        this.signInUI = new SignInUI();
        this.signUpUI = new SignUpUI();
        this.speakerUI = new SpeakerUI(textUI);
        this.attUI = new AttendeeUI(textUI);
        this.vipUI = new VipUI(textUI);
        this.organizerPresenter = new OrganizerPresenter(textUI);
        try{
            this.userW = new UserFileWriter("phase2/DataBase/UserData.ini");
            this.msgW = new MsgFileWriter("phase2/DataBase/MsgData.ini");
            this.eventW = new EventFileWriter("phase2/DataBase/EventData.ini");
        }catch (IOException ignored){}
        this.accM = new AccountManager(userW);
        this.MsgM = new MessageManager(msgW);
        this.eventM = new EventManager(eventW);
        this.strategyM = new StrategyManager();
        this.vh = new VIPsystemhandler(accM, eventM, MsgM, vipUI, strategyM, vipM, roomM, rqstM);
        this.oh = new OrganizerSystemHandler(accM, MsgM, strategyM, ognM, spkM, eventM, roomM, organizerPresenter, rqstM);
        this.signInS = new SignInSystem(accM, signInUI);
        this.signUpS = new SignUpSystem(accM, signUpUI, strategyM);
        this.attendeeS = new AttendeeSystem(accM, eventM, MsgM, attUI, strategyM, attM, roomM, rqstM, ah);
        this.organizerS = new OrganizerSystem(accM, MsgM, organizerPresenter, strategyM, ognM, spkM, eventM, roomM, oh, rqstM);
        this.speakerS = new SpeakerSystem(accM, eventM, MsgM, speakerUI, strategyM, spkM, roomM, rqstM, sh);
        this.vipsystem = new VipSystem(accM, eventM, MsgM, vipUI, strategyM, vipM, roomM, rqstM, vh);
        this.ah = new Attendeesystemhandler(accM, eventM, MsgM, attUI, strategyM, attM, roomM, rqstM);
        this.sh = new SpeakerSystemHandler(accM, eventM, MsgM, speakerUI, strategyM, spkM, roomM, rqstM);
        try{
            this.userL = new UserFileLoader("phase2/DataBase/UserData.ini", accM);
            this.msgL = new MsgFileLoader("phase2/DataBase/MsgData.ini", MsgM);
            this.eventL = new EventFileLoader("phase2/DataBase/EventData.ini", eventM);
        }catch (IOException ignored){}

    }

    /**
     * Start the whole program and guide users to sign up or sign in.
     */
    public void run(){
        // scan files
        try{
            userL.loadData();
            msgL.loadData();
        }catch (NumberFormatException ignored) {}

        // start
        int userChoice;
        do{
            startUI.startup();
            userChoice = chooseMode();
            if (userChoice != 4){
                int currAccountType = enterBranch(userChoice);
                enterSystems(currAccountType);
            }
        }while(userChoice != 4);
        startUI.informQuiting();
    }



    //Helper methods:
    private int enterBranch(int userInput){
        switch (userInput){
            case 1:
                return signInS.run();
            case 2:
                signUpS.run();
                return signInS.run();
            default:
                return -1;
        }
    }

    private void enterSystems(int currAccountType){
        switch (currAccountType){
            case 0:
                organizerS.run();
                break;
            case 1:
                attendeeS.run();
                break;
            case 2:
                speakerS.run();
                break;
            case 3:
                vipsystem.run();
                break;
            default:
                break;
        }
    }

    private int chooseMode(){
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = startUI.requestModeSelection();
            if (!strategyM.isValidChoice(userInput, validChoices))
                startUI.informInvalidInput();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    /**
     * Show all Talks with each Event's title, ID, start time, room name, and room ID.
     */
    public void readAllEvents(){
        organizerPresenter.message7();
        ArrayList<Integer> talkLst = new ArrayList<>(eventM.getAllEvents());
        for(int item:talkLst){
            String title = eventM.getTitle(item);
            int startTime = eventM.getStartTime(item);
            String roomName = roomM.getRoomName(eventM.getRoomIdWithId(item));
            int roomId = eventM.getRoomIdWithId(item);
            int type = eventM.getEventTypeWithID(item);
            int duration = eventM.getDuration(item);
            ArrayList<Integer> speaker = new ArrayList<>(eventM.getSpeakerOfEvent(item));
            ArrayList<Integer> attendee = new ArrayList<>(eventM.getAttendeeOfEvent(item));
            organizerPresenter.readTalks(title, item, startTime, roomName, roomId, type, speaker, attendee, duration);
        }
    }


}
