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
//Klasa główna dla bonusów
public class Bonus extends Obiekt {
    //ATRYBUTY WŁASNE
    
    //KONSTRUKTORY
    public Bonus(int p, int x, int y, int pkt, String sc){
        wymiary_x = 20;
        wymiary_y = 20;
        typ       = 4;
        podtyp    = p;
        wsp_x     = x;
        wsp_y     = y;
        punkty    = pkt;
        sciezka1  = sc;
        sciezka2  = sciezka1;
        obrazek1  = new ImageIcon(sciezka1).getImage();
    }
    
    //METODY
    @Override
    public String dane(String napis){
        for(int i=0; i<6; i++){
            switch(i){
                case 0:{napis += get_typ();      break;}
                case 1:{napis += get_podtyp();   break;}
                case 2:{napis += get_wsp_x();    break;}
                case 3:{napis += get_wsp_y();    break;}
                case 4:{napis += get_punkty();   break;}
                case 5:{napis += get_sciezka1(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    void ruch(int x, int y) {
        wsp_y += 5;
    }

    @Override
    void atak(ArrayList<Obiekt> aktywne_obiekty) {
    }

    @Override
    void kolidowanie(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2) {
    }

    @Override
    protected void pokaz_zdrowie(Graphics2D g2d){  
    }

    @Override
    void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie) {
        gracz.set_punkty(gracz.get_punkty() + punkty); 
        aktywne_obiekty.remove(nr_na_liscie);
    }

    @Override
    void skutki_kolizji(Przeciwnik przeciwnik, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie) {
    }

    @Override
    void smierc(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2) {
    }
}
