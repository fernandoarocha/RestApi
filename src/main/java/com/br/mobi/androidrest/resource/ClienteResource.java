package com.br.mobi.androidrest.resource;

import com.br.mobi.androidrest.business.ClienteBusiness;
import com.br.mobi.androidrest.exceptions.NoContentException;
import com.br.mobi.androidrest.model.Cliente;
import com.br.mobi.androidrest.model.Login;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import java.util.ArrayList;


/**
 * Created by fernando.rocha on 22/07/2014.
 */

@Path("/resource")
public class ClienteResource {
    @GET
    @Path("/buscarTodos")
    @Produces("application/json")
    public ArrayList<Cliente> selTodos() {
        return new ClienteBusiness().buscarTodos();
    }

    @GET
    @Path("/buscarTodosGSON")
    @Produces("application/json")
    public String selTodosGSON() {
        return new Gson().toJson(new ClienteBusiness().buscarTodos());
    }

    @GET
    @Path("buscaPeloId/{id}")
    @Produces("application/json")
    public Cliente getCliente(@PathParam("id") int id) {
        Cliente cliente = new ClienteBusiness().buscar(id);

        if (cliente == null)
            throw new NoContentException("Cliente não encontrado!");

        return cliente;
    }

    @GET
    @Path("buscaPeloNome/{name}")
    @Produces("application/json")
    public ArrayList<Cliente> getClienteByName(@PathParam("name") String name) {
        ArrayList<Cliente> clientes = new ClienteBusiness().buscarByName(name);

        if (clientes == null)
            throw new NoContentException("Cliente não encontrado!");

        return clientes;
    }

    @GET
    @Path("/delete/{id}")
    @Produces("application/json")
    public String deleteCliente(@PathParam("id") int id) {
        return new ClienteBusiness().deletar(id);
    }

    @POST
    @Path("/inserir")
    @Produces("application/json")
    @Consumes("application/json")
    public String inserirCliente(Cliente cliente) {
        return new ClienteBusiness().inserir(cliente);
    }
//Espaço em query String é ?%20

    @POST
    @Path("/inserirLista")
    @Produces("application/json")
    @Consumes("application/json")
    public String inserirLista(String listaClientesJson) {

        Gson gson = new Gson();
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(listaClientesJson).getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
            listaClientes.add(gson.fromJson(array.get(i), Cliente.class));
        }

        return new ClienteBusiness().inserirLista(listaClientes);

    }

    @PUT
    @Path("/alterar")
    @Produces("application/json")
    @Consumes("application/json")
    public String alterarCliente(Cliente cliente) {
        return new ClienteBusiness().alterar(cliente);
    }

    @POST
    @Path("/login")
    @Produces("application/json")
    @Consumes("application/json")
    public Cliente login(String json){

        Gson gson = new Gson();
        Login obj = gson.fromJson(json, Login.class);

      return new ClienteBusiness().login(obj.getEmail(), obj.getPassword());
    }

}
