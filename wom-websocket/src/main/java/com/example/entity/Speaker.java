package com.example.entity;

import java.security.Principal;

public class Speaker implements Principal {
    String username;

    public Speaker(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
