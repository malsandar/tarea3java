/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.SemillaCRUD;
import vista.Principal;

/**
 *
 * @author Julio
 */
public class Controlador implements ActionListener {

    private Principal vista;
    private SemillaCRUD modelo;

    public Controlador() {
        this.vista = new Principal();
        this.modelo = new SemillaCRUD();
    }

    public Controlador(Principal vista, SemillaCRUD modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.guardarButton.addActionListener(this);
        this.vista.tipoComboBox.addActionListener(this);
        this.vista.buscarButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.tipoComboBox) {
            JComboBox cb = (JComboBox) e.getSource();
            String opcion = (String) cb.getSelectedItem();
            switch (opcion) {
                case "Flor":
                    this.vista.comodinLabel.setVisible(true);
                    this.vista.comodinLabel.setText("Color");
                    this.vista.comodinTextBox.setVisible(true);
                    break;
                case "Arbol":
                    this.vista.comodinLabel.setVisible(true);
                    this.vista.comodinLabel.setText("Altura");
                    this.vista.comodinTextBox.setVisible(true);
                    break;
                default:
                    this.vista.comodinLabel.setVisible(true);
                    this.vista.comodinLabel.setText("Ups, ha ocurrido un error");
                    this.vista.comodinTextBox.setVisible(false);
            }
        }
        if (e.getSource() == this.vista.buscarButton) {
            String id = this.vista.buscarTextBox.getText();
            String resultado = this.modelo.buscarSemilla(id);
            if (resultado != null) {
                String[] parte = resultado.split(",");
                if (parte[2].equals("Flor")) {

                    JOptionPane.showMessageDialog(null, "ID: " + parte[0] + "\nNombre: " + parte[1] + "\nTipo: Flor\nPrecio: " + parte[3] + "\nColor: " + parte[4]);
                } else if (parte[2].equals("Arbol")) {

                    JOptionPane.showMessageDialog(null, "ID: " + parte[0] + "\nNombre: " + parte[1] + "\nTipo: Arbol\nPrecio: " + parte[3] + "\nAltura: " + parte[4]);
                } else {
                    JOptionPane.showMessageDialog(null, "Ups hubo un error inesperado");
                }
            }
            else
                JOptionPane.showMessageDialog(null, "No se encontraron semillas con ese código");

        }

        if (e.getSource() == this.vista.guardarButton) {
            String id = this.vista.idTextBox.getText();
            String nombre = this.vista.nombreTextBox.getText();
            String tipo = (String) this.vista.tipoComboBox.getSelectedItem();
            String precio = this.vista.precioTextBox.getText();
            if (tipo.equals("Flor")) {
                
                String color = this.vista.comodinTextBox.getText();
                
                if (this.modelo.insertarSemilla(id, nombre, precio,tipo, color)) {
                    JOptionPane.showMessageDialog(null, "Guardado");
                } else {
                    JOptionPane.showMessageDialog(null, "No Guardado");
                }
            } else if (tipo.equals("Arbol")) {
                String altura = this.vista.comodinTextBox.getText();
                if (this.modelo.insertarSemilla(id, nombre, precio,tipo, altura)) {
                    JOptionPane.showMessageDialog(null, "Guardado");
                } else {
                    JOptionPane.showMessageDialog(null, "No Guardado");
                }
            }
        }
    }

}

/*
if(e.getSource() == this.vista.guardarButton){
String nombre = this.vista.idTextBox.getText();
            String apellido = this.vista.nombreTextBox.getText();
            int rut = Integer.parseInt(this.vista.precioTextBox.getText());
            char dv = this.vista.jTextField4.getText().charAt(0);
            String email = this.vista.jTextField5.getText();
            if(modelo.insertarPersona(new Persona(rut, dv, nombre, apellido, email)))
                JOptionPane.showMessageDialog(null, "Guardado");
            else
                JOptionPane.showMessageDialog(null, "No Guardado");
 */
