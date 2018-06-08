/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author krzys
 */

//Klasa główna dla pocisków
public class Pocisk extends Obiekt {
    //ATRYBUTY WŁASNE
    protected int dx;
    protected int dy;
    
    //KONSTRUKTORY
    public Pocisk(int p, int x, int y, int px, int py, String sc){
        wymiary_x = 10;
        wymiary_y = 10;
        typ       = 3;
        podtyp    = p;
        wsp_x     = x;
        wsp_y     = y;
        dx        = py;
        dy        = py;
        sciezka1  = sc;
        sciezka2  = sciezka1;
        obrazek1  = new ImageIcon(sciezka1).getImage();
    }
    //METODY (GETTERY)
    public int get_dx(){
        return dx;
    }
    public int get_dy(){
        return dy;
    }
    
    //METODY
    @Override
    String dane(String napis) {
        for(int i=0; i<7; i++){
            switch(i){
                case 0: {napis += get_typ();            break;}
                case 1: {napis += get_podtyp();         break;}
                case 2: {napis += get_wsp_x();          break;}
                case 3: {napis += get_wsp_y();          break;}
                case 4: {napis += get_sciezka1();       break;}
                case 5: {napis += get_dx();             break;}
                case 6: {napis += get_dy();             break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    void ruch(int x, int y) {
       wsp_y += dy;
       wsp_x += dx;
    }
    /*
    void ruch(int x, int y) {
       kat    = kat + ruch_kata;
       wsp_y = (int) (yy + i*promien*sin(kat));
       wsp_x = (int) (xx + i*promien*cos(kat));
       i+=0.1;
    }*/
    @Override
    protected void kolidowanie(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2){
    }
    
    @Override
    protected void atak(ArrayList<Obiekt> aktywne_obiekty) {
    }
    
    @Override
    protected void pokaz_zdrowie(Graphics2D g2d){
    }

    @Override
    protected void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie) {
        gracz.set_punkty(gracz.get_punkty()-20);
        gracz.set_punkty_zdrowia(gracz.get_punkty_zdrowia()-1);
        aktywne_obiekty.remove(nr_na_liscie);
    }
    @Override
    protected void skutki_kolizji(Przeciwnik przeciwnik, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie) {
        przeciwnik.set_punkty_zdrowia(przeciwnik.get_punkty_zdrowia()-1);
        aktywne_obiekty.remove(nr_na_liscie);
    }

    @Override
    void smierc(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2) {
    }
}
