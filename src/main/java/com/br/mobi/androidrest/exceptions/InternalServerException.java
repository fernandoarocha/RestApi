package com.br.mobi.androidrest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by fernando.rocha on 25/07/2014.
 */
public class InternalServerException extends WebApplicationException {

    /**
     * NOT FOUND Exception 404
     */
    public InternalServerException() {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     *
     *
     */
    public InternalServerException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).type("application/json").build());
    }
}
