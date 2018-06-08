/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

/**
 *
 * @author krzys
 */

//Pocisk Przeciwnika
public class Pocisk2 extends Pocisk {
    //ATRYBUTY WŁASNE
    
    //KONSTRUKTORY
    public Pocisk2(int x, int y, int py, String sc) {
        super(2, x, y, 0, py, sc);
    }
    
    //METODY
    @Override
    void ruch(int x, int y) {
        wsp_y += dy;
    }
}
