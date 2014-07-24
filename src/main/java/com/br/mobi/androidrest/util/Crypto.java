package com.br.mobi.androidrest.util;

import com.br.mobi.androidrest.exceptions.UnauthorizedException;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Created by fernando.rocha on 24/07/2014.
 */
public class Crypto {
    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    public String encryptedPass(String pass) {
        String encryptedPassword = passwordEncryptor.encryptPassword(pass);
        return encryptedPassword;
    }


    public boolean checkEncrypted(String passEncrypted, String passOrigin) {
        if (passwordEncryptor.checkPassword(passOrigin, passEncrypted) == true) {
            return true;
        } else return false;
    }
}
