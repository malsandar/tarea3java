/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Julio
 */
public class Flor extends Semilla{
    private String color;

    public Flor(int codigo, String nombre, int precio, String color) {
        super(codigo, nombre, precio);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
    
    @Override
    public double calcularPrecioOferta() {
        if(!this.color.equalsIgnoreCase("Rojo") || !this.color.equalsIgnoreCase("Roja"))
            return this.getPrecio()-(this.getPrecio()*DESC_FLOR);
        else
            return this.getPrecio();
    }
    
}
