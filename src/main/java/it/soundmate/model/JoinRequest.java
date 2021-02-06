/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 30/01/21, 16:06
 * Last edited: 30/01/21, 16:06
 */

package it.soundmate.model;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;

public class JoinRequest  {

    private int code;
    private int codeApplication;
    private int idBand;
    private int idSolo;
    private String message;
    private RequestState requestState;
    private SoloResultBean soloResultBean;
    private BandResultBean band;


    public JoinRequest() {
    }

    public JoinRequest(int idBand, int codeApplication, int idSolo, String message) {
        this.idBand = idBand;
        this.codeApplication = codeApplication;
        this.idSolo = idSolo;
        this.message = message;
    }

    public JoinRequest withCode(int code){
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setMessage(this.message);
        joinRequest.setCodeApplication(this.codeApplication);
        joinRequest.setIdBand(this.idBand);
        joinRequest.setIdSolo(this.idSolo);
        joinRequest.setRequestState(this.requestState);
        joinRequest.setCode(code);
        return joinRequest;
    }


    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCodeApplication() {
        return codeApplication;
    }

    public void setCodeApplication(int codeApplication) {
        this.codeApplication = codeApplication;
    }

    public int getIdBand() {
        return idBand;
    }

    public void setIdBand(int idBand) {
        this.idBand = idBand;
    }
    public int getIdSolo() {
        return idSolo;
    }

    public void setIdSolo(int idSolo) {
        this.idSolo = idSolo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SoloResultBean getSoloResultBean() {
        return soloResultBean;
    }

    public void setSoloResultBean(SoloResultBean soloResultBean) {
        this.soloResultBean = soloResultBean;
    }

    public BandResultBean getBand() {
        return band;
    }

    public void setBand(BandResultBean band) {
        this.band = band;
    }
}


