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

public class Przeciwnik1 extends Przeciwnik {
    //ATRYBUTY WŁASNE
    protected int parametr_czasowy1;
    protected int parametr_czasowy2;
    
    //KONSTRUKTORY
    public Przeciwnik1(int x, int y, int pz, int pkt, int px, int py, int pa, int pc1, int pc2) {
        super(1, x, y, pz, pkt, "Grafika/Przeciwnik1.png", "Grafika/Przeciwnik1_2.png", px, py, pa);
        parametr_czasowy1 = pc1;
        parametr_czasowy2 = pc2;
    }
     public Przeciwnik1(int x, int y, boolean anim, int pz, int pkt, int px, int py, int pa, int ccr, int cca, int pc1, int pc2) {
        super(1, x, y, pz, pkt, "Grafika/Przeciwnik1.png", "Grafika/Przeciwnik1_2.png", px, py, pa);
        animacja          = anim;
        dx                = px;
        dy                = py;
        chwila_czasu_ruch = ccr;
        chwila_czasu_atak = cca;
        parametr_czasowy1 = pc1;
        parametr_czasowy2 = pc2;
    }
     
    //METODY (GETTERY)
    public int get_parametr_czasowy1(){
        return parametr_czasowy1;
    }
    public int get_parametr_czasowy2(){
        return parametr_czasowy2;
    }
    
    //METODY
    @Override
    public String dane(String napis){
        napis = super.dane(napis);
        for(int i=0; i<2; i++){
            switch(i){
                case 0:{napis += get_parametr_czasowy1(); break;}
                case 1:{napis += get_parametr_czasowy2(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    public void ruch(int x, int y){
        if(chwila_czasu_ruch <= parametr_czasowy1){
            wsp_y += dy;
        }
        else if(chwila_czasu_ruch <= parametr_czasowy1 + parametr_czasowy2){
            wsp_x += dx;
        }
        if(chwila_czasu_ruch <= (parametr_czasowy1 + parametr_czasowy2))
            chwila_czasu_ruch++;
    }
    
    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if((chwila_czasu_atak == 0) && (wsp_y>0) && (wsp_y<640)){
            Obiekt pocisk    = new Pocisk2(wsp_x + 25, wsp_y + 60, 4, "Grafika/Pocisk2.png");
            aktywne_obiekty.add(pocisk);
       }
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }

}
