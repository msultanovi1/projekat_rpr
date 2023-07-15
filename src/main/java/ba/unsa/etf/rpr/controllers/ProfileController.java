package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.User;

public class ProfileController {

    private User user;

    public ProfileController(User user){
        this.user = user;
    }
}
