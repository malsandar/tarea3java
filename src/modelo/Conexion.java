/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Julio
 */
public class Conexion {

    public static Connection conexion;

    private Conexion() {

    }

    public static Connection getInstance() {
        if (conexion == null) {
            //String[] parte;
            //parte = infoConexion.split(",");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "semilla";
            String pass = "julio123";
            try {
                conexion = DriverManager.getConnection(url, user, pass);
                System.out.println("Conectado");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();

            }
            return conexion;
        }
        else
            return conexion;
    }
}

