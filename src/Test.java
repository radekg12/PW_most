import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Test extends JFrame implements ActionListener {
    private static Timer timer;
    private final JFrame ramka = new JFrame("Most");
    private final Droga drogaL;
    private final Droga drogaP;
    private final Most most;
    private boolean m = false;
    private Producent producent;
    private Polygon poly1;
    private Polygon poly2;
    private BufferedImage car_logo;
    private BufferedImage znak0, znak1, znak2, cable;

    private Test() {
        timer = new Timer(400, this);
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ramka.setSize(1300, 700);
        Panel panel = new Panel();
        ramka.add(panel);
        try {
            car_logo = ImageIO.read(new File("logo.png"));
            znak0 = ImageIO.read(new File("a12a.png"));
            znak1 = ImageIO.read(new File("d5.png"));
            znak2 = ImageIO.read(new File("b31.png"));
            cable = ImageIO.read(new File("cable2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ramka.setIconImage(car_logo);
        //panel.add(new JButton("Start ?"));
        //panel.add(new TextField(4));
        drogaL = new Droga(50, 150);
        most = new Most(drogaL.getX() + drogaL.getDlugosc(), drogaL.getY() + drogaL.getSzerokosc() / 4, drogaL.getSzerokosc() / 2);
        drogaP = new Droga(most.getX() + most.getDlugosc(), drogaL.getY());
        //ramka.setResizable(false);
        pack();
        ramka.setVisible(true);
        init();
    }

    public static void main(String[] args) {

        Test test = new Test();

    }


    private void init() {

        int[] xPoly1 = {most.getX(), most.getX(), most.getX() + most.getSzerokosc() / 2, most.getX() + most.getSzerokosc() / 2};
        int[] yPoly1 = {drogaL.getY() + drogaL.getSzerokosc(), drogaL.getY(), most.getY(), most.getY() + most.getSzerokosc()};
        poly1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
        int[] xPoly2 = {drogaP.getX() - most.getSzerokosc() / 2, drogaP.getX() - most.getSzerokosc() / 2, drogaP.getX(), drogaP.getX()};
        int[] yPoly2 = {most.getY() + most.getSzerokosc(), most.getY(), drogaP.getY(), drogaP.getY() + drogaP.getSzerokosc()};
        poly2 = new Polygon(xPoly2, yPoly2, xPoly2.length);
        producent = new Producent(most, drogaL, drogaP, ramka);
        wczytaj();
        new Thread(producent).start();
        //pracuj();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        m = !m;
        repaint();
    }

    public void wczytaj() {
        String warn = "Wprowadziłeś złą wartość! ";
        String[] kom = {"Podaj mninimalny czas generowania z przedziału", "Podaj maksymalny czas generowania z przedziału"};
        String[] war2 = {" (800; 2000)[ms]: ", " (1800; 3500)[ms]: "};
        boolean poprawne = false;
        int ile = 0;
        for (int i = 0; i < 2; i++) {
            while (!poprawne) {
                try {
                    int liczba;
                    if (ile == 0)
                        liczba = Integer.parseInt(JOptionPane.showInputDialog(kom[i] + war2[i]));
                    else
                        liczba = Integer.parseInt(JOptionPane.showInputDialog(warn + kom[i] + war2[i]));
                    producent.setT(i, liczba);
                    poprawne = true;
                } catch (IllegalArgumentException e) {
                    ile++;
                }
            }
            ile = 0;
            poprawne = false;
        }
    }

    public class Panel extends JComponent {
        public Panel() {
            //setBackground(Color.BLACK);
        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.setColor(Color.CYAN);
            g.fillRect(most.getX(), drogaL.getY(), most.getDlugosc(), drogaL.getSzerokosc());
            g.setColor(drogaL.getKolor());
            g.fillRect(drogaL.getX(), drogaL.getY(), drogaL.getDlugosc(), drogaL.getSzerokosc());
            g.fillRect(drogaP.getX(), drogaP.getY(), drogaP.getDlugosc(), drogaP.getSzerokosc());
            g.fillRect(most.getX(), most.getY(), most.getDlugosc(), most.getSzerokosc());
            g.fillPolygon(poly1);
            g.fillPolygon(poly2);
            g.setColor(Color.white);
            g.fillRect(drogaL.getX(), drogaL.getY() + drogaL.getSzerokosc() / 2 - 2, drogaL.getDlugosc(), 4);
            g.fillRect(drogaP.getX(), drogaP.getY() + drogaP.getSzerokosc() / 2 - 2, drogaP.getDlugosc(), 4);

            for (Samochod s : producent.samochody) {
//                g.setColor(s.getKolor());
//                g.fillOval(s.getX(), s.getY(), Samochod.getSzerokosc(), Samochod.getWysokosc());
                g.drawImage(s.getIconCar(), s.getX(), s.getY(), null);
            }

            g.setColor(Color.gray);
            g.fillRect(most.getX() - Most.dlugosc / 4 + 14, -40 + most.getY() + Most.szerokosc + 30, 6, 80);
            g.drawImage(znak0, most.getX() - Most.dlugosc / 4, -40 + most.getY() + Most.szerokosc, null);
            g.drawImage(znak1, most.getX() - Most.dlugosc / 4, -40 + most.getY() + Most.szerokosc + 45, null);
            g.fillRect(most.getX() + 5 * Most.dlugosc / 4 + 14, most.getY() - 2 * Most.szerokosc + 30, 6, 80);
            g.drawImage(znak0, most.getX() + 5 * Most.dlugosc / 4, most.getY() - 2 * Most.szerokosc, null);
            g.drawImage(znak2, most.getX() + 5 * Most.dlugosc / 4, most.getY() - 2 * Most.szerokosc + 45, null);

            g.setColor(Color.gray.darker());
            g.fillRoundRect(60, 390, 200, 150, 10, 10);
            g.fillRoundRect(60, 555, 200, 40, 10, 10);
            g.setColor(Color.gray);
            g.fillRoundRect(50, 400, 200, 150, 10, 10);
            g.fillRoundRect(50, 565, 200, 40, 10, 10);
            g.setColor(Color.black);
            g.fillRoundRect(65, 415, 170, 120, 10, 10);
            g.setColor(Color.lightGray);
            g.fillRect(65, 575, 15, 7);
            g.fillRect(100, 590, 100, 5);

            g.setColor(Color.green.darker());
            g.setFont(new Font("Retro Computer", Font.PLAIN, 15));
            g.drawString("cL = " + most.getCzekaLewa() + ",      jL = " + most.getJedzieLewa() + ";", 70, 450);
            g.drawString("cP = " + most.getCzekaPrawa() + ",     jP = " + most.getJedziePrawa(), 70, 490);
            if (m) g.drawString(" |", 70, 515);
            g.drawImage(cable, 0, 570, null);
        }
    }


}
