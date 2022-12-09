package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    int velikost=7;
    int velikost2=7;
    boolean prviKlik=true;
    Color barva=null;
    Color barva2;
    int stPotez=0;
    JFrame polje = new JFrame("Hidden words");

    JLabel spot = new JLabel(String.valueOf(stPotez));
    JLabel spot1 = new JLabel("Število potez:");



    JButton[][] kartice; //= new JButton[velikost][velikost];
    JFrame podatki = new JFrame("Menu");
    JTextField barvst= new JTextField();
    JButton okbarve = new JButton("Začni!");
    JLabel oznaka = new JLabel("Vnesi število barv (med 3 in 6)");
    JLabel oznaka2= new JLabel("Vnesi velikost polja od 4 do 50 (npr 5  7)");
    JTextField velPolja = new JTextField();
    JTextField velPolja2 = new JTextField();
    JFrame stevPotez= new JFrame();
    JLabel napaka = new JLabel();


    Random rdn = new Random();

    public Main () {

        polje.setVisible(false);
        polje.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        stevPotez.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        oznaka2.setSize(300, 20);
        oznaka2.setLocation(170, 130);
        velPolja.setSize(150, 20);
        velPolja.setLocation(300, 160);
        velPolja2.setLocation(100,160);
        velPolja2.setSize(150,20);

        napaka.setSize(200,20);
        napaka.setLocation(230,260);


        //okno ki steje stevilo potez
        stevPotez.setLayout(null);
        stevPotez.setSize(250, 150);
        stevPotez.add(spot);
        spot.setLocation(110, 80);
        spot.setSize(30, 20);
        spot1.setSize(150, 20);
        spot1.setLocation(50, 40);
        stevPotez.add(spot1);
        //vnosno okno za stevilo barv
        barvst.setLocation(225, 60);
        barvst.setSize(150, 20);

        oznaka.setSize(200, 20);
        oznaka.setLocation(200, 30);

        //okno za izbiro tezavnosti pred igro
        podatki.setVisible(true);
        podatki.setSize(600, 400);
        podatki.add(okbarve);
        podatki.add(barvst);
        podatki.add(oznaka);
        podatki.setLayout(null);
        podatki.add(oznaka2);
        podatki.add(velPolja);
        podatki.add(velPolja2);
        podatki.setResizable(false);
        podatki.add(napaka);
        podatki.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //gumb za zacetek igre
        okbarve.setSize(70, 30);
        okbarve.setLocation(265, 320);
        okbarve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JButton[][] kartice = new JButton[7][7];

                //JFrame polje = new JFrame();
                ArrayList<Color> barve = new ArrayList<>();
                //avtomaticno so karte v treh barvah
                barve.add(Color.pink);
                barve.add(Color.blue);
                barve.add(Color.yellow);




                int stBarv=Integer.parseInt(barvst.getText());

                velikost=Integer.parseInt(velPolja.getText());
                velikost2=Integer.parseInt(velPolja2.getText());

                if (velikost>50 || velikost2>50 || stBarv>6 ){
                    napaka.setText("Napaka, preveri števila");
                }else {
                    podatki.setVisible(false);

                    polje.setVisible(true);
                    stevPotez.setVisible(true);
                    kartice=new JButton[velikost][velikost2];
                    //ce igralec izbere vec kot 3 barve se dodajo nove
                    if (stBarv==4){
                        barve.add(Color.ORANGE);
                    }else if (stBarv==5){
                        barve.add(Color.ORANGE);
                        barve.add(Color.cyan);
                    }else if (stBarv==6){
                        barve.add(Color.ORANGE);
                        barve.add(Color.cyan);
                        barve.add(Color.green);
                    }
                    polje.setLayout(new GridLayout(velikost, velikost2));

                    polje.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    polje.setSize(600, 600);
                    polje.setResizable(false);

                    for (int i = 0; i < velikost; i++) {
                        for (int j = 0; j < velikost2; j++) {
                            Color randomBarva = barve.get(rdn.nextInt(barve.size()));
                            kartice[i][j] = new JButton();
                            kartice[i][j].setOpaque(true);
                            kartice[i][j].setBorder(BorderFactory.createLineBorder(Color.gray, 2));
                            //kartice[i][j].setLocation();
                            kartice[i][j].setBackground(randomBarva);
                            //newListener listener= new newListener();
                            kartice[i][j].addActionListener(new newListener());
                            //kartice[i][j].setSize(50,50);
                            polje.add(kartice[i][j]);
                        }
                    }

                }
            }}
        );
    }
    public static void main(String[] args) {

        Main i = new Main();

        //shranjevanje igre
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/home/igra.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(i);
            out.close();
            fileOut.close();
            System.out.printf("Trenutna igra je shranjena v /home/igra.ser");
        } catch (IOException ii) {
            //ii.printStackTrace();
        }
    }

    //poslusalec za karte
    public class newListener implements ActionListener {
        public newListener() {
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < velikost; i++) {
                for (int j = 0; j < velikost2; j++) {
                    if (e.getSource() == kartice[i][j]) {
                        //ob prvem kliku shrani barvo kliknjene karte
                        if (prviKlik) {
                            barva = kartice[i][j].getBackground();
                            kartice[i][j].setBackground(barva);
                            prviKlik = false;
                        }
                        //ob drugem kliku pa z izbrano barvo pobarva tisto karto ter njene sosede
                        else {
                            barva2 = kartice[i][j].getBackground();
                            kartice[i][j].setBackground(barva);
                            if ((i > 0) && kartice[i - 1][j].getBackground() == barva2) {
                                kartice[i - 1][j].setBackground(barva);
                            }
                            if ((j > 0) && kartice[i][j - 1].getBackground() == barva2) {
                                kartice[i][j - 1].setBackground(barva);
                            }
                            if ((j < velikost2) && kartice[i][j + 1].getBackground() == barva2) {
                                kartice[i][j + 1].setBackground(barva);
                            }
                            if ((i < velikost) && kartice[i + 1][j].getBackground() == barva2) {
                                kartice[i + 1][j].setBackground(barva);
                            }
                            stPotez++;
                            spot.setText(String.valueOf(stPotez));
                            prviKlik = true;

                            //preverja ali so ze vse enakih barv, ce ja zapre igralno okno
                            Color prvaKarta = kartice[0][0].getBackground();
                            int steviloEnakih = 1;
                            for (int k = 0; k < velikost; k++) {
                                for (int m = 0; m < velikost2; m++) {
                                    Color enaKarta = kartice[k][m].getBackground();
                                    if (enaKarta == prvaKarta) {
                                        steviloEnakih++;
                                    }
                                }
                                if (steviloEnakih == (velikost * velikost2) + 1) {
                                    polje.setVisible(false);
                                    spot1.setText("Konec! Število potez:");
                                }
                            }
                        }
                    }
                }
            }
        }}
}
