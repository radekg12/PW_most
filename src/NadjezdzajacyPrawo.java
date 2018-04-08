import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NadjezdzajacyPrawo extends Samochod implements Runnable {

    public NadjezdzajacyPrawo(ConcurrentLinkedQueue<Samochod> samochody, Most most, int x, int y, JFrame ramka) {
        super(samochody, most, x, y, ramka);
        ts = 7;
        try {
            iconCar = ImageIO.read(new File("car2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void run() {
        jazda();
        most.wjedzP(zglosilCzekanie);
        przejedzMost();
        most.zjedzP();
        zjazd();
        most.samochodyP.remove(this);
        samochody.remove(this);
        ramka.repaint();
    }

    public void jazda() {
//        System.out.println("\tStart_jazda");
        for (int i = 0; i < (Droga.dlugosc - szerokosc) / predkosc; i++) {

            int a = most.samochodyP.indexOf(this);
            if (a != 0) {
                try {
                    while (this.getX() > most.getX() + most.getDlugosc() && this.getX() - most.samochodyP.get(a - 1).getX() < 2 * szerokosc) {
                        if (!zglosilCzekanie) {
                            most.zatrzymajP(this);
                            zglosilCzekanie = true;
                        }
                        a = most.samochodyP.indexOf(this);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("\t WyjatekP");
                }
            }
            x -= predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("\tStop_jazda");
    }

    public void przejedzMost() {
//        System.out.println("\tStart_skos");

        for (int i = 0; i < Most.szerokosc / (2 * predkosc); i++) {
            x -= predkosc;
            y += predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("\tStart_most");
        for (int i = 0; i < (Most.dlugosc - Most.szerokosc / 2) / predkosc; i++) {
            x -= predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("\tStart_skos");
        for (int i = 0; i < Most.szerokosc / (2 * predkosc); i++) {
            x -= predkosc;
            y -= predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("\tZjazd_most");
    }

    public void zjazd() {
        ts -= 2;
        for (int i = 0; i < (Droga.dlugosc - szerokosc) / predkosc; i++) {
            x -= predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
