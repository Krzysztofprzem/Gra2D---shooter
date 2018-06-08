/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krzys
 */
public class Wyniki {
    protected String sciezka;
    public Wyniki(String sc){
        sciezka = sc;
    }
    
    //ODCZYT WYNIKÓW Z PLIKU TEKSTOWEGO
    public void odczyt(String []wyniki_imiona, int []wyniki_punkty, String []wyniki_wp, String []wyniki_daty){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sciezka));
            String linia = null;
            for(int i=0; i<10; i++){
                linia            = reader.readLine();
                wyniki_imiona[i] = linia;
                linia            = reader.readLine();
                wyniki_punkty[i] = Integer.parseInt(linia);
                linia            = reader.readLine();
                wyniki_wp[i]     = linia;
                linia            = reader.readLine();
                wyniki_daty[i]   = linia;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //ZAPIS OBECNYCH WYNIKÓW DO PLIKU TEKSTOWEGO
    public void zapis(String []wyniki_imiona, int []wyniki_punkty, String []wyniki_wp, String []wyniki_daty){
        String dane = "";
        for(int i=0; i<10; i++){
            dane += wyniki_imiona[i];
            dane += System.getProperty("line.separator");
            dane += wyniki_punkty[i];
            dane += System.getProperty("line.separator");
            dane += wyniki_wp[i];
            dane += System.getProperty("line.separator");
            dane += wyniki_daty[i];
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
    
}
