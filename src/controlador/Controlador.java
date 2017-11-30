/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Arbol;
import modelo.Flor;
import modelo.Semilla;
import modelo.SemillaCRUD;
import vista.Principal;

/**
 *
 * @author Julio
 */
public class Controlador implements ActionListener {
    private List<Semilla> semillas;
    private Principal vista;
    private SemillaCRUD modelo;

    public Controlador() {
        this.semillas = new ArrayList<Semilla>();
        this.vista = new Principal();
        this.modelo = new SemillaCRUD();
    }

    public Controlador(Principal vista, SemillaCRUD modelo) {
        this.semillas = new ArrayList<Semilla>();
        this.vista = vista;
        this.modelo = modelo;
        this.vista.guardarButton.addActionListener(this);
        this.vista.tipoComboBox.addActionListener(this);
        this.vista.buscarButton.addActionListener(this);
        this.vista.borrarButton.addActionListener(this);
        this.vista.actualizarButton.addActionListener(this);
    }
    
    public void llenarTabla(JTable tablaSemilla){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        tablaSemilla.setModel(modeloTabla);
        
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Color");
        modeloTabla.addColumn("Altura");
        
        Object[] columna = new Object[6];
        int cantidadSemillas = this.semillas.size();
        for(int x = 0; x < cantidadSemillas; x++){
            columna[0] = this.semillas.get(x).getCodigo();
            columna[1] = this.semillas.get(x).getNombre();
            columna[3] = this.semillas.get(x).getPrecio();
            if(this.semillas.get(x) instanceof Flor){
                columna[2] = "Flor";
                columna[4] = ((Flor)this.semillas.get(x)).getColor();
                columna[5] = "No Aplica";
            }
            else if(this.semillas.get(x) instanceof Arbol){
                columna[2] = "Arbol";
                columna[5] = ((Arbol)this.semillas.get(x)).getAlturaMaxima();
                columna[4] = "No Aplica";
            }
            modeloTabla.addRow(columna);
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Cambia el label comodin entre "Color" y "Altura" dependiendo del tipo de semilla
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
        //Busca una semilla en la BD
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
        //Inserta una semilla en la BD
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
        //Borra una semilla en la BD
        if(e.getSource() == this.vista.borrarButton){
            String id = this.vista.borrarTextBox.getText();
            if(this.modelo.borrarSemilla(id)){
                JOptionPane.showMessageDialog(null, "Eliminado");
            }
            else
                JOptionPane.showMessageDialog(null, "No se pudo eliminar, dado de que no existe el código la semilla en los registros");
        }
        
        if(e.getSource() == this.vista.actualizarButton){
            this.semillas = this.modelo.listarSemillas();
            if(this.semillas == null){
                JOptionPane.showMessageDialog(null, "No existen semillas en la BD");
            }
            else
                llenarTabla(this.vista.jTable2);
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
