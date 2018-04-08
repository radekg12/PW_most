import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Samochod {
    static final int szerokosc = 30;
    static final int wysokosc = 30;
    static Most most;
    final int predkosc = 1;
    final JFrame ramka;
    final ConcurrentLinkedQueue<Samochod> samochody;
    volatile boolean zglosilCzekanie = false;
    BufferedImage iconCar;
    volatile int x;
    int y;
    int ts;

    Samochod(ConcurrentLinkedQueue<Samochod> samochody, Most most, int x, int y, JFrame ramka) {
        this.samochody = samochody;
        this.x = x;
        this.y = y;
        Samochod.most = most;
        this.ramka = ramka;
    }

    abstract public void jazda();

    abstract public void przejedzMost();

    abstract public void zjazd();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getIconCar() {
        return iconCar;
    }

}
