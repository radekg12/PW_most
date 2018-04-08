import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

class Producent implements Runnable {
    private final static int[] MIN_T = {800, 1800};
    private final static int[] MAX_T = {2000, 3500};
    final ConcurrentLinkedQueue<Samochod> samochody = new ConcurrentLinkedQueue<>();
    private final Most most;
    private final Droga drogaL;
    private final Droga drogaP;
    private final JFrame ramka;
    private final Random czas = new Random();
    private final Random random = new Random();
    private int[] t = {1000, 1000};

    public Producent(Most most, Droga drogaL, Droga drogaP, JFrame ramka) {
        this.most = most;
        this.drogaL = drogaL;
        this.drogaP = drogaP;
        this.ramka = ramka;
    }

    public void setT(int x, int tx) {
        if (x == 0)
            if (tx < MIN_T[x] || tx > MAX_T[x])
                throw new IllegalArgumentException();
            else this.t[x] = tx;
        else {
            if (tx <= t[0] || (tx < MIN_T[x] || tx > MAX_T[x]))
                throw new IllegalArgumentException();
            else this.t[x] = tx - t[0];
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(t[0] + czas.nextInt(t[1]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int x = random.nextInt(4);
            if (x != 3 && most.getCzekaLewa() < 7) {
                Samochod s1 = new NadjezdzajacyLewo(samochody, most, drogaL.getX(), drogaL.getY() + 3 * drogaL.getSzerokosc() / 4 - Samochod.wysokosc / 2, ramka);
                samochody.add(s1);
                most.samochodyL.add((NadjezdzajacyLewo) s1);
            }
            if (x == 3 && most.getCzekaPrawa() < 7) {
                Samochod s2 = new NadjezdzajacyPrawo(samochody, most, drogaP.getX() + drogaP.getDlugosc() - Samochod.szerokosc, drogaP.getY() + drogaP.getSzerokosc() / 4 - Samochod.wysokosc / 2, ramka);
                samochody.add(s2);
                most.samochodyP.add((NadjezdzajacyPrawo) s2);
            }
            ramka.repaint();
        }
    }
}
