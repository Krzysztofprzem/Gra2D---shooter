/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList; 
import javax.swing.ImageIcon;

/**
 *
 * @author krzys
 */
public class ObslugaObiektow {
    //ATRYBUTY
    private int               nr_fali;
    private int               ilosc_przeciwnikow_w_obecnej_fali;
    private int               ilosc_bonusow_w_obecnej_fali;
    private int               ilosc_pociskow_przeciwnika_w_obecnej_fali;
    private Image             tlo;
    private ArrayList<Obiekt> aktywne_obiekty;
    private int               granica_x;
    private int               granica_y;
    private int               rozmiar_okna_x;
    private int               rozmiar_okna_y;
    
    //KONSTRUKTOR
    public ObslugaObiektow(int x, int y){
        rozmiar_okna_x                            = x;
        rozmiar_okna_y                            = y;
        granica_x                                 = 400;
        granica_y                                 = 400;
        nr_fali                                   = 0;
        ilosc_przeciwnikow_w_obecnej_fali         = 0;
        ilosc_bonusow_w_obecnej_fali              = 0;
        ilosc_pociskow_przeciwnika_w_obecnej_fali = 0;
        tlo                                       = new ImageIcon("Grafika/Tlo.jpg").getImage();
        aktywne_obiekty                           = new ArrayList<Obiekt>();
        aktywne_obiekty.add(new Gracz(rozmiar_okna_x/2-30, rozmiar_okna_y-60));
        
    }
    
    //METODY (GETTERY)
    public Image get_tlo(){
        return tlo;
    }
    public Gracz get_gracz(){
        if(aktywne_obiekty.get(0).get_typ() == 1)
            return (Gracz)aktywne_obiekty.get(0);
        else{
            Gracz temp = new Gracz(0,0);
            return temp;
        }
    }
    public ArrayList<Obiekt> get_aktywne_obiekty(){
        return aktywne_obiekty;
    }
    public int get_nr_fali(){
        return nr_fali;
    }
    
    //METODY (SETTERY)
    public void set_nr_fali(int a){
        nr_fali = a;
    }
    
    //METODY
    public void rysowanie_obiektow(Graphics2D g2d){
        for(int i=0; i<aktywne_obiekty.size(); i++){
            aktywne_obiekty.get(i).rysuj(g2d);
            aktywne_obiekty.get(i).pokaz_zdrowie(g2d);
        }
    }
    
    public void aktualizacja_ilosci(){  //przeciwnikow, pociskow przeciwnika i bonusow
        ilosc_przeciwnikow_w_obecnej_fali         = 0;
        ilosc_pociskow_przeciwnika_w_obecnej_fali = 0;
        ilosc_bonusow_w_obecnej_fali              = 0;
        for(int i=1; i<aktywne_obiekty.size(); i++){
            if(aktywne_obiekty.get(i).get_typ() != 2){
                if(aktywne_obiekty.get(i).get_typ() == 4)
                    ilosc_bonusow_w_obecnej_fali++;
                else if((aktywne_obiekty.get(i).get_typ()    == 3) && 
                        (aktywne_obiekty.get(i).get_podtyp() != 1))
                    ilosc_pociskow_przeciwnika_w_obecnej_fali++;
            }
            else 
                ilosc_przeciwnikow_w_obecnej_fali++;
        }
    }
    
    //USUWANIE NIEPOTRZEBNYCH OBIEKTÓW
    public void czyszczenie_obiektow(){
        //usuwanie pocisków gracza i bonusów
        for(int i=1; i<aktywne_obiekty.size(); i++){
            if((((aktywne_obiekty.get(i).get_typ() == 3 )              &&
                 (aktywne_obiekty.get(i).get_podtyp() == 1 ))          ||
                 (aktywne_obiekty.get(i).get_typ() == 4 ))             &&
                ((aktywne_obiekty.get(i).get_wsp_x() < -10)            ||
                 (aktywne_obiekty.get(i).get_wsp_x() > rozmiar_okna_x) ||
                 (aktywne_obiekty.get(i).get_wsp_y() < -10)            ||
                 (aktywne_obiekty.get(i).get_wsp_y() > rozmiar_okna_y))){
                  aktywne_obiekty.remove(i);
                i--;
            }
        }
        //usuwanie pocisków przeciwników
        for(int i=1; i<aktywne_obiekty.size(); i++){
            if((aktywne_obiekty.get(i).get_typ() == 3 )                          &&
              ((aktywne_obiekty.get(i).get_wsp_x() < -1*granica_x)               ||
               (aktywne_obiekty.get(i).get_wsp_x() > rozmiar_okna_x + granica_x) ||
               (aktywne_obiekty.get(i).get_wsp_y() < -1*granica_y)               ||
               (aktywne_obiekty.get(i).get_wsp_y() > rozmiar_okna_y + granica_y))){
                aktywne_obiekty.remove(i);
            i--;
            }
        }
    }
    
