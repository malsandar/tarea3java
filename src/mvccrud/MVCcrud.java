/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvccrud;

import controlador.Controlador;
import modelo.SemillaCRUD;
import vista.Principal;

/**
 *
 * @author Julio
 */
public class MVCcrud {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Principal vista = new Principal();
        SemillaCRUD modelo = new SemillaCRUD();
        Controlador controlador = new Controlador(vista, modelo);
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
}
