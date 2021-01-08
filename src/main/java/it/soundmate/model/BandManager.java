/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.ArrayList;
import java.util.List;

public class BandManager extends User {

    private ArrayList<Band> managedBands;

    public BandManager(User user) {
        super(user.getUserID(), user.getEmail(), user.getPassword(), user.getEncodedImg(), UserType.BAND_MANAGER);
    }

    public BandManager(User user, List<Band> bands) {
        this(user);
        this.managedBands = new ArrayList<>();
        this.managedBands.addAll(bands);
    }

    public void addBandManagement(Band band){
        if (this.managedBands == null) {
            this.managedBands = new ArrayList<>();
        }
        this.managedBands.add(band);
    }

    public void removeBandManagement(Band band){
        for (int i = 0; i < this.managedBands.size(); i++) {
            if (band.equals(this.managedBands.get(i))) {
                this.managedBands.remove(i);
                break;
            }
        }
    }

    public List<Band> getManagedBands(){
        return managedBands;
    }
}
