/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author krzys
 */
abstract class Obiekt {
    //ZMIENNE CHARAKTERYSTYCZNE
    protected int       typ;     //typ obiektu tj. 1. Gracz, 2. Przeciwnik, 3. Pocisk ,4. Bonus
    protected int       podtyp;
    protected int       wsp_x;
    protected int       wsp_y; 
    protected boolean   animacja;  
    protected int       punkty_zdrowia;
    protected int       punkty; 
    protected String    sciezka1;
    protected String    sciezka2;
    protected Image     obrazek1;
    protected Image     obrazek2;
    protected int       wymiary_x;
    protected int       wymiary_y;
    
    //METODY ABRSTRAKCYJNE
    abstract void ruch(int x, int y);
    abstract void atak(ArrayList<Obiekt> aktywne_obiekty);
    abstract void kolidowanie(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2);
    abstract void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie);
    abstract void skutki_kolizji(Przeciwnik przeciwnik, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie);
    abstract String dane(String napis);
    abstract void smierc(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2);
    
    //METODY(GETTERY)
    protected int get_typ(){
        return typ;
    }
    protected int get_podtyp(){
        return podtyp;
    }
    protected int get_wsp_x(){
        return wsp_x;
    }
    protected int get_wsp_y(){
        return wsp_y;
    }
    protected int get_wymiary_x(){
        return wymiary_x;
    }
    protected int get_wymiary_y(){
        return wymiary_y;
    }
    protected boolean get_animacja(){
        return animacja;
    }
    protected int get_punkty_zdrowia(){
        return punkty_zdrowia;
    }
    protected int get_punkty(){
        return punkty;
    }
    protected String get_sciezka1(){
        return sciezka1;
    }
    protected String get_sciezka2(){
        return sciezka2;
    }
    
    //METODY(SETTERY)
    protected void set_punkty_zdrowia(int z){
        punkty_zdrowia = z;
    }
    protected void set_punkty(int p){
        punkty = p;
    }
    
    //METODY
    protected void rysuj(Graphics2D g2d){
        g2d.drawImage(obrazek1,wsp_x,wsp_y,null);
    }
    
    protected void pokaz_zdrowie(Graphics2D g2d){
        if(punkty_zdrowia<10)
            g2d.drawString(""+punkty_zdrowia, wsp_x+27, wsp_y+20);
        else if(punkty_zdrowia<100)
            g2d.drawString(""+punkty_zdrowia, wsp_x+24, wsp_y+20);
        else if(punkty_zdrowia<1000)
            g2d.drawString(""+punkty_zdrowia, wsp_x+21, wsp_y+20);
        else
            g2d.drawString(""+punkty_zdrowia, wsp_x+19, wsp_y+20);
    }
}
