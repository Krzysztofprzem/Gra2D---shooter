/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;

/**
 *
 * @author krzys
 */
//Ostatni przeciwnik
public class Boss extends Przeciwnik {
    //ATRYBUTY WŁASNE
    private int i;
    private boolean przelacznik_ataku;
    private boolean przelacznik_ruchu;
    
    //KONSTRUKTORY
    public Boss(int x, int y, int pz, int pkt, int px, int py, int pa) {
        super(7, x, y, pz, pkt, "Grafika/Boss1.png", "Grafika/Boss1_2.png", px, py, pa);
        i                 = 0;
        przelacznik_ataku = false;
        przelacznik_ruchu = false;
    }
    public Boss(int x, int y, int pz, int pkt, int px, int py, int pa, int cca, int ii, boolean p_a, boolean p_r ) {
        super(7, x, y, pz, pkt, "Grafika/Boss1.png", "Grafika/Boss1_2.png", px, py, pa);
        chwila_czasu_atak = cca;
        i                 = ii;
        przelacznik_ataku = p_a;
        przelacznik_ruchu = p_r;
    }
    
    //METODY (GETTERY)
    public int get_i(){
        return i;
    }
    public boolean get_przelacznik_ataku(){
        return przelacznik_ataku;
    }
    public boolean get_przelacznik_ruchu(){
        return przelacznik_ruchu;
    }
    
