package Controller;

import UseCase.LoginManager;

public class OrganizerSystem {
    protected LoginManager loginM;

    public OrganizerSystem(LoginManager loginM){
        this.loginM = loginM;
    }
}
