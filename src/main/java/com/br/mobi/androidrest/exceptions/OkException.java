package com.br.mobi.androidrest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by fernando.rocha on 25/07/2014.
 */
public class OkException extends WebApplicationException{
    public OkException(String mensagem) {
        super(Response.status(Response.Status.OK).entity(mensagem).build());
    }
}
