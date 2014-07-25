package com.br.mobi.androidrest.business;

import com.br.mobi.androidrest.dao.ClienteDAO;
import com.br.mobi.androidrest.exceptions.ComplexityException;
import com.br.mobi.androidrest.model.Cliente;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

/**
 * Created by fernando.rocha on 22/07/2014.
 */
public class ClienteBusiness {

    public ArrayList<Cliente> buscarTodos() {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscarTodos();
    }

    public String inserir(Cliente cliente) throws ComplexityException {

        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.inserir(cliente) > 0){
            return "Cliente inserido no banco com sucesso!";
        } else {
            return "Falha ao inserir o cliente no banco!";
        }
    }

    public String deletar(int id) {
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.deletar(id) > 0){
            return "Cliente removido no banco com sucesso!";
        } else {
            return "Cliente não existe!";
        }
    }

    public Cliente buscar(int id) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscar(id);
    }
    public ArrayList<Cliente> buscarByName(String name) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscarByName(name);
    }

    public String inserirLista(ArrayList<Cliente> listaClientes) throws ComplexityException{
        ClienteDAO clienteDAO = new ClienteDAO();
        String retorno = "";
        for (int i = 0; i < listaClientes.size(); i++) {
            if(clienteDAO.inserir(listaClientes.get(i)) < 1){
                retorno += "Erro ao inserir o cliente de CPF: "+ listaClientes.get(i).getNome() +"\n";
            }
        }
        if(retorno.length() == 0){
            retorno = "Lista de clientes inserida no banco com sucesso!";
        }
        return retorno;
    }

    public String alterar(Cliente cliente){
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.alterar(cliente) > 0)  {
            return "Cliente alterado no banco com sucesso!";
        }else{
            return "Cliente não existe!";
        }
    }
    public Cliente login(String username, String password){
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.login(username, password);
    }

}
