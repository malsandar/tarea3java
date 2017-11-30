/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Julio
 */
public class SemillaCRUD {

    public boolean insertarSemilla(String id, String nombre, String precio, String tipo, String comodin) {
        boolean flag = false;
        try {
            Connection c = Conexion.getInstance();
            Statement s = c.createStatement();
            if (tipo.equals("Flor")) {
                String query = "INSERT INTO SEMILLA(SEMILLA_ID, SEMILLA_NOMBRE, SEMILLA_PRECIO, SEMILLA_TIPO) VALUES ('" + id + "','" + nombre + "','" + precio + "','Flor')";
                s.executeUpdate(query);
                query = "INSERT INTO FLOR(SEMILLA_ID, FLOR_COLOR) VALUES ('" + id + "','" + comodin + "')";
                s.executeUpdate(query);
                flag = true;
            } else if (tipo.equals("Arbol")) {
                String query = "INSERT INTO SEMILLA(SEMILLA_ID, SEMILLA_NOMBRE, SEMILLA_PRECIO, SEMILLA_TIPO) VALUES ('" + id + "','" + nombre + "','" + precio + "','Arbol')";
                s.executeUpdate(query);
                query = "INSERT INTO ARBOL(SEMILLA_ID, ARBOL_ALTURA) VALUES ('" + id + "','" + comodin + "')";
                s.executeUpdate(query);
                flag = true;
            } else {
                flag = false;
            }

        } catch (SQLException ex) {

        }
        return flag;
    }
    
    public String buscarSemilla(String id) {
        try {
            ResultSet rs = null;
            ResultSet rs2 = null;
            Connection c = Conexion.getInstance();
            Statement s = c.createStatement();
            String query = "SELECT SEMILLA_ID, SEMILLA_NOMBRE, SEMILLA_PRECIO, SEMILLA_TIPO FROM SEMILLA WHERE SEMILLA_ID = " + id + "";
            rs = s.executeQuery(query);
            if (rs.next()) {
                System.out.println(rs.getString("SEMILLA_NOMBRE"));
                if (rs.getString("SEMILLA_TIPO").equals("Flor")) {
                    s = c.createStatement();
                    query = "SELECT FLOR_COLOR FROM FLOR WHERE SEMILLA_ID = " + id + "";
                    rs2 = s.executeQuery(query);
                    if (rs2.next()) {
                        String resultado = "";
                        resultado = resultado.concat(rs.getString("SEMILLA_ID"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs.getString("SEMILLA_NOMBRE"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs.getString("SEMILLA_TIPO"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs.getString("SEMILLA_PRECIO"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs2.getString("FLOR_COLOR"));
                        System.out.println("hola = " + resultado);
                        return resultado;
                    }

                } else if (rs.getString("SEMILLA_TIPO").equals("Arbol")) {
                    s = c.createStatement();
                    query = "SELECT ARBOL_ALTURA FROM ARBOL WHERE SEMILLA_ID = " + id + "";
                    rs2 = s.executeQuery(query);
                    if (rs2.next()) {
                        String resultado = "";
                        resultado = resultado.concat(rs.getString("SEMILLA_ID"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs.getString("SEMILLA_NOMBRE"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs.getString("SEMILLA_TIPO"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs.getString("SEMILLA_PRECIO"));
                        resultado = resultado.concat(",");
                        resultado = resultado.concat(rs2.getString("ARBOL_ALTURA"));
                        System.out.println("hola = " + resultado);
                        return resultado;
                    }
                } else {
                    System.out.println("Ups");
                }
            }
            else
                return null;

        } catch (SQLException ex) {

        }
        return null;
    }
}