    //WYKONANIE RUCHU I ATAKU DLA WSZYSTKICH OBIEKTÓW (POZA GRACZEM)
    public void ruch_obiektow(){
        for(int i=1; i<aktywne_obiekty.size(); i++){
            aktywne_obiekty.get(i).ruch(0, 0);
            aktywne_obiekty.get(i).atak(aktywne_obiekty);
        }
    }
    
    //SPRAWDZANIE CZY OBIEKTY ZE SOBĄ KOLIDUJĄ
    public void kolizje(){
        //Dla gracza i wszystkich przeciwnikow trzeba przejrzec cala liste obiektow
        for(int i=0; i<ilosc_przeciwnikow_w_obecnej_fali+1; i++)
            for(int j=ilosc_przeciwnikow_w_obecnej_fali+1; j<aktywne_obiekty.size(); j++)
                aktywne_obiekty.get(i).kolidowanie(aktywne_obiekty.get(j).get_wsp_x(), aktywne_obiekty.get(j).get_wsp_y(), aktywne_obiekty.get(j).get_wymiary_x(), aktywne_obiekty.get(j).get_wymiary_y(), aktywne_obiekty, i, j);
    }
    
    //INSTRUKCJE WYKONYWANE PO ZAKOŃCZENIU OBECNEJ FALI
    public void zakonczenie_fali(){
        if((ilosc_przeciwnikow_w_obecnej_fali         == 0) && 
           (ilosc_bonusow_w_obecnej_fali              == 0) && 
           (ilosc_pociskow_przeciwnika_w_obecnej_fali == 0)){
            Gracz temp = (Gracz)aktywne_obiekty.get(0);
            aktywne_obiekty.clear();
            aktywne_obiekty.add(temp);
            if(nr_fali<8)  
                nr_fali++;
            fale_przeciwnikow();
        }
    }
    
