package com.br.mobi.androidrest.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by fernando.rocha on 22/07/2014.
 */
public class PasswordUtils {

    public String geraHash(String password){
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        return passwordHash;
    }

    public boolean verificaPassword(String password){
        if(BCrypt.checkpw(password, geraHash(password))) {
            return true;
        }else return false;
    }

}
