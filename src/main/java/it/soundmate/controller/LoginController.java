package it.soundmate.controller;

public class LoginController {

    public boolean checkFields(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}