    //TWORZENIE KOLEJNYCH FAL PRZECIWNIKÓW
    public void fale_przeciwnikow(){
        switch(nr_fali){
            //Fala pierwsza
            case 0:{
                for(int i=0;i<5;i++){
                    aktywne_obiekty.add(new Przeciwnik1(i*60,    -35, 4, 100, 1, 1, 150, 100, 100));
                    aktywne_obiekty.add(new Przeciwnik5(60+i*60, -95, 4, 100, 1, 1, 200, 360, 0));
                    aktywne_obiekty.add(new Przeciwnik1(rozmiar_okna_x-60-i*60, -335, 4, 200, -1, 1, 150, 400, 100));
                    aktywne_obiekty.add(new Przeciwnik5(rozmiar_okna_x-120-i*60, -395, 4, 200, -1, 1, 200, 660, 0));
                }
                break;}
            //Fala druga
            case 1:{
                for(int i=0; i<5; i++){
                    aktywne_obiekty.add(new Przeciwnik1(-60, 25+60*i, 10, 100, 1, 1, 200, 0, 300+30*i));
                    aktywne_obiekty.add(new Przeciwnik1(rozmiar_okna_x, 25+60*i, 10, 100, -1, 1, 200, 0, 300+30*i));
                }
                aktywne_obiekty.add(new Przeciwnik2(rozmiar_okna_x/2-60, 25, 15, 150, 1, 1, 200, 100, true));
                aktywne_obiekty.add(new Przeciwnik2(rozmiar_okna_x/2   , 25, 15, 150, 1, 1, 200, 100, false));
                aktywne_obiekty.add(new Przeciwnik3(rozmiar_okna_x/2-30, 25, 20, 300, 1, 1, 200, 100));
                break;}
            //Fala trzecia
            case 2:{
                for(int i=0; i<5; i++)
                    aktywne_obiekty.add(new Przeciwnik5(185*i, -75, 4, 300, 1, 1, 120, 100, 0));
                aktywne_obiekty.add(new Przeciwnik4(rozmiar_okna_x/2-30, -60, 200, 100, 1, 1, 100, 160));
                break;}
            //Fala czwarta
            case 3:{
                for(int i=0; i<10; i++)
                    aktywne_obiekty.add(new Przeciwnik5(82*i, -65, 20, 300, 1, 1, 120, 100, 0));
                for(int i=0; i<5; i++)
                    aktywne_obiekty.add(new Przeciwnik1(100+120*i, -60, 15, 100, 1, 1, 200, 200, 0));
                for(int i=0; i<2; i++){
                    aktywne_obiekty.add(new Przeciwnik2(0, 25+60*i, 10, 150, 1, 1, 200, 100, true));
                    aktywne_obiekty.add(new Przeciwnik2(rozmiar_okna_x-60, 25+60*i, 10, 100, 1, 1, 200, 100, false));
                }
                break;}
            //Fala piąta
            case 4:{
                for(int i=0;i<5;i++){
                    aktywne_obiekty.add(new Przeciwnik5(i*60, -95, 10, 300, 1, 1, 200, 290, 0));
                    aktywne_obiekty.add(new Przeciwnik5(rozmiar_okna_x-60-i*60, -395, 10, 300, -1, 1, 200, 590, 0));
                }
                aktywne_obiekty.add(new Przeciwnik4(120, -60, 125, 200, 1, 1, 250, 180));
                aktywne_obiekty.add(new Przeciwnik4(rozmiar_okna_x-180, -60, 125, 200, 1, 1, 250, 180));
                break;}
            //Fala szósta
            case 5:{
                for(int i=0;i<5;i++){
                    aktywne_obiekty.add(new Przeciwnik5(60+i*120, -95, 10, 300, 1, 1, 200, 260, 0));
                    aktywne_obiekty.add(new Przeciwnik5(rozmiar_okna_x-120-i*120, -395, 10, 300, -1, 1, 200, 560, 0));
                }
                aktywne_obiekty.add(new Przeciwnik6(rozmiar_okna_x/4, -60, 50, 200, 1, 1));
                aktywne_obiekty.add(new Przeciwnik6(rozmiar_okna_x-rozmiar_okna_x/4-60, -60, 50, 200, 1, 1));
                break;}
            //Fala siódma
            case 6:{
                for(int i=0; i<2; i++){
                    aktywne_obiekty.add(new Przeciwnik2(rozmiar_okna_x/10 + 120*i, -60, 15, 300, 1, 1, 200, 185, true));
                    aktywne_obiekty.add(new Przeciwnik2(rozmiar_okna_x-rozmiar_okna_x/10-60 - 120*i , -60, 15, 300, 1, 1, 200, 185, false));
                }
                for(int i=0; i<5; i++){
                    aktywne_obiekty.add(new Przeciwnik5(60+i*60, -95, 20, 150, 1, 1, 200, 360, 0));
                    aktywne_obiekty.add(new Przeciwnik5(rozmiar_okna_x-120-i*60, -95, 20, 150, 1, 1, 200, 360, 0));
                }
                aktywne_obiekty.add(new Przeciwnik4(rozmiar_okna_x/2-30, -60, 200, 400, 1, 1, 300, 185));
                aktywne_obiekty.add(new Przeciwnik3(rozmiar_okna_x/2-90, -60, 20, 500, 1, 1, 400, 185));
                aktywne_obiekty.add(new Przeciwnik3(rozmiar_okna_x/2+30, -60, 20, 500, 1, 1, 400, 185));
                break;}
            //Fala ósma
            case 7:{
                aktywne_obiekty.add(new Boss(rozmiar_okna_x/2-60, -120, 350, 10000, 2, 2, 4000));
                break;}
        }    
    }
}
