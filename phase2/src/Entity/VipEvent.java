package Entity;

import java.util.ArrayList;

public class VipEvent extends Event {

    public VipEvent(int talkId, String talkTitle, int startTime, int roomId, ArrayList<Integer> speakerID,
                    int eventCapacity, int duration, boolean isVip){
        super(talkId, talkTitle, startTime, roomId, speakerID, eventCapacity, duration, isVip);
    }


}
