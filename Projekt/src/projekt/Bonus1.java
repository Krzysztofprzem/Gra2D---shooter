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
public class Bonus1 extends Bonus {
    //ATRYBUTY WŁASNE
    
    //KONSTRUKTORY
    public Bonus1(int x, int y, String sciezka) {
        super(1, x, y, 10, "Grafika/Bonus1.png");
    }
    //METODY
    @Override
    void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie) {
        super.skutki_kolizji(gracz, aktywne_obiekty, numer_na_liscie);
    }
    
}
