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
public class Przeciwnik6 extends Przeciwnik{
    //ATRYBUTY WŁASNE
    protected int i;
    protected boolean przelacznik_ataku;
    
    //KONSTRUKTORY
    public Przeciwnik6(int x, int y, int pz, int pkt, int px, int py) {
        super(6, x, y, pz, pkt, "Grafika/Przeciwnik6.png", "Grafika/Przeciwnik6_2.png", px, py, 0);
        i                 = 0;
        przelacznik_ataku = true;
    }
    public Przeciwnik6(int x, int y, boolean anim, int pz, int pkt, int px, int ccr, int py, int ii, boolean p_a) {
        super(6, x, y, pz, pkt, "Grafika/Przeciwnik6.png", "Grafika/Przeciwnik6_2.png", px, py, 0);
        chwila_czasu_ruch = ccr;
        animacja          = anim;
        i                 = ii;
        przelacznik_ataku = p_a;
    }
    
    //METODY (GETTERY)
    public int get_i(){
        return i;
    }
    public boolean get_przelacznik_ataku(){
        return przelacznik_ataku;
    }
    
    //ZMIENNE
    @Override
    public String dane(String napis) 
    {
        napis = super.dane(napis);
        for(int i=0; i<2; i++){
            switch(i){
                case 0: {napis += get_i();                 break;}
                case 1: {napis += get_przelacznik_ataku(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    @Override
    public void ruch(int x, int y){
        if(chwila_czasu_ruch < 150){
            wsp_y += 1;
        }
        chwila_czasu_ruch++;
    }
    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if(((wsp_y>0) && (wsp_y<640)) && (i%5==0)){
            double x = wsp_x + 25;  //+100*cos(alfa);
            double y = wsp_y + 60;  //+100*sin(alfa);
            double alfa = Math.PI + 2*i*Math.PI/180;
            if     (alfa >= 170*Math.PI/180) przelacznik_ataku = false;
            else if(alfa <=  10*Math.PI/180) przelacznik_ataku = true;
            aktywne_obiekty.add(new Pocisk3((int)x, (int)y, 3, alfa, 0, "Grafika/Pocisk6.png"));
        }
        if(przelacznik_ataku) i++;
        else                  i--;
    }
    /*
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if(((wsp_y>0) && (wsp_y<640))&&(i%3!=1)){
            double alfa = 90+10*i;
            double x = wsp_x + 25;  //+100*cos(alfa);
            double y = wsp_y + 60;  //+100*sin(alfa);
            aktywne_obiekty.add(new Pocisk3((int)x, (int)y, 3, alfa, 0, "Grafika/Pocisk3.png"));
        }
        i++;
    } */
}
