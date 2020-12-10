package UseCase.IGateWay;

import java.io.IOException;

public interface IApplicationGateWay {
    void writeNewApplication(int appId, int applicatorId, String applicatorName, String description)
            throws IOException;
    void updateStatus(int appId, boolean status)throws IOException;

    void updateNewUsername(int appId, String username)throws IOException;

    void updateNewPassword(int appId, String passw)throws IOException;
}
