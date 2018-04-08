import java.awt.*;

class Droga {
    static final int dlugosc = 450;
    private static final int szerokosc = 150;
    private Color kolor = Color.black;
    private int x, y;

    public Droga(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Color getKolor() {
        return kolor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getDlugosc() {
        return dlugosc;
    }

}
