package com.travel.api.security;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by lusouza on 22/07/18.
 */

public class User {

    private Long id;
    private String login;
    private String password;

    public User() {}

    public User(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}