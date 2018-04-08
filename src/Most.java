import java.util.ArrayList;

class Most {
    static int dlugosc = 250;
    static int szerokosc;
    final ArrayList<NadjezdzajacyLewo> samochodyL = new ArrayList<>();
    final ArrayList<NadjezdzajacyPrawo> samochodyP = new ArrayList<>();
    private final Object sL = new Object();
    private final Object sP = new Object();
    private volatile int czekaLewa = 0, czekaPrawa = 0, jedzieLewa = 0, jedziePrawa = 0;
    private int x, y;

    public Most(int x, int y, int szerokosc) {
        this.x = x;
        this.y = y;
        Most.szerokosc = szerokosc;
    }

    public void wjedzL(boolean zglosilCzekanie) {
        synchronized (sL) {
            if ((jedziePrawa + czekaPrawa) > 0 && jedzieLewa == 0) {
                if (!zglosilCzekanie) {
                    czekaLewa++;
                    zglosilCzekanie = true;
                }
                try {
                    sL.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (zglosilCzekanie) {
                    czekaLewa--;
                    zglosilCzekanie = false;
                }
            }
            if (zglosilCzekanie) czekaLewa--;
            jedzieLewa++;
            sL.notifyAll();
        }

    }

    public void zjedzL() {
        synchronized (sP) {
            jedzieLewa--;
            if (jedzieLewa == 0) {
                sP.notify();
            }
        }
    }

    public void wjedzP(boolean zglosilCzekanie) {
        synchronized (sP) {
            if (jedzieLewa + jedziePrawa > 0) {
                if (!zglosilCzekanie) czekaPrawa++;
                try {
                    sP.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                czekaPrawa--;
            }
            jedziePrawa = 1;
            sP.notifyAll();
        }
    }

    public void zjedzP() {
        synchronized (sL) {
            synchronized (sP) {
                jedziePrawa = 0;
                if (czekaLewa > 0) {
                    sL.notifyAll();
                } else {
                    sP.notify();
                }
            }
        }
    }


    public void zatrzymajL(Samochod s) {
        synchronized (sL) {
            czekaLewa++;
            s.zglosilCzekanie = true;
            ;
            try {
                sL.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void zatrzymajP(Samochod p) {
        synchronized (sP) {
            czekaPrawa++;
            p.zglosilCzekanie = true;
            try {
                sP.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getCzekaLewa() {
        return czekaLewa;
    }

    public int getCzekaPrawa() {
        return czekaPrawa;
    }

    public int getJedzieLewa() {
        return jedzieLewa;
    }

    public int getJedziePrawa() {
        return jedziePrawa;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

}
