package com.travel.api.security;

/**
 * Created by lusouza on 22/07/18.
 */
class CredentialDTO implements Credential {

    private static final long serialVersionUID = 5222103920384128831L;

    private String login;
    private String password;

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }
}