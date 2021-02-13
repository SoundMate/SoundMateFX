package it.soundmate.model;

import it.soundmate.bean.searchbeans.SoloResultBean;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private int applicationCode;
    private int bandId;
    private List<Integer> appliedSoloListID = new ArrayList<>();
    private List<String> instrumentsList = new ArrayList<>(); //instruments the band are looking for
    private String message;
    private List<SoloResultBean> appliedSoloList = new ArrayList<>();
    private List<JoinRequest> joinRequestList = new ArrayList<>();

    public Application() {
    }

    public Application(int bandId, String instrument, String message) {
        this.bandId = bandId;
        this.instrumentsList.add(instrument);
        this.message = message;
    }

    public Application withCode(int code){
        Application application = new Application();
        application.setBand(this.bandId);
        application.setMessage(this.message);
        application.setApplicationCode(code);
        return application;
    }

    public int getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(int applicationCode) {
        this.applicationCode = applicationCode;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBand(int bandId) {
        this.bandId = bandId;
    }

    public List<Integer> getAppliedSoloListID() {
        return appliedSoloListID;
    }

    public void setAppliedSoloListID(List<Integer> solosId){ this.appliedSoloListID = solosId;}

    public void addSolo(Integer soloID){
        this.appliedSoloListID.add(soloID);
    }

    public List<String> getInstrumentsList() {
        return instrumentsList;
    }

    public void setInstrumentsList(List<String> instrumentsList) {
        this.instrumentsList = instrumentsList;
    }

    public void addInstrument(String instrument){
        this.instrumentsList.add(instrument);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SoloResultBean> getAppliedSoloList() {
        return appliedSoloList;
    }

    public void setAppliedSoloList(List<SoloResultBean> appliedSoloList) {
        this.appliedSoloList = appliedSoloList;
    }

    public List<JoinRequest> getJoinRequestList() {
        return joinRequestList;
    }

    public void setJoinRequestList(List<JoinRequest> joinRequestList) {
        this.joinRequestList = joinRequestList;
    }
}
