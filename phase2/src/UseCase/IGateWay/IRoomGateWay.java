package UseCase.IGateWay;

import java.io.IOException;
import java.util.HashMap;

public interface IRoomGateWay {
    public void writeNewRoom(int id, String roomName, int roomC)throws IOException;
    public void updateTimetable(int id, HashMap<Integer, Integer> timetable)throws IOException;
}
