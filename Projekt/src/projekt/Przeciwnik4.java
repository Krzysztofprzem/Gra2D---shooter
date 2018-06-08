/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author krzys
 */

//Przeciwnik tworzący dwa pierścienie pocisków, kręcące się w przeciwne strony jednocześnie zwiększając swój promień
public class Przeciwnik4 extends Przeciwnik3 {
    //ATRYBUTY WŁASNE
    
    //KONSTRUKTORY
    public Przeciwnik4(int x, int y, int pz, int pkt, int px, int py, int pa, int pc) {
        super(x, y, pz, pkt, px, py, pa, pc);
        sciezka1 = "Grafika/Przeciwnik4.png";
        sciezka2 = "Grafika/Przeciwnik4_2.png";
        obrazek1 = new ImageIcon(sciezka1).getImage();
        obrazek2 = new ImageIcon(sciezka2).getImage();
        podtyp   = 4;
    }
    public Przeciwnik4(int x, int y, boolean anim, int pz, int pkt, int px, int py, int pa, int ccr, int cca, int pc, boolean p_a) {
        super(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc, p_a);
        sciezka1 = "Grafika/Przeciwnik4.png";
        sciezka2 = "Grafika/Przeciwnik4_2.png";
        obrazek1 = new ImageIcon(sciezka1).getImage();
        obrazek2 = new ImageIcon(sciezka2).getImage();
        podtyp   = 4;
    }
    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if((chwila_czasu_atak == 0) && (wsp_y>0) && (wsp_y<640)){
            int ilosc_pociskow = 36;
            for(int i=0;i<ilosc_pociskow;i++)
            {
                String sc = "";
                double alfa    = 360/ilosc_pociskow*i*Math.PI/180;
                double x       = wsp_x + 25;
                double y       = wsp_y + 60;
                double ruch_alfa;
                if(przelacznik_ataku){
                    ruch_alfa         = 0.005;
                    sc                = "Grafika/Pocisk4_1.png";
                    przelacznik_ataku = false;
                }
                else{
                    ruch_alfa         = -0.005;
                    sc                = "Grafika/Pocisk4_2.png";
                    przelacznik_ataku = true;
                }
                aktywne_obiekty.add(new Pocisk3((int)x, (int)y, 2, alfa, ruch_alfa, sc));
            }
        }
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }
    @Override
    void smierc(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2){
        aktywne_obiekty.add(new Bonus5(wsp_x+20, wsp_y+20, "Grafika/Bonus5.png"));
        //dzwiek.play();
        aktywne_obiekty.remove(nr_na_liscie1);
    }
}
