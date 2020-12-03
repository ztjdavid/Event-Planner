package Controller;

import Entity.Message;
import Entity.Speaker;
import Presenters.SpeakerUI;
import UseCase.*;

import java.util.ArrayList;

public class SpeakerSystemHandler {
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected SpeakerManager SpeakerM;

    public SpeakerSystemHandler(MessageManager MsgM, SpeakerUI speakerUI, SpeakerManager SpeakerM) {
        this.MsgM = MsgM;
        this.speakerUI = speakerUI;
        this.SpeakerM = SpeakerM;
    }

    public ArrayList<Integer> getAllUnread(int speakerId) {
        Speaker acc = (Speaker) SpeakerM.getAccountWithId(speakerId);
        ArrayList<Integer> inbox = acc.getInbox();
        ArrayList<Integer> unread = new ArrayList<>();
        for (Integer i : inbox) {
            Message msg = MsgM.getmessage(i);
            if (!msg.getReadStatus()) {
                unread.add(msg.getmessageid());
            }
        }
        return unread;
    }

    public ArrayList<Integer> getAllRead(int speakerId) {
        Speaker acc = (Speaker) SpeakerM.getAccountWithId(speakerId);
        ArrayList<Integer> inbox = acc.getInbox();
        ArrayList<Integer> read = new ArrayList<>();
        for (Integer i : inbox) {
            Message msg = MsgM.getmessage(i);
            if (!msg.getReadStatus()) {
                read.add(msg.getmessageid());
            }
        }
        return read;
    }

}