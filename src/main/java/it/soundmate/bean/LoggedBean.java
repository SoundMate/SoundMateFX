package it.soundmate.bean;

import it.soundmate.model.UserType;

public class LoggedBean {

    private final UserType userType;
    private final int userID;
    private boolean queryResult = false;

    public LoggedBean(final String userType,final int userID){
        this.userType = UserType.returnUserType(userType);
        this.userID = userID;
    }
    public LoggedBean(){
        this.userType = null;
        this.userID = 0;
        this.queryResult = false;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isQueryResult() {
        return queryResult;
    }

    public void setQueryResult(boolean queryResult) {
        this.queryResult = queryResult;
    }
}
