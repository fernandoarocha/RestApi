package com.br.mobi.androidrest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by fernando.rocha on 25/07/2014.
 */
public class NotFoundException extends WebApplicationException {

    /**
     * NOT FOUND Exception 404
     */
    public NotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     *
     *
     */
    public NotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(message).type("application/json").build());
    }

}
