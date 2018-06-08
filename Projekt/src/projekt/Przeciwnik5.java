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

//Przeciwnik strzelający w pozycję na której znajduje się gracz
public class Przeciwnik5 extends Przeciwnik1{
    //ATRYBUTY WŁASNE
    
    //KONSTRUKTORY
    public Przeciwnik5(int x, int y, int pz, int pkt, int px, int py, int pa, int pc1, int pc2) {
        super(x, y, pz, pkt, px, py, pa, pc1, pc2);
        podtyp = 5;
        sciezka1 = "Grafika/Przeciwnik5.png";
        sciezka2 = "Grafika/Przeciwnik5_2.png";
        obrazek1 = new ImageIcon(sciezka1).getImage();
        obrazek2 = new ImageIcon(sciezka2).getImage();
    }
    public Przeciwnik5(int x, int y, boolean anim, int pz, int pkt, int px, int py, int pa, int ccr, int cca, int pc1, int pc2) {
        super(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc1, pc2);
        podtyp = 5;
        sciezka1 = "Grafika/Przeciwnik5.png";
        sciezka2 = "Grafika/Przeciwnik5_2.png";
        obrazek1 = new ImageIcon(sciezka1).getImage();
        obrazek2 = new ImageIcon(sciezka2).getImage();
    }
    
    //METODY
    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if((chwila_czasu_atak == 0) && (wsp_y>0) && (wsp_y<640)){
            double alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+60),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x+25));
            aktywne_obiekty.add(new Pocisk3(wsp_x + 25, wsp_y + 60, 4, alfa, 0, "Grafika/Pocisk5.png"));
       }
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }
}
