import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NadjezdzajacyLewo extends Samochod implements Runnable {

    public NadjezdzajacyLewo(ConcurrentLinkedQueue<Samochod> samochody, Most most, int x, int y, JFrame ramka) {
        super(samochody, most, x, y, ramka);
        ts = 8;
        //ts=4+random.nextInt(4);
        try {
            iconCar = ImageIO.read(new File("car1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }


    public void run() {
//        System.out.println("L_StartRun");
        jazda();
        most.wjedzL(zglosilCzekanie);
        przejedzMost();
        most.zjedzL();
        zjazd();
        most.samochodyL.remove(this);
        samochody.remove(this);
        ramka.repaint();
        // }
    }

    public void jazda() {
//        System.out.println("L_StartJazda");
        for (int i = 0; i < (Droga.dlugosc - szerokosc) / predkosc; i++) {

            int a = most.samochodyL.indexOf(this);
            if (a != 0) {
                try {
                    while (this.getX() < most.getX() && most.samochodyL.get(a - 1).getX() - this.getX() < 2 * szerokosc) {
                        if (!zglosilCzekanie) {
                            most.zatrzymajL(this);
                            zglosilCzekanie = true;
                        }
                        a = most.samochodyL.indexOf(this);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("\t WyjatekL");
                }
            }

            x += predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void przejedzMost() {
        ts -= 2;
//        System.out.println("L_StartSkos");

        for (int i = 0; i < Most.szerokosc / (2 * predkosc); i++) {
            x += predkosc;
            y -= predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("L_StartJazdaMost");
        for (int i = 0; i < (Most.dlugosc - Most.szerokosc / 2) / predkosc; i++) {
            x += predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("L_StartSkos2");
        for (int i = 0; i < Most.szerokosc / (2 * predkosc); i++) {
            x += predkosc;
            y += predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void zjazd() {
        ts -= 2;
        for (int i = 0; i < (Droga.dlugosc - szerokosc) / predkosc; i++) {
            x += predkosc;
            ramka.repaint();
            try {
                Thread.sleep(ts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
