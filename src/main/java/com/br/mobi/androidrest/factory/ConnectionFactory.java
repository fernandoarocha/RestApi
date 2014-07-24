package com.br.mobi.androidrest.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by fernando.rocha on 22/07/2014.
 */
public class ConnectionFactory {

    // database URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/cliente";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    //Abre Conexão
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(DATABASE_URL, USERNAME,
                    PASSWORD);
        } catch (Exception e) {
            System.out.println("Erro ao criar conexao.");
            e.printStackTrace();
        }
        return con;
    }

    //Verifica o fechamento da Conexão e os parametros que sao retornados.

    public void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            close(conn, stmt, rs);
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexao.");
            e.printStackTrace();
        }
    }

    public void closeConnection(Connection conn, Statement stmt) {
        try {
            close(conn, stmt, null);
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexao. Resultset NULL");
            e.printStackTrace();
        }
    }

    public void closeConnection(Connection conn) {
        try {
            close(conn, null, null);
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexao. Statement NULL ou ResultsetNull");
            e.printStackTrace();
        }
    }
    private void close(Connection conn, Statement stmt, ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexao.");
            e.printStackTrace();
        }
    }


}
