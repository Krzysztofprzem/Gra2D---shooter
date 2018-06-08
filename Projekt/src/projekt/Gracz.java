/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import javax.swing.ImageIcon;


/**
 *
 * @author krzys
 */
public class Gracz extends Obiekt{
    //ATRYBUTY WŁASNE
    private int przerwa;
    
    //KONSTRUKTORY
    public Gracz(int x, int y){
        typ            = 1;
        punkty_zdrowia = 20;
        punkty         = 0;
        animacja       = true;
        wymiary_x      = 60;
        wymiary_y      = 60;
        wsp_x          = x;
        wsp_y          = y;
        sciezka1       = "Grafika/Gracz1.png"; 
        sciezka2       = "Grafika/Gracz1_2.png";
        obrazek1       = new ImageIcon(sciezka1).getImage();
        obrazek2       = new ImageIcon(sciezka2).getImage();
        przerwa        = 0;
    }
    public Gracz(int x, int y, boolean anim, int pz, int pkt, int prz){
        typ            = 1;
        wymiary_x      = 60;
        wymiary_y      = 60;
        wsp_x          = x;
        wsp_y          = y;
        animacja       = anim;
        punkty_zdrowia = pz;
        punkty         = pkt;
        przerwa        = prz;
        sciezka1       = "Grafika/Gracz1.png"; 
        sciezka2       = "Grafika/Gracz1_2.png";
        obrazek1       = new ImageIcon(sciezka1).getImage();
        obrazek2       = new ImageIcon(sciezka2).getImage();
    }
    
    //METODY (GETTERY)
    public int get_przerwa(){
        return przerwa;
    }
    
    //METODY
    @Override
    public String dane(String napis){
        for(int i=0; i<9; i++){
            switch(i){
                case 0:{napis += get_typ();            break;}
                case 1:{napis += get_wsp_x();          break;}
                case 2:{napis += get_wsp_y();          break;}
                case 3:{napis += get_animacja();       break;}
                case 4:{napis += get_punkty_zdrowia(); break;}
                case 5:{napis += get_punkty();         break;}
                case 6:{napis += get_sciezka1();       break;}
                case 7:{napis += get_sciezka2();       break;}
                case 8:{napis += get_przerwa();        break;}
            }
            napis += System.getProperty("line.separator");
        }
        return napis;
    }
    
    @Override
    public void ruch(int x, int y){
        x = (wsp_x+x < 0)   ? 0          : x;
        x = (wsp_x+x > 740) ? 740 -wsp_x : x;
        y = (wsp_y+y < 24)  ? 24  -wsp_y : y;
        y = (wsp_y+y > 538) ? 538 -wsp_y : y;
        wsp_x += x;
        wsp_y += y;
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
    public void atak(ArrayList<Obiekt> aktywne_obiekty){
        if(przerwa%5 == 0){
            przerwa = 0;
            Pocisk pocisk = new Pocisk1(wsp_x+25, wsp_y, 10, "Grafika/Pocisk1.png");
            aktywne_obiekty.add(pocisk);
            przerwa++;
        }
        else 
            przerwa++;
    }

    @Override
    public void kolidowanie(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2) {
        //tylko pociski przeciwnikow i bonusy
        if(((aktywne_obiekty.get(nr_na_liscie2).get_typ()==3)&&(aktywne_obiekty.get(nr_na_liscie2).get_podtyp()!=1)) || (aktywne_obiekty.get(nr_na_liscie2).get_typ()==4)){
            int x2Points[] = {wsp_x+30, wsp_x+45, wsp_x+15};
            int y2Points[] = {wsp_y+15, wsp_y+50, wsp_y+50};
            //przybliżanie kształtu trójkątem
            GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);
            polyline.moveTo (x2Points[0], y2Points[0]);
            for (int index = 1; index < x2Points.length; index++) {
                polyline.lineTo(x2Points[index], y2Points[index]);
            }
            if(polyline.intersects(x, y ,szer, wys)){
                //Instrukcje wywołane kolizją
                aktywne_obiekty.get(nr_na_liscie2).skutki_kolizji(this, aktywne_obiekty, nr_na_liscie2);
            }
        }
    }
    
    @Override
    void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie) {
    }

    @Override
    void skutki_kolizji(Przeciwnik przeciwnik, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie) {
    }

    @Override
    void smierc(int x, int y, int szer, int wys, ArrayList<Obiekt> aktywne_obiekty, int nr_na_liscie1, int nr_na_liscie2) {
    } 
}
