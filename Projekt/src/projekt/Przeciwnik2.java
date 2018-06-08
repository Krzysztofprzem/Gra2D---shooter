/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;

/**
 *
 * @author krzys
 */

public class Przeciwnik2 extends Przeciwnik{
    //ATRYBUTY WŁASNE
    protected int     parametr_czasowy;
    protected boolean przelacznik_ruchu;
    
    //KONSTRUKTORY
    public Przeciwnik2(int x, int y, int pz, int pkt, int px, int py, int pa, int pc, boolean pr) {
        super(2, x, y, pz, pkt, "Grafika/Przeciwnik2.png", "Grafika/Przeciwnik2_2.png", px, py, pa);
        parametr_czasowy  = pc;
        przelacznik_ruchu = pr;  
    }
    public Przeciwnik2(int x, int y, boolean anim, int pz, int pkt, int px, int py, int pa, int ccr, int cca, int pc, boolean p_r) {
        super(2, x, y, pz, pkt, "Grafika/Przeciwnik2.png", "Grafika/Przeciwnik2_2.png", px, py, pa);
        animacja          = anim;
        dx                = px;
        dy                = py;
        chwila_czasu_ruch = ccr;
        chwila_czasu_atak = cca;
        parametr_czasowy  = pc;
        przelacznik_ruchu = p_r;
    }
    
    //METODY (GETTERY)
    public int get_parametr_czasowy(){
        return parametr_czasowy;
    }
    public boolean get_przelacznik_ruchu(){
        return przelacznik_ruchu;
    }
    
    //METODY
    @Override
    public String dane(String napis){
        napis = super.dane(napis);
        for(int i=0;i<2;i++){
            switch(i){
                case 0:{napis += get_parametr_czasowy();  break;}
                case 1:{napis += get_przelacznik_ruchu(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }

    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if((chwila_czasu_atak == 0) && (wsp_y>0) && (wsp_y<640)){
            int ilosc_pociskow = 10;
            for(int i=0;i<ilosc_pociskow;i++)
            {
                double alfa = 360/ilosc_pociskow*i*Math.PI/180;
                double x = wsp_x + 25 + dx*cos(alfa);
                double y = wsp_y + 30 + dy*sin(alfa);
                double ruch_alfa = 0;
                Obiekt pocisk = new Pocisk3((int) x, (int) y, 3, alfa, ruch_alfa, "Grafika/Pocisk3.png");
                aktywne_obiekty.add(pocisk);
            }
        }
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }
    
    @Override
    public void ruch(int x, int y){
        if(chwila_czasu_ruch < parametr_czasowy){
            wsp_y += dy;
        }
        else if(przelacznik_ruchu){
            if(wsp_x - dx < 0){
                wsp_x = 0;
                przelacznik_ruchu = false;
            }
            else
                wsp_x -= dx;
        }
        else{
            if(wsp_x + dx > 740){
                wsp_x = 740;
                przelacznik_ruchu = true;
            }
            else
                wsp_x += dx;
        }
        chwila_czasu_ruch++;
    }
}
