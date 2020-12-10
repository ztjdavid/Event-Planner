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
    protected RoomManager roomM;
    protected OrganizerPresenter organizerPresenter;
    protected StrategyManager strategyM;
    protected AccountManager accM;
    protected EventManager eventM;
    protected MessageManager MsgM;
    protected AttendeeManager attM;
    protected RequestManager rqstM;
    protected VIPManager vipM;
    protected StartP startP;
    protected SignInP signInP;
    protected SignUpP signUpP;
    protected AttendeeUI attUI;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;
    protected ApplicationManager appM;
    protected ITextUI textUI;
    protected VipUI vipUI;
    protected UserFileLoader userL;
    protected MsgFileLoader msgL;
    protected EventFileLoader eventL;
    protected RoomFileLoader roomL;
    protected RequestFileLoader requestL;
    protected ApplicationLoader appL;
    protected UserFileWriter userW;
    protected MsgFileWriter msgW;
    protected EventFileWriter eventW;
    protected RoomFileWriter roomW;
    protected RequestFileWriter requestW;
    protected ApplicationWriter appW;


    public AppSystem(ITextUI textUI){
        this.textUI = textUI;
        this.startP = new StartP(textUI);
        this.signInP = new SignInP(textUI);
        this.signUpP = new SignUpP(textUI);
        this.speakerUI = new SpeakerUI(textUI);
        this.attUI = new AttendeeUI(textUI);
        this.vipUI = new VipUI(textUI);
        this.organizerPresenter = new OrganizerPresenter(textUI);
        try{
            this.userW = new UserFileWriter("phase2/DataBase/UserData.ini");
            this.msgW = new MsgFileWriter("phase2/DataBase/MsgData.ini");
            this.eventW = new EventFileWriter("phase2/DataBase/EventData.ini");
            this.roomW = new RoomFileWriter("phase2/DataBase/RoomData.ini");
            this.requestW = new RequestFileWriter("phase2/DataBase/RequestData.ini");
            this.appW = new ApplicationWriter("phase2/DataBase/ApplicationData.ini");
        }catch (IOException ignored){}
        this.attM = new AttendeeManager(userW);
        this.accM = new AccountManager(userW);
        this.vipM = new VIPManager(userW);
        this.ognM = new OrganizerManager(userW);
        this.spkM = new SpeakerManager(userW);
        this.MsgM = new MessageManager(msgW);
        this.eventM = new EventManager(eventW);
        this.roomM = new RoomManager(roomW);
        this.rqstM = new RequestManager(requestW);
        this.appM = new ApplicationManager(appW);
        this.strategyM = new StrategyManager();
        this.signInS = new SignInSystem(accM, signInP);
        this.signUpS = new SignUpSystem(accM, signUpP, strategyM);
        this.attendeeS = new AttendeeSystem(accM, eventM, MsgM, attUI, strategyM, attM, roomM, rqstM, appM);
        this.organizerS = new OrganizerSystem(accM, MsgM, organizerPresenter, strategyM, ognM, spkM, eventM, roomM, rqstM);
        this.speakerS = new SpeakerSystem(accM, eventM, MsgM, speakerUI, strategyM, spkM, roomM, rqstM);
        this.vipsystem = new VipSystem(accM, eventM, MsgM, vipUI, strategyM, vipM, roomM, rqstM, appM);
        try{
            this.userL = new UserFileLoader("phase2/DataBase/UserData.ini", accM, attM, spkM, ognM, vipM);
            this.msgL = new MsgFileLoader("phase2/DataBase/MsgData.ini", MsgM);
            this.eventL = new EventFileLoader("phase2/DataBase/EventData.ini", eventM);
            this.roomL = new RoomFileLoader("phase2/DataBase/RoomData.ini", roomM);
            this.requestL = new RequestFileLoader("phase2/DataBase/RequestData.ini", rqstM);
            this.appL = new ApplicationLoader("phase2/DataBase/ApplicationData.ini", appM);
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
            roomL.loadData();
            eventL.loadData();
            requestL.loadData();
            appL.loadData();
        }catch (NumberFormatException ignored) {}

        // start
        int userChoice;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        String invalidInfo = "Please select one operation from below:\n1 -> login\n2 -> signup\n3 -> End Program";
        do{
            startP.startup();
            userChoice = startP.chooseOption(validChoices, "Your choice:", invalidInfo);
            if (userChoice != 3){
                int currAccountType = enterBranch(userChoice);
                enterSystems(currAccountType);
            }
        }while(userChoice != 3);
        startP.informQuiting();
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

}
