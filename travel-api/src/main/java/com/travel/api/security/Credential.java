package com.travel.api.security;

import java.io.Serializable;

/**
 * Created by lusouza on 22/07/18.
 */
public interface Credential extends Serializable {

    public String getLogin();

    public String getPassword();
}
