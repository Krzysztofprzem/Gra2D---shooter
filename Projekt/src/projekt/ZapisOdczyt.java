/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krzys
 */
public class ZapisOdczyt {
    protected String sciezka;
    ZapisOdczyt(String sc){
        sciezka = sc;
    }
    //ZAPIS DANYCH WSZYSTKICH AKTYWNYCH OBIEKTÓW DO PLIKU TEKSTOWEGO
    public void zapis(ObslugaObiektow ObsObj){
        String dane="";
        dane += ObsObj.get_nr_fali();
        dane += System.getProperty("line.separator");
        dane += "<-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><->";
        dane += System.getProperty("line.separator");
        //Wyciąganie wszystkich istotnych atrybutów z wszystkich aktywnych obiektów do jednego Stringa
        for(int i=0; i<ObsObj.get_aktywne_obiekty().size(); i++){
            dane = ObsObj.get_aktywne_obiekty().get(i).dane(dane);
            dane += "<-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><->";
            dane += System.getProperty("line.separator");
        }
        try{
            PrintWriter zapis = new PrintWriter(sciezka);
            zapis.println(dane);
            zapis.close();
        }catch(FileNotFoundException ex){
            Logger.getLogger(Projekt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     //ODCZYT DANYCH Z PLIKU TEKSTOWEGO ORAZ ODPOWIEDNIE ICH PRZETWORZENIE
    public void odczyt(ObslugaObiektow ObsObj){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sciezka));
            String linia = null;
            linia = reader.readLine();
            ObsObj.get_aktywne_obiekty().clear(); //Czyszczenie obecnego stanu gry
            ObsObj.set_nr_fali(Integer.parseInt(linia.trim()));
            linia = reader.readLine();
            for(;;){
                linia = reader.readLine();  //Odczytanie typu obiektu
                if(linia == null)
                    break;
                switch(Integer.parseInt(linia.trim())){
                    case 1: {wczytanie_gracza     (linia, reader, ObsObj.get_aktywne_obiekty()); break;}
                    case 2: {wczytanie_przeciwnika(linia, reader, ObsObj.get_aktywne_obiekty()); break;}
                    case 3: {wczytanie_pocisku    (linia, reader, ObsObj.get_aktywne_obiekty()); break;}
                    case 4: {wczytanie_bonusu     (linia, reader, ObsObj.get_aktywne_obiekty()); break;}
                }
                linia = reader.readLine();  //Odczytanie rozdzielacza
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void wczytanie_gracza(String linia, BufferedReader reader, ArrayList<Obiekt> aktywne_obiekty) throws IOException{
        //Atrybuty klasy "Gracz"
        int x        = 0;
        int y        = 0;
        boolean anim = true;
        int pz       = 0;
        int pkt      = 0;
        String sc1   = "";
        String sc2   = "";
        int prz      = 0;
        //Pobieranie atrybutów klasy Gracz z pliku tekstowego
        for(int i=0; i<8; i++){
            linia = reader.readLine();
            switch(i){
                case 0:{x    = Integer.parseInt(linia.trim());     break;}
                case 1:{y    = Integer.parseInt(linia.trim());     break;}
                case 2:{anim = Boolean.parseBoolean(linia.trim()); break;}
                case 3:{pz   = Integer.parseInt(linia.trim());     break;}
                case 4:{pkt  = Integer.parseInt(linia.trim());     break;}
                case 5:{sc1  = linia;                              break;}
                case 6:{sc2  = linia;                              break;}
                case 7:{prz  = Integer.parseInt(linia.trim());     break;}
            }
        }
        aktywne_obiekty.add(new Gracz(x, y, anim, pz, pkt, prz));
    }
    
    private void wczytanie_przeciwnika(String linia, BufferedReader reader, ArrayList<Obiekt> aktywne_obiekty) throws IOException{
        //Atrybuty klasy "Przeciwnik"
        linia = reader.readLine();  //Odczytanie podtypu - numeru klasy dziedziczącej z klasy "Przeciwnik"
        int     podtyp = Integer.parseInt(linia.trim());
        int     x      = 0; 
        int     y      = 0;
        boolean anim   = true;
        int     pz     = 0; 
        int     pkt    = 0; 
        String  sc1    = "";
        String  sc2    = "";
        int     px     = 0; 
        int     py     = 0;
        int     pa     = 0;
        int     ccr    = 0; 
        int     cca    = 0; 
        //Pobieranie atrybutów klasy Przeciwnik z pliku tekstowego
        for(int i=0; i<12; i++){
            linia = reader.readLine();
            switch(i){
                case 0:  {x    = Integer.parseInt(linia.trim());     break;}
                case 1:  {y    = Integer.parseInt(linia.trim());     break;}
                case 2:  {anim = Boolean.parseBoolean(linia.trim()); break;}
                case 3:  {pz   = Integer.parseInt(linia.trim());     break;}
                case 4:  {pkt  = Integer.parseInt(linia.trim());     break;}
                case 5:  {sc1  = linia;                              break;}
                case 6:  {sc2  = linia;                              break;}
                case 7:  {px   = Integer.parseInt(linia.trim());     break;}
                case 8:  {py   = Integer.parseInt(linia.trim());     break;}
                case 9:  {pa   = Integer.parseInt(linia.trim());     break;}
                case 10: {ccr  = Integer.parseInt(linia.trim());     break;}
                case 11: {cca  = Integer.parseInt(linia.trim());     break;}
            }
        }
        //Na podstawie podtypu wybierany jest sposób pobrania następnych atrybutów oraz
        //wczytania obiektu o poprawnej klasie
        switch(podtyp){
            //Wczytanie obiektu - Przeciwnik1
            case 1:{
                int pc1 = 0;
                int pc2 = 0;
                linia   = reader.readLine();
                pc1     = Integer.parseInt(linia.trim());
                linia   = reader.readLine();
                pc2     = Integer.parseInt(linia.trim());
                aktywne_obiekty.add(new Przeciwnik1(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc1, pc2));
                break;
            }
            //Wczytanie obiektu - Przeciwnik2
            case 2:{
                int     pc = 0;
                boolean p  = true;
                linia      = reader.readLine();
                pc         = Integer.parseInt(linia.trim());
                linia      = reader.readLine();
                p          = Boolean.parseBoolean(linia.trim());
                aktywne_obiekty.add(new Przeciwnik2(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc, p));
                break;
            }
            //Wczytanie obiektu - Przeciwnik3
            case 3:{
                int     pc = 0;
                boolean p  = true;
                linia      = reader.readLine();
                pc         = Integer.parseInt(linia.trim());
                linia      = reader.readLine();
                p          = Boolean.parseBoolean(linia.trim());
                aktywne_obiekty.add(new Przeciwnik3(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc, p));
                break;
            }
            //Wczytanie obiektu - Przeciwnik4
            case 4:{
                int     pc = 0;
                boolean p  = true;
                linia      = reader.readLine();
                pc         = Integer.parseInt(linia.trim());
                linia      = reader.readLine();
                p          = Boolean.parseBoolean(linia.trim());
                aktywne_obiekty.add(new Przeciwnik4(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc, p));
                break;
            }
            //Wczytanie obiektu - Przeciwnik5
            case 5:{
                int pc1 = 0;
                int pc2 = 0;
                linia   = reader.readLine();
                pc1     = Integer.parseInt(linia.trim());
                linia   = reader.readLine();
                pc2     = Integer.parseInt(linia.trim());
                aktywne_obiekty.add(new Przeciwnik5(x, y, anim, pz, pkt, px, py, pa, ccr, cca, pc1, pc2));
                break;
            }
            //Wczytanie obiektu - Przeciwnik6
            case 6:{
                int     ii  = 0;
                boolean p_a = true;
                linia       = reader.readLine();
                ii          = Integer.parseInt(linia.trim());
                linia       = reader.readLine();
                p_a         = Boolean.parseBoolean(linia.trim());
                aktywne_obiekty.add(new Przeciwnik6(x, y, anim, pz, pkt, px, ccr, py, ii, p_a)); 
                break;
            }
            //Wczytanie obiektu - Boss
            case 7:{
                int     ii  = 0;
                boolean p_a = true;
                boolean p_r = true;
                for(int i =0; i<3; i++){
                    linia   = reader.readLine();
                    switch(i){
                        case 0:{ii  = Integer.parseInt(linia.trim())    ; break;}
                        case 1:{p_a = Boolean.parseBoolean(linia.trim()); break;}
                        case 2:{p_r = Boolean.parseBoolean(linia.trim()); break;}
                    }
                }
                aktywne_obiekty.add(new Boss(x, y, pz, pkt, px, py, pa, cca, ii, p_a, p_r )); 
                break;
            }
        }          
    }
    
    private void wczytanie_pocisku(String linia, BufferedReader reader, ArrayList<Obiekt> aktywne_obiekty) throws IOException{
        //Atrybuty klasy "Pocisk"
        linia = reader.readLine();  //Odczytanie podtypu - numeru klasy dziedziczącej z klasy "Pocisk"
        int podtyp = Integer.parseInt(linia.trim());
        int x      = 0;
        int y      = 0;
        String sc  = "";
        int px     = 0; 
        int py     = 0;
        //Pobieranie atrybutów klasy Pocisk z pliku tekstowego
        for(int i=0; i<5; i++){
            linia = reader.readLine();
            switch(i){
                case 0:{x  = Integer.parseInt(linia.trim()); break;}
                case 1:{y  = Integer.parseInt(linia.trim()); break;}
                case 2:{sc = linia                         ; break;}
                case 3:{px = Integer.parseInt(linia.trim()); break;}
                case 4:{py = Integer.parseInt(linia.trim()); break;}
            }
        } 
        //Na podstawie podtypu wybierany jest sposób pobrania następnych atrybutów oraz
        //wczytania obiektu o poprawnej klasie
        switch(podtyp){
            //Wczytanie obiektu - Pocisk1
            case 1:{aktywne_obiekty.add(new Pocisk1(x, y, py, sc)); break;}
            //Wczytanie obiektu - Pocisk2
            case 2:{aktywne_obiekty.add(new Pocisk2(x, y, py, sc)); break;}
            //Wczytanie obiektu - Pocisk3
            case 3:{
                int    r  = 0;
                double a  = 0;
                double a2 = 0;
                int    xx = 0;
                int    yy = 0;
                double ii = 0;
                for(int i=0; i<6; i++){
                    linia = reader.readLine();
                    switch(i){
                        case 0:{r  = Integer.parseInt(linia.trim());   break;}
                        case 1:{a  = Double.parseDouble(linia.trim()); break;}
                        case 2:{a2 = Double.parseDouble(linia.trim()); break;}
                        case 3:{xx = Integer.parseInt(linia.trim());   break;}
                        case 4:{yy = Integer.parseInt(linia.trim());   break;}
                        case 5:{ii = Double.parseDouble(linia.trim()); break;}
                    }
                }
                aktywne_obiekty.add(new Pocisk3(x, y, r, a, a2, xx, yy, ii, sc)); 
                break;
            }
        }
    }
    
    private void wczytanie_bonusu(String linia, BufferedReader reader, ArrayList<Obiekt> aktywne_obiekty) throws IOException{
        //Atrybuty klasy "Pocisk"
        linia = reader.readLine();  //Odczytanie podtypu - numeru klasy dziedziczącej z klasy "Pocisk"
        int podtyp = Integer.parseInt(linia.trim());
        int x      = 0;
        int y      = 0;
        int pkt    = 0;
        String sc  = "";
        //Pobieranie atrybutów klasy Gracz z pliku tekstowego
        for(int i=0; i<4; i++){
            linia = reader.readLine();
            switch(i){
                case 0:{x   = Integer.parseInt(linia.trim()); break;}
                case 1:{y   = Integer.parseInt(linia.trim()); break;}
                case 2:{pkt = Integer.parseInt(linia.trim()); break;}
                case 3:{sc  = linia                         ; break;}
            }
        }  
        //Na podstawie podtypu wybierany jest sposób pobrania następnych atrybutów oraz
        //wczytania obiektu o poprawnej klasie
        switch(podtyp){
            //Wczytanie obiektu - Bonus1
            case 1:{aktywne_obiekty.add(new Bonus1(x, y, sc)); break;}
            //Wczytanie obiektu - Bonus2
            case 2:{aktywne_obiekty.add(new Bonus2(x, y, sc)); break;}
            //Wczytanie obiektu - Bonus3
            case 3:{aktywne_obiekty.add(new Bonus3(x, y, sc)); break;}
            //Wczytanie obiektu - Bonus4
            case 4:{aktywne_obiekty.add(new Bonus4(x, y, sc)); break;}
            //Wczytanie obiektu - Bonus5
            case 5:{aktywne_obiekty.add(new Bonus5(x, y, sc)); break;}
        }
    }
}
