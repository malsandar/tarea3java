/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author Julio
 */
public interface Vendible {
    public final double DESC_FLOR = 0.1;
    public final double DESC_ARBOL = 0.3;
    
    public double calcularPrecioOferta();
}
