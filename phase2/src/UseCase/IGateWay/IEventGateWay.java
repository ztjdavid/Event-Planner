package UseCase.IGateWay;

import java.io.IOException;
import java.util.ArrayList;

public interface IEventGateWay {
    public void writeNewEvent(int id, String talkTitle, int startTime, int roomId,
                              ArrayList<Integer> speakerList, int eventCapacity,
                              int duration, boolean isVip)throws IOException;
    public void updateAttendeeList(int id, ArrayList<Integer> attList)throws IOException;
    public void updateRemainingSeat(int id, int rs)throws IOException;//////NOT YET ADDRESSED
    public void updateSeatsOccupied(int id, int so)throws IOException;
    public void removeEvent(int id)throws IOException;
    public void updateTalkTitle(int id, String title)throws IOException;

}
