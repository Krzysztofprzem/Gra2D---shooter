/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author krzys
 */

//Pocisk któremu można nadać kierunek, obrót względem punktu itp. (wykorzystanie współrzędnych biegunowych)
public class Pocisk3 extends Pocisk{
    //ATRYBUTY WŁASNE
    protected int    promien;
    protected double kat;
    protected double ruch_kata;
    protected int    x0;
    protected int    y0;
    protected double i;
    
    //KONSTRUKTORY
    public Pocisk3(int x, int y, int r, double a, double a2, String sc){
        super(3, x, y, 0, 0, sc);
        promien   = r;
        kat       = a;
        ruch_kata = a2;
        x0        = x;
        y0        = y;
        i         = 1;
    }
    
    public Pocisk3(int x, int y, int r, double a, double a2, int xx, int yy, double ii, String sc){
        super(3, x, y, 0, 0, sc);
        promien   = r;
        kat       = a;
        ruch_kata = a2;
        x0        = xx;
        y0        = yy;
        i         = ii;
    }
    
    //METODY (GETTERY)
    protected int get_promien(){
        return promien;
    }
    protected double get_kat(){
        return kat;
    }
    protected double get_ruch_kata(){
        return ruch_kata;
    }
    protected int get_x0(){
        return x0;
    }
    protected int get_y0(){
        return y0;
    }
    protected double get_i(){
        return i;
    }
    
    //METODY
    @Override
    public String dane(String napis){
        napis = super.dane(napis);
        for(int i=0; i<6; i++){
            switch(i){
                case 0: {napis += get_promien();   break;}
                case 1: {napis += get_kat();       break;}
                case 2: {napis += get_ruch_kata(); break;}
                case 3: {napis += get_x0();        break;}
                case 4: {napis += get_y0();        break;}
                case 5: {napis += get_i();         break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
  void ruch(int x, int y) {
       kat   = kat + ruch_kata;
       wsp_y = (int) (y0 + i*promien*sin(kat));
       wsp_x = (int) (x0 + i*promien*cos(kat));
       i+=0.8;
  }
}
