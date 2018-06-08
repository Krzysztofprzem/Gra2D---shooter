/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author krzys
 */

//Klasa główna dla przeciwników
public class Przeciwnik extends Obiekt {
    //ATRYBUTY WŁASNE
    protected int dx;
    protected int dy;
    protected int chwila_czasu_ruch;
    protected int chwila_czasu_atak;
    protected int predkosc_ataku;
    protected AudioClip dzwiek;
    
    //KONSTRUKTORY
    public Przeciwnik(int p, int x, int y, int pz, int pkt, String sc1, String sc2, int px, int py, int pa){
        typ               = 2;
        podtyp            = p;
        animacja          = true;
        wsp_x             = x;
        wsp_y             = y;
        dx                = px;
        dy                = py;
        punkty_zdrowia    = pz;
        punkty            = pkt;
        sciezka1          = sc1;
        sciezka2          = sc2;
        obrazek1          = new ImageIcon(sciezka1).getImage();
        obrazek2          = new ImageIcon(sciezka2).getImage();
        chwila_czasu_ruch = 0;
        chwila_czasu_atak = 0;
        predkosc_ataku    = pa;
        try{
            dzwiek = Applet.newAudioClip(new URL("file:Dźwięk/Zniszczenie.mp3"));
        }
        catch(Exception e){ 
        }
    }
    @Override
    public void ruch(int x, int y){
        if(chwila_czasu_ruch < 60){
            wsp_y += 1;
        }
        chwila_czasu_ruch++;
    }
    
    //METODY (GETTERY)
    protected int get_dx(){
        return dx;
    }
    protected int get_dy(){
        return dy;
    }
    public int get_predkosc_ataku(){
        return predkosc_ataku;
    }
    protected int get_chwila_czasu_ruch(){
        return chwila_czasu_ruch;
    }
    protected int get_chwila_czasu_atak(){
        return chwila_czasu_atak;
    }
    
    //METODY
    @Override
    public String dane(String napis) {
        for(int i=0; i<14; i++){
            switch(i){
                case 0:  {napis += get_typ();               break;}
                case 1:  {napis += get_podtyp();            break;}
                case 2:  {napis += get_wsp_x();             break;}
                case 3:  {napis += get_wsp_y();             break;}
                case 4:  {napis += get_animacja();          break;}
                case 5:  {napis += get_punkty_zdrowia();    break;}
                case 6:  {napis += get_punkty();            break;}
                case 7:  {napis += get_sciezka1();          break;}
                case 8:  {napis += get_sciezka2();          break;}
                case 9:  {napis += get_dx();                break;}
                case 10: {napis += get_dy();                break;}
                case 11: {napis += get_predkosc_ataku();    break;}
                case 12: {napis += get_chwila_czasu_ruch(); break;}
                case 13: {napis += get_chwila_czasu_atak(); break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if((chwila_czasu_atak == 0) && (wsp_y>0) && (wsp_y<640)){
            Pocisk pocisk = new Pocisk2(wsp_x + 25, wsp_y + 60, 4, "Grafika/Pocisk2.png");
            aktywne_obiekty.add(pocisk);
       }
        chwila_czasu_atak++;
        if(chwila_czasu_atak == predkosc_ataku)
            chwila_czasu_atak = 0;
    }
    
    @Override
    public void rysuj(Graphics2D g2d){
        if(animacja){
            g2d.drawImage(obrazek1, wsp_x, wsp_y, null);
            animacja = false;
        }
        else{
            g2d.drawImage(obrazek2, wsp_x, wsp_y, null);
            animacja = true;
        }
    }

    @Override
    protected void kolidowanie(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2){
        //tylko pociski gracza
        if((aktywne_obiekty.get(nr_na_liscie2).get_typ() == 3) && (aktywne_obiekty.get(nr_na_liscie2).get_podtyp() == 1)){ 
            int x2Points[] = {wsp_x,      wsp_x + 60, wsp_x + 29};
            int y2Points[] = {wsp_y + 10, wsp_y + 10, wsp_y + 60};
            //przybliżanie kształtu trójkątem
            GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);
            polyline.moveTo (x2Points[0], y2Points[0]);
            for (int index = 1; index < x2Points.length; index++) {
                polyline.lineTo(x2Points[index], y2Points[index]);
            }
            if(polyline.intersects(x, y, szer,wys)){
                //Instrukcje wywołane kolizją
                aktywne_obiekty.get(nr_na_liscie2).skutki_kolizji(this, aktywne_obiekty, nr_na_liscie2);
                if(punkty_zdrowia <= 0){
                    smierc(x, y, szer, wys, aktywne_obiekty, nr_na_liscie1, nr_na_liscie2);
                }
            }
        }
    }
    @Override
    void smierc(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2) {
        //generowanie bonusow
        aktywne_obiekty.get(0).set_punkty(aktywne_obiekty.get(0).get_punkty() + get_punkty());
        Random generator;
        generator = new Random();
        int b = generator.nextInt(100)+1;
        if(b<=60){
            int rodzaj;
            if     (b<=6)  rodzaj = 1;
            else if(b<=26) rodzaj = 2;
            else if(b<=40) rodzaj = 3;
            else if(b<=59) rodzaj = 4;
            else           rodzaj = 5;
            switch(rodzaj){
                case 1:{aktywne_obiekty.add(new Bonus1(wsp_x+20, wsp_y+20, "Grafika/Bonus1.png")); break;}
                case 2:{aktywne_obiekty.add(new Bonus2(wsp_x+20, wsp_y+20, "Grafika/Bonus2.png")); break;}
                case 3:{aktywne_obiekty.add(new Bonus3(wsp_x+20, wsp_y+20, "Grafika/Bonus3.png")); break;}
                case 4:{aktywne_obiekty.add(new Bonus4(wsp_x+20, wsp_y+20, "Grafika/Bonus4.png")); break;}
                case 5:{aktywne_obiekty.add(new Bonus5(wsp_x+20, wsp_y+20, "Grafika/Bonus5.png")); break;}
            }
        }
        //dzwiek.play();
        aktywne_obiekty.remove(nr_na_liscie1);
    }

    @Override
    void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie) {
    }

    @Override
    void skutki_kolizji(Przeciwnik przeciwnik, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie) {
    }
}