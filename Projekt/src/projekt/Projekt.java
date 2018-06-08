package projekt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Projekt extends JFrame{
    //ZMIENNE GLOBALNE
    private boolean         przyciski[];
    
    //Zmienne wykorzystywane przy zapisie i odczycie
    private boolean         stan_zapodcz;
    private int             zapisano;
    private int             wczytano;
    private ZapisOdczyt     zapodcz;
    
    //Zmienne wykorzystywane przy aktualizacji wyników
    private static String   nazwa_gracza;
    private String[]        wyniki_imiona;
    private int[]           wyniki_punkty;
    private String[]        wyniki_wp;
    private String[]        wyniki_daty;
    private Wyniki          wyniki;
    private boolean         aktualizacja_wynikow;
    private int             numer_gracza;
    
    private boolean         koniec;
    private ObslugaObiektow ObsObj;
    private Timer           timer;

    //consty
    private final int rozmiar_okna_x = 800;
    private final int rozmiar_okna_y = 600;
    
    //TIMER / MECHANIKA GRY / GŁÓWNA PĘTLA GRY
    class Glowna_petla extends TimerTask{
        @Override
        public void run(){
            if(stan_zapodcz){    //zapobiega zacieciu podczas zapisu i odczytu
                //REAKCJA NA PRZYCISKI
                
                //STEROWANIE
                if(przyciski[0]) ObsObj.get_gracz().ruch(0 ,-4);
                if(przyciski[1]) ObsObj.get_gracz().ruch(0 , 4);
                if(przyciski[2]) ObsObj.get_gracz().ruch(-4, 0);
                if(przyciski[3]) ObsObj.get_gracz().ruch(4 , 0);
                if(przyciski[4]) ObsObj.get_gracz().atak(ObsObj.get_aktywne_obiekty());
                
                //ZAPIS
                if(przyciski[5]){
                    if(!koniec){
                        stan_zapodcz = false;
                        zapodcz.zapis(ObsObj);
                        zapisano = 0;
                        wczytano = 200;
                    }
                }
                
                //ODCZYT
                if(przyciski[6]){
                    if(koniec){
                        aktualizacja_wynikow = true;
                        stan_zapodcz = false;
                        koniec       = false;
                        zapodcz.odczyt(ObsObj);
                        zapisano = 200;
                        wczytano = 0;
                    }
                }
                
                //OBSŁUGA OBIEKTÓW
                if(ObsObj.get_aktywne_obiekty().get(0).get_punkty_zdrowia() > 0){
                    ObsObj.aktualizacja_ilosci();
                    ObsObj.czyszczenie_obiektow();
                    ObsObj.ruch_obiektow();
                    ObsObj.kolizje();
                    ObsObj.zakonczenie_fali();
                }
                repaint();
            }
        }
    }
    
    Projekt(){
        super("PROJEKT");
        setBounds(50, 50, rozmiar_okna_x, rozmiar_okna_y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible  (true);
        createBufferStrategy(2);

        //Nadanie wartości zmiennym globalnym
        przyciski            = new boolean[7];
        stan_zapodcz         = true;
        zapisano             = 200;
        wczytano             = 200;
        zapodcz              = new ZapisOdczyt("Zapis.txt");
        wyniki_imiona        = new String[10];
        wyniki_punkty        = new int[10];
        wyniki_wp            = new String[10];
        wyniki_daty          = new String[10];
        wyniki               = new Wyniki("wyniki.txt");
        wyniki.odczyt(wyniki_imiona, wyniki_punkty, wyniki_wp, wyniki_daty);
        aktualizacja_wynikow = true;
        numer_gracza         = 20;
        koniec               = false;
        ObsObj               = new ObslugaObiektow(800, 600);
        ObsObj.fale_przeciwnikow();
        timer                = new Timer();
        timer.scheduleAtFixedRate(new Glowna_petla(), 0, 20);
        
        //STEROWANIE
        this.addKeyListener(new KeyListener(){

            @Override
            public void keyPressed(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:    {przyciski[0] = true; break;}
                    case KeyEvent.VK_DOWN:  {przyciski[1] = true; break;}
                    case KeyEvent.VK_LEFT:  {przyciski[2] = true; break;}
                    case KeyEvent.VK_RIGHT: {przyciski[3] = true; break;}
                    case KeyEvent.VK_Z:     {przyciski[4] = true; break;}  //atak
                    case KeyEvent.VK_S:     {przyciski[5] = true; break;}  //zapis
                    case KeyEvent.VK_L:     {przyciski[6] = true; break;}  //odczyt
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:    {przyciski[0] = false; break;}
                    case KeyEvent.VK_DOWN:  {przyciski[1] = false; break;}
                    case KeyEvent.VK_LEFT:  {przyciski[2] = false; break;}
                    case KeyEvent.VK_RIGHT: {przyciski[3] = false; break;}
                    case KeyEvent.VK_Z:     {przyciski[4] = false; break;}
                    case KeyEvent.VK_S:     {przyciski[5] = false; stan_zapodcz = true; break;}
                    case KeyEvent.VK_L:     {przyciski[6] = false; stan_zapodcz = true; break;}
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e){
            }
        }
        );
    }
    
    //START
    public static void main(String[] args)
    {
        wpisanie_nazwy();
        Projekt okno = new Projekt();
        okno.repaint();
    }
    
    //WPISANIE SWOJEJ NAZWY
    private static void wpisanie_nazwy(){
        nazwa_gracza = JOptionPane.showInputDialog("Wpisz swoje imię");
        if(nazwa_gracza == null){
            nazwa_gracza = "Anonymous";
        }
    }
    
    //WYSWIETLANIE OBRAZKÓW
    @Override
    public void paint(Graphics g)
    {
        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D)bstrategy.getDrawGraphics();
        g2d.drawImage(ObsObj.get_tlo(), 0, 0, null);
        if(ObsObj.get_aktywne_obiekty().get(0).get_punkty_zdrowia()>0){
            if(ObsObj.get_nr_fali()!=8)
                gra(g2d);
            else
                wygrana(g2d);
        }
        else
            przegrana(g2d);
        g2d.dispose();
        bstrategy.show();
    }
    
    //WYSWIETLANIE HUD'U PODCZAS GRY
    private void gra(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        ObsObj.rysowanie_obiektow(g2d);
        g2d.setColor(Color.blue);
        g2d.setFont(new Font("Arial", Font.BOLD, 15));
        g2d.drawString("FALA " + (ObsObj.get_nr_fali()+1), 6, 40);
        g2d.drawString("Punkty gracza: " + ObsObj.get_aktywne_obiekty().get(0).get_punkty(), 630, 580);
        //wyświetlenie "komunikatu" o zapisie
        if(zapisano<200){
            g2d.setColor(Color.green);
            g2d.setFont(new Font("Arial", Font.BOLD, 100));
            g2d.drawString(".", 775, 41);
            zapisano++;
        }
        //wyświetlenie "komunikatu" o odczycie
        if(wczytano<200){
            g2d.setColor(Color.yellow);
            g2d.setFont(new Font("Arial", Font.BOLD, 100));
            g2d.drawString(".", 775, 41);
            wczytano++;
        }
        g2d.setColor(Color.blue);
        g2d.setFont(new Font("Arial", Font.BOLD, 15));
    }
    
    //WYSWIETLANIE NAPISÓW PO PRZEGRANEJ
    private void przegrana(Graphics2D g2d){
        koniec = true;
        if(aktualizacja_wynikow)
            aktualizacja_listy_wynikow();
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));
        g2d.drawString("PRZEGRAŁEŚ", 200, 100);
        g2d.setFont(new Font("Serif", Font.PLAIN, 50));
        g2d.drawString("へ　へ", 600, 70);
        g2d.drawString("の　の", 600, 100);
        g2d.drawString("　も", 590, 130);
        g2d.drawString("　へ", 600, 180);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Wczytać poprzednio zapisany stan gry? (l)", 200, 570);
        tablica(g2d, Color.RED);
    }
    
    //WYSWIETLANIE NAPISÓW PO WYGRANEJ
    private void wygrana(Graphics2D g2d){
        koniec = true;
        if(aktualizacja_wynikow)
            aktualizacja_listy_wynikow();
        g2d.drawImage(ObsObj.get_tlo(), 0, 0, null);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));
        g2d.drawString("WYGRAŁEŚ", 200, 100);
        g2d.setFont(new Font("Serif", Font.PLAIN, 50));
        g2d.drawString("へ　へ", 600, 70);
        g2d.drawString("め　め", 600, 100);
        g2d.drawString("　も", 590, 130);
        g2d.drawString("　ひ", 600, 180);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Wczytać poprzednio zapisany stan gry? (l)", 200, 570);
        tablica(g2d, Color.BLUE);
    }
    
    //SPRAWDZENIE CZY GRACZ NIE DOSTAŁ SIĘ NA LISTĘ
    private void aktualizacja_listy_wynikow(){
        //Sprawdzenie czy gracz uzyskał wynik kwalifikujący się na listę najlepszych wyników
        //oraz odpowiednie przekształcenie tablic
        numer_gracza = 20;
        for(int i=0; i<10; i++){
            if(ObsObj.get_gracz().get_punkty() > wyniki_punkty[i]){
                numer_gracza = i;
                for(int j=9; j>i; j--){
                    wyniki_imiona[j] = wyniki_imiona[j-1];
                    wyniki_punkty[j] = wyniki_punkty[j-1];
                }
                if (nazwa_gracza == null) //jeśli podczas wpisywania imienia gracz kliknął "cancel" lub krzyżyk
                    wyniki_imiona[i] = "Anonymous";
                else
                    wyniki_imiona[i] = nazwa_gracza;
                wyniki_punkty[i] = ObsObj.get_gracz().get_punkty();
                if(ObsObj.get_nr_fali()>=8)
                    wyniki_wp[i] = "W";
                else 
                    wyniki_wp[i] = "P";
                wyniki_daty[i] = DateTimeFormatter.ofPattern("yyy/MM/dd").format(LocalDate.now());
                wyniki.zapis(wyniki_imiona, wyniki_punkty, wyniki_wp, wyniki_daty);
                break;
            }
        }
        aktualizacja_wynikow = false;
    }
    
    //WYŚWIETLENIE TABLICY NAJLEPSZYCH WYNIKÓW
    private void tablica(Graphics2D g2d, Color kolor){
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Tablica najlepszych wyników", 100, 150);
        g2d.drawString("Imię", 100, 200);
        g2d.drawString("Wynik", 380, 200);
        g2d.drawString("P/W", 535, 200);
        g2d.drawString("Data", 610, 200);
        for(int i=0; i<10; i++){
            if(i == numer_gracza)
                g2d.setColor(Color.green);
            else
                g2d.setColor(kolor);
            g2d.drawString("" + (i + 1) + ".", 55, 250+i*30);
            if(wyniki_imiona[i] == null){
                g2d.drawString("_____", 100, 250+i*30);
                g2d.drawString("0", 380, 250+i*30);
                g2d.drawString("_", 535, 250+i*30);
                g2d.drawString("0000/00/00", 610, 250+i*30);
            }
            else{
                g2d.drawString(wyniki_imiona[i]     , 100, 250+i*30);
                g2d.drawString("" + wyniki_punkty[i], 380, 250+i*30);
                g2d.drawString(wyniki_wp[i]         , 535, 250+i*30);
                g2d.drawString(wyniki_daty[i]       , 610, 250+i*30);
            }
        }
    }
    
}
