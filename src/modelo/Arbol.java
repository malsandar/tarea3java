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
public class Arbol extends Semilla{
    private int alturaMaxima;

    public Arbol(int codigo, String nombre, int precio, int alturaMaxima) {
        super(codigo, nombre, precio);
        this.alturaMaxima = alturaMaxima;
    }

    public int getAlturaMaxima() {
        return alturaMaxima;
    }

    public void setAlturaMaxima(int alturaMaxima) {
        this.alturaMaxima = alturaMaxima;
    }
    
    @Override
    public double calcularPrecioOferta() {
        if(this.alturaMaxima > 180)
            return this.getPrecio()-(this.getPrecio()*DESC_ARBOL);
        else
            return this.getPrecio();
    }
    
}
