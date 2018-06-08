/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.util.ArrayList;

/**
 *
 * @author krzys
 */

public class Przeciwnik3 extends Przeciwnik {
    //ATRYBUTY WŁASNE
    protected int     parametr_czasowy;
    protected boolean przelacznik_ataku;

    //KONSTRUKTORY
    public Przeciwnik3(int x, int y, int pz, int pkt, int px, int py, int pa, int pc) {
        super(3, x, y, pz, pkt, "Grafika/Przeciwnik3.png", "Grafika/Przeciwnik3_2.png", px, py, pa);
        parametr_czasowy  = pc;
        przelacznik_ataku = true;
    }
    public Przeciwnik3(int x, int y, boolean anim, int pz, int pkt, int px, int py, int pa, int ccr, int cca, int pc, boolean p_a) {
        super(3, x, y, pz, pkt, "Grafika/Przeciwnik3.png", "Grafika/Przeciwnik3_2.png", px, py, pa);
        animacja          = anim;
        dx                = px;
        dy                = py;
        chwila_czasu_ruch = ccr;
        chwila_czasu_atak = cca;
        parametr_czasowy  = pc;
        przelacznik_ataku = p_a;
    }
    
    //METODY (GETTERY)
    public int get_parametr_czasowy(){
        return parametr_czasowy;
    }
    public boolean get_przelacznik_ataku(){
        return przelacznik_ataku;
    }
    
    //METODY
    @Override
    public String dane(String napis){
        napis = super.dane(napis);
        for(int i=0;i<2;i++){
            switch(i){
                case 0:{napis += get_parametr_czasowy();  break;}
                case 1:{napis += get_przelacznik_ataku(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    public void ruch(int x, int y){
        if(chwila_czasu_ruch <= parametr_czasowy){
            wsp_y += dy;
            chwila_czasu_ruch++;
        }
    }
    
    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if((chwila_czasu_atak == 0) && (wsp_y>0) && (wsp_y<640)){
            int ilosc_pociskow = 20;
            int a             = (przelacznik_ataku) ? 1 : -1;
            przelacznik_ataku = (przelacznik_ataku) ? false: true;
            for(int i=0;i<ilosc_pociskow;i++)
            {
                double alfa = 360/ilosc_pociskow*i*Math.PI/180;
                double ruch_alfa = a*0.01;;
                double x = wsp_x + 25;
                double y = wsp_y + 60;
                aktywne_obiekty.add(new Pocisk3((int)x, (int)y, 2, alfa, ruch_alfa, "Grafika/Pocisk3.png"));
            }
       }
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }
}
