package it.soundmate.model;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private int applicationCode;
    private int bandId;
    private List<Integer> appliedSoloList = new ArrayList<>();
    private List<String> instrumentsList = new ArrayList<>(); //instruments the band are looking for
    private String message;

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

    public List<Integer> getAppliedSoloList() {
        return appliedSoloList;
    }

    public void setAppliedSoloList(List<Integer> solosId){ this.appliedSoloList = solosId;}

    public void addSolo(Integer soloID){
        this.appliedSoloList.add(soloID);
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

}