    //METODY
    @Override
    public String dane(String napis) 
    {
        napis = super.dane(napis);
        for(int i=0; i<3; i++){
            switch(i){
                case 0: {napis += get_i();                 break;}
                case 1: {napis += get_przelacznik_ataku(); break;}
                case 2: {napis += get_przelacznik_ruchu(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    public void ruch(int x, int y){
        if(wsp_y<30){
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
            if(wsp_x + dx > 680){
                wsp_x = 680;
                przelacznik_ruchu = true;
            }
            else
                wsp_x += dx;
        }
    }
    @Override
    public void atak (ArrayList<Obiekt> aktywne_obiekty){
        if(punkty_zdrowia>=300)      {atak1(aktywne_obiekty);}
        else if(punkty_zdrowia>=250) {atak2(aktywne_obiekty);}
        else if(punkty_zdrowia>=200) {atak3(aktywne_obiekty); atak2(aktywne_obiekty);}
        else if(punkty_zdrowia>=150) {atak4(aktywne_obiekty);}
        else                         {atak5(aktywne_obiekty);}
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }
    public void atak1(ArrayList<Obiekt> aktywne_obiekty){
        if((wsp_y>0) && (wsp_y<640)){
            if(chwila_czasu_atak%20==0){
                Pocisk pocisk = new Pocisk2(wsp_x + 30, wsp_y + 100, 4, "Grafika/PociskBossa1.png");
                aktywne_obiekty.add(pocisk);
            }
            else if(chwila_czasu_atak%20==10){
                Pocisk pocisk = new Pocisk2(wsp_x + 85, wsp_y + 100, 4, "Grafika/PociskBossa2.png");
                aktywne_obiekty.add(pocisk);
            }
        }
    }
    public void atak2(ArrayList<Obiekt> aktywne_obiekty){
        if((wsp_y>0) && (wsp_y<640)){
                if(chwila_czasu_atak%40==0){
                    double alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+100),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x+30));
                    aktywne_obiekty.add(new Pocisk3(wsp_x + 30, wsp_y + 100, 10, alfa, 0, "Grafika/PociskBossa1.png"));
                }
                else if(chwila_czasu_atak%40==20){
                    double alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+100),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x+90));
                    aktywne_obiekty.add(new Pocisk3(wsp_x + 85, wsp_y + 100, 10, alfa, 0, "Grafika/PociskBossa2.png"));
                }
            }
    }
    public void atak3(ArrayList<Obiekt> aktywne_obiekty){
        if((wsp_y<640) && (i%5==0)){
            double x = wsp_x + 55;  //+100*cos(alfa);
            double y = wsp_y + 120;  //+100*sin(alfa);
            double alfa = Math.PI + 2*i*Math.PI/180;
            if     (alfa >= 170*Math.PI/180) przelacznik_ataku = false;
            else if(alfa <=  10*Math.PI/180) przelacznik_ataku = true;
            String sc = (i%2==0) ? "Grafika/PociskBossa2.png" : "Grafika/PociskBossa1.png";
            aktywne_obiekty.add(new Pocisk3((int)x, (int)y, 3, alfa, 0, sc));
        }
        if(przelacznik_ataku) i++;
        else                  i--;

    }
    public void atak4(ArrayList<Obiekt> aktywne_obiekty){
        if((wsp_y>0) && (wsp_y<640)){
            if(chwila_czasu_atak%40 == 0){
                double alfa;
                alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+100),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x));
                aktywne_obiekty.add(new Pocisk3(wsp_x, wsp_y + 100, 10, alfa, 0, "Grafika/PociskBossa1.png"));
                alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+100),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x+120));
                aktywne_obiekty.add(new Pocisk3(wsp_x + 120, wsp_y + 100, 10, alfa, 0, "Grafika/PociskBossa2.png"));
                alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+100),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x+30));
                aktywne_obiekty.add(new Pocisk3(wsp_x +30, wsp_y + 100, 10, alfa, 0, "Grafika/PociskBossa1.png"));
                alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - (wsp_y+100),aktywne_obiekty.get(0).get_wsp_x()+30 - (wsp_x+80));
                aktywne_obiekty.add(new Pocisk3(wsp_x + 80, wsp_y + 100, 10, alfa, 0, "Grafika/PociskBossa2.png"));
            }
        }
        if((chwila_czasu_atak%200 == 0) && (wsp_y>0) && (wsp_y<640)){
            int ilosc_pociskow = 20;
            int a             = (przelacznik_ataku) ? 1 : -1;
            przelacznik_ataku = (przelacznik_ataku) ? false: true;
            for(int i=0;i<ilosc_pociskow;i++)
            {
                double alfa = 360/ilosc_pociskow*i*Math.PI/180;
                double ruch_alfa = a*0.01;;
                double x = wsp_x + 55;
                double y = wsp_y + 120;
                aktywne_obiekty.add(new Pocisk3((int)x, (int)y, 2, alfa, ruch_alfa, "Grafika/PociskBossa1.png"));
            }
       }
    }
    public void atak5(ArrayList<Obiekt> aktywne_obiekty){
        atak4(aktywne_obiekty);
        if((chwila_czasu_atak%200 == 0) && (wsp_y>0) && (wsp_y<640)){
            int ilosc_pociskow = 30;
            for(int i=0; i<ilosc_pociskow; i++)
            {
                double alfa = 360/ilosc_pociskow*i*Math.PI/180;
                double x    = wsp_x + 55 + 100*cos(alfa);
                double y    = wsp_y + 55 + 100*sin(alfa);
                alfa = Math.atan2(aktywne_obiekty.get(0).get_wsp_y()+30 - y,aktywne_obiekty.get(0).get_wsp_x()+30 - x);
                String sc=(i%2==0) ? "Grafika/PociskBossa1.png" : "Grafika/PociskBossa2.png";
                aktywne_obiekty.add(new Pocisk3((int) x, (int) y, 6, alfa, 0, sc));
            }
        }
    }
    
    @Override
    protected void pokaz_zdrowie(Graphics2D g2d){
        if(punkty_zdrowia<10)
            g2d.drawString(""+punkty_zdrowia, wsp_x+57, wsp_y+20);
        else if(punkty_zdrowia<100)
            g2d.drawString(""+punkty_zdrowia, wsp_x+54, wsp_y+20);
        else if(punkty_zdrowia<1000)
            g2d.drawString(""+punkty_zdrowia, wsp_x+51, wsp_y+20);
        else
            g2d.drawString(""+punkty_zdrowia, wsp_x+49, wsp_y+20);
    }
    
    @Override
    protected void kolidowanie(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2){
        //tylko pociski gracza
        if((aktywne_obiekty.get(nr_na_liscie2).get_typ() == 3) && (aktywne_obiekty.get(nr_na_liscie2).get_podtyp() == 1)){ 
            int x2Points[] = {wsp_x,      wsp_x + 120, wsp_x + 59};
            int y2Points[] = {wsp_y + 10, wsp_y + 10, wsp_y + 120};
            //przybliżanie kształtu trójkątem
            GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);
            polyline.moveTo (x2Points[0], y2Points[0]);
            for (int index = 1; index < x2Points.length; index++) {
                polyline.lineTo(x2Points[index], y2Points[index]);
            }
            if(polyline.intersects(x, y, szer,wys)){
                //Instrukcje wywolane kolizja
                aktywne_obiekty.get(nr_na_liscie2).skutki_kolizji(this, aktywne_obiekty, nr_na_liscie2);
                //generowanie bonusow
                if(punkty_zdrowia <= 0){
                    smierc(x, y, szer, wys, aktywne_obiekty, nr_na_liscie1, nr_na_liscie2);
                }
            }
        }
    }
}
