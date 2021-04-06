package com.github.boyarsky1997.store.model;

public class Buyer extends User {
    public Buyer() {
        super(Role.BUYER);
    }

    public Buyer(Integer id, String login, String password, String name, String surname) {
        super(id, Role.BUYER, login, password, name, surname);
    }
}
