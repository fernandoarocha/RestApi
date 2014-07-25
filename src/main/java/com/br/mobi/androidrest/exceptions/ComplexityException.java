package com.br.mobi.androidrest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by fernando.rocha on 25/07/2014.
 */
public class ComplexityException extends WebApplicationException {

    public ComplexityException(String message){
        super (Response.status(Response.Status.NOT_ACCEPTABLE).entity(message).type("application/json").build());
    }
}
