package Controller;
import Entity.*;
import UI.AttendeeUI;
import UI.OrganizerUI;
import UseCase.*;

import java.util.ArrayList;

public class AccountSystem {
    protected AccountManager accountM;
    protected LoginManager loginM;
    protected MessageManager MsgM;
    protected Organizer currOrganizer;
    protected Attendee currAttendee;
    protected AttendeeUI attendeeUI;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;
    protected TalkManager tlkM;
    protected RoomManager roomM;

    public AccountSystem(AccountManager accountM, LoginManager loginM) {
        this.accountM = accountM;
        this.loginM = loginM;
    }

    public void run(){

    }

    public void readFile(){}
}
