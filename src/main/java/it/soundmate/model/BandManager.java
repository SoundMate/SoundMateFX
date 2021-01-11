/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;


import java.util.List;

public class BandManager extends User {

    private String firstName;
    private String lastName;
    private List<Band> managedBands;
    private static final UserType userType = UserType.BAND_MANAGER;

    public BandManager(int id, String fName, String lName, String password){
        super.setId(id);
        this.setFirstName(fName);
        this.setLastName(lName);
        super.setPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Band> getManagedBands() {
        return managedBands;
    }

    public void setManagedBands(List<Band> managedBands) {
        this.managedBands = managedBands;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }
}
