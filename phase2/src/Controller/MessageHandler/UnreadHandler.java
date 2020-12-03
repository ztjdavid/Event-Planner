package Controller.MessageHandler;

import Entity.Message;
import Entity.Speaker;
import Presenters.SpeakerUI;
import UseCase.*;

import java.util.ArrayList;

public class UnreadHandler {
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected SpeakerManager SpeakerM;

    public UnreadHandler(MessageManager MsgM, SpeakerUI speakerUI, SpeakerManager SpeakerM) {
        this.MsgM = MsgM;
        this.speakerUI = speakerUI;
        this.SpeakerM = SpeakerM;
    }

    public ArrayList<Message> getAllUnread(int speakerId) {
        Speaker acc = (Speaker) SpeakerM.getAccountWithId(speakerId);
        ArrayList<Integer> inbox = acc.getInbox();
        ArrayList<Message> unread = new ArrayList<>();
        for (Integer i : inbox) {
            Message msg = MsgM.getmessage(i);
            if (!msg.getReadStatus()) {
                unread.add(msg);
            }
        }
        return unread;
    }

    public ArrayList<Message> getAllRead(int speakerId) {
        Speaker acc = (Speaker) SpeakerM.getAccountWithId(speakerId);
        ArrayList<Integer> inbox = acc.getInbox();
        ArrayList<Message> read = new ArrayList<>();
        for (Integer i : inbox) {
            Message msg = MsgM.getmessage(i);
            if (!msg.getReadStatus()) {
                read.add(msg);
            }
        }
        return read;
    }

}