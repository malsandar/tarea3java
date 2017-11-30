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

    public void llenarTabla(JTable tablaSemilla) {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        tablaSemilla.setModel(modeloTabla);

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Color");
        modeloTabla.addColumn("Altura");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Precio con oferta");

        Object[] columna = new Object[7];
        int cantidadSemillas = this.semillas.size();
        for (int x = 0; x < cantidadSemillas; x++) {
            columna[0] = this.semillas.get(x).getCodigo();
            columna[1] = this.semillas.get(x).getNombre();
            if (this.semillas.get(x) instanceof Flor) {
                columna[6] = (int)((Flor)this.semillas.get(x)).calcularPrecioOferta();
                columna[5] = this.semillas.get(x).getPrecio();
                columna[2] = "Flor";
                columna[3] = ((Flor) this.semillas.get(x)).getColor();
                columna[4] = "No Aplica";
            } else if (this.semillas.get(x) instanceof Arbol) {
                columna[6] = (int)((Arbol)this.semillas.get(x)).calcularPrecioOferta();
                columna[5] = this.semillas.get(x).getPrecio();
                columna[2] = "Arbol";
                columna[4] = ((Arbol) this.semillas.get(x)).getAlturaMaxima();
                columna[3] = "No Aplica";
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
                    this.vista.comodinLabel.setText("Altura (en centímetros)");
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
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron semillas con ese código");
            }

        }
        //Inserta una semilla en la BD
        if (e.getSource() == this.vista.guardarButton) {
            String id = this.vista.idTextBox.getText();
            String nombre = this.vista.nombreTextBox.getText();
            String tipo = (String) this.vista.tipoComboBox.getSelectedItem();
            String precio = this.vista.precioTextBox.getText();
            String comodin = this.vista.comodinTextBox.getText();
            if ((Integer.parseInt(id) > 99 && Integer.parseInt(id) < 1000) && nombre.length() > 4 && Integer.parseInt(precio) > 0) {
                if (tipo.equals("Flor")) {

                    if (this.modelo.insertarSemilla(id, nombre, precio, tipo, comodin)) {
                        JOptionPane.showMessageDialog(null, "Guardado");
                    } else {
                        JOptionPane.showMessageDialog(null, "No Guardado");
                    }
                } else if (tipo.equals("Arbol")) {
                    if (Integer.parseInt(comodin) > 0) {
                        if (this.modelo.insertarSemilla(id, nombre, precio, tipo, comodin)) {
                            JOptionPane.showMessageDialog(null, "Guardado");
                        } else {
                            JOptionPane.showMessageDialog(null, "No Guardado");
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "El precio debe ser mayor que cero");

                }
            } else {
                this.vista.idTipTextBox.setText("(Debe ser un número entre 100 y 999)");
                JOptionPane.showMessageDialog(null, "Datos inválidos.\n- La ID debe estar entre 100 y 999\n- El nombre debe tener más de 4 caracteres\n- Precio debe ser mayorque cero");
            }
        }
        //Borra una semilla en la BD
        if (e.getSource() == this.vista.borrarButton) {
            String id = this.vista.borrarTextBox.getText();
            if (this.modelo.borrarSemilla(id)) {
                JOptionPane.showMessageDialog(null, "Eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar, dado de que no existe el código la semilla en los registros");
            }
        }

        if (e.getSource() == this.vista.actualizarButton) {
            this.semillas = this.modelo.listarSemillas();
            if (this.semillas == null) {
                JOptionPane.showMessageDialog(null, "No existen semillas en la BD");
            } else {
                llenarTabla(this.vista.jTable2);
            }
        }
    }

}
