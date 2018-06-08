/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
/**
 *
 * @author krzys
 */
public class Bonus5 extends Bonus {
    //ATRYBUTY WŁASNE
    
    //KONSTRUKTORY
    public Bonus5(int x, int y, String sciezka){
        super(5, x, y, 500, "Grafika/Bonus5_1.png");  
        sciezka2  = "Grafika/Bonus5_2.png";  
        obrazek2  = new ImageIcon(sciezka2).getImage();
    }
    //METODY
    @Override
    public void rysuj(Graphics2D g2d){
        g2d.drawImage(obrazek1, wsp_x, wsp_y, null);
        g2d.drawImage(obrazek2, wsp_x-20, wsp_y-20, null);
    }
    
    @Override
    void skutki_kolizji(Gracz gracz, ArrayList<Obiekt> aktywne_obiekty, int numer_na_liscie) {
        super.skutki_kolizji(gracz, aktywne_obiekty, numer_na_liscie);
        gracz.set_punkty_zdrowia(gracz.get_punkty_zdrowia()+1);
    }
}
