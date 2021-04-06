package com.github.boyarsky1997.store.model;

public class Admin extends User {
    public Admin() {
        super(Role.ADMIN);
    }

    public Admin(Integer id, String login, String password, String name, String surname) {
        super(id, Role.ADMIN, login, password, name, surname);
    }
}
