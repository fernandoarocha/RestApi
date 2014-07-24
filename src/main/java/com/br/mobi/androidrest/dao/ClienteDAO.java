package com.br.mobi.androidrest.dao;

import com.br.mobi.androidrest.exceptions.UnauthorizedException;
import com.br.mobi.androidrest.factory.ConnectionFactory;
import com.br.mobi.androidrest.model.Cliente;
import com.br.mobi.androidrest.util.Crypto;
import com.br.mobi.androidrest.util.PasswordUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by fernando.rocha on 22/07/2014.
 */
public class ClienteDAO extends ConnectionFactory {
    Crypto crypto = new Crypto();

    public int verificaCadastrado(String nome) {

        int id = 0;
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        conn = getConnection();
        try {
            stmt = conn.prepareStatement("SELECT ID FROM CLIENTE WHERE NOME = ?");
            stmt.setString(1, nome);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, resultSet);
        }
        return id;
    }

    public int inserir(Cliente cliente) {
        String passCrypted;

        Connection conn = null;
        conn = getConnection();
        int sucesso = 0;
        int prodsCadastrados = verificaCadastrado(cliente.getNome());



        if (prodsCadastrados == 0) {
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement("INSERT INTO "
                        + "CLIENTE (NOME, EMAIL, PASSWORD, SEXO, DATANASCIMENTO, TELEFONE) VALUES(?,?,?,?,?,?)");

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getEmail());
                passCrypted = crypto.encryptedPass(cliente.getPassword());
                stmt.setString(3, passCrypted);
                stmt.setString(4, cliente.getSexo());
                stmt.setString(5, cliente.getDataNascimento());
                stmt.setString(6, cliente.getTelefone());
                sucesso = stmt.executeUpdate();

                if (sucesso > 0) {
                    System.out.println("CLIENTE INSERIDO!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ERRO AO INSERIR CLIENTE!");
            } finally {
                closeConnection(conn, stmt);
            }
        } else {
            System.out.println("ERRO: CLIENTE JA CADASTRADO");
            closeConnection(conn);
        }
        return sucesso;
    }

    public int alterar(Cliente cliente) {

        Connection conn = null;
        conn = getConnection();
        PreparedStatement stmt = null;
        int sucesso = 0;
        try {
            stmt = conn
                    .prepareStatement("UPDATE CLIENTE SET NOME = ?, EMAIL = ?, PASSWORD = ?, SEXO = ?, " +
                            "DATANASCIMENTO = ?, TELEFONE = ? WHERE ID = ?");

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getPassword());
            stmt.setString(4, cliente.getSexo());
            stmt.setString(5, cliente.getDataNascimento());
            stmt.setString(6, cliente.getTelefone());
            stmt.setInt(7, cliente.getId());
            sucesso = stmt.executeUpdate();

            if (sucesso > 0) {
                System.out.println("CLIENTE ALTERADO!");
            } else {
                System.out.println("CLIENTE NÃO EXISTE!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ALTERAR CLIENTE!");
        } finally {
            closeConnection(conn, stmt);
        }
        return sucesso;
    }

    public int deletar(int id) {

        Connection conn = null;
        conn = getConnection();
        int excluidos = 0;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM CLIENTE WHERE ID = ?");
            stmt.setInt(1, id);
            excluidos = stmt.executeUpdate();

            if (excluidos > 0) {
                System.out.println("CLIENTE REMOVIDO!");
            } else {
                System.out.println("CLIENTE NÃO EXISTE!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO DELETAR CLIENTE!");
        } finally {
            closeConnection(conn, stmt);
        }
        return excluidos;
    }

    public Cliente buscar(int id) {

        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        conn = getConnection();
        Cliente cliente = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM CLIENTE WHERE ID = ?");
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setPassword(resultSet.getString("password"));
                cliente.setSexo(resultSet.getString("sexo"));
                cliente.setDataNascimento(resultSet.getString("dataNascimento"));
                cliente.setTelefone(resultSet.getString("telefone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, resultSet);
        }
        return cliente;
    }
    public Cliente login(String username, String password) {

        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        conn = getConnection();
        Cliente cliente = null;
        String passCrypted;




        try {
            stmt = conn.prepareStatement("SELECT * FROM CLIENTE WHERE EMAIL = ?");
            stmt.setString(1, username);

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setPassword(resultSet.getString("password"));
                cliente.setSexo(resultSet.getString("sexo"));
                cliente.setDataNascimento(resultSet.getString("dataNascimento"));
                cliente.setTelefone(resultSet.getString("telefone"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, resultSet);
        }

            if(crypto.checkEncrypted( cliente.getPassword(), password) == true){
                return cliente;
            }else throw new UnauthorizedException("Não autorizado, email ou senha incorretos.");
    }

    public ArrayList<Cliente> buscarByName(String nome) {

        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        conn = getConnection();
        Cliente cliente = null;
        ArrayList<Cliente> listaClientes = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM CLIENTE WHERE NOME LIKE ?");
            listaClientes = new ArrayList<Cliente>();

            stmt.setString(1, "%"+ nome +"%");
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setPassword(resultSet.getString("password"));
                cliente.setSexo(resultSet.getString("sexo"));
                cliente.setDataNascimento(resultSet.getString("dataNascimento"));
                cliente.setTelefone(resultSet.getString("telefone"));
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, resultSet);
        }
        return listaClientes;
    }

    public ArrayList<Cliente> buscarTodos() {

        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        conn = getConnection();
        ArrayList<Cliente> listaClientes = null;

        try {

            stmt = conn.prepareStatement("SELECT * FROM CLIENTE ORDER BY ID");
            resultSet = stmt.executeQuery();
            listaClientes = new ArrayList<Cliente>();

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setPassword(resultSet.getString("password"));
                cliente.setSexo(resultSet.getString("sexo"));
                cliente.setDataNascimento(resultSet.getString("dataNascimento"));
                cliente.setTelefone(resultSet.getString("telefone"));
                listaClientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            listaClientes = null;
        } finally {
            closeConnection(conn, stmt, resultSet);
        }
        return listaClientes;
    }
}
