/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 04/02/21, 18:24
 * Last edited: 04/02/21, 18:24
 */

package it.soundmate.bean.messagebeans;

public class SoloMessageBean extends UserMessageBean {

    private final String firstName;
    private final String lastName;

    public SoloMessageBean(int id, String email, String firstName, String lastName, String encodedImg) {
        super(id, email, firstName+" "+lastName, encodedImg);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
