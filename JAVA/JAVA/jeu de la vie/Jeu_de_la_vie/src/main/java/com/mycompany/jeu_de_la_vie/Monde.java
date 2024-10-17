package com.mycompany.jeu_de_la_vie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Monde extends JFrame {
    Cellule[][] grille;
    int nbRow;
    int nbCol;
    double densite;

    public Monde(int nbRow, int nbCol, double densite) {
        this.nbRow = nbRow;
        this.nbCol = nbCol;
        this.densite = densite;
        this.grille = new Cellule[nbRow][nbCol];

        initFrame();
    }

    public void initFrame() {
        setTitle("Jeu de la vie");
        
        initGrille(this.densite); // Initialiser la grille avant de l'ajouter au frame
        setLayout(new GridLayout(nbRow, nbCol));
        
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                add(grille[i][j]); // Ajouter les cellules après leur initialisation
            }
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void initGrille(double densite) {
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                if (Math.random() < densite) {
                    grille[i][j] = new Cellule(grille, i, j, 1);
                } else {
                    grille[i][j] = new Cellule(grille, i, j, 0);
                }
            }
        }
    }

    public void animeGrille() {
        // Calculer l'état suivant de toutes les cellules
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j].calculeEtatSuivant();
            }
        }
        // Mettre à jour l'état actuel de toutes les cellules
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j].metAJourEtatActuel();
            }
        }
    }

    class Cellule extends JPanel {
        int etatActuel;
        int etatSuivant;
        int x;
        int y;

        public Cellule(Cellule[][] grille, int x, int y, int etatInitial) {
            this.x = x;
            this.y = y;
            this.etatActuel = etatInitial;
            this.etatSuivant = etatInitial;

            setMinimumSize(new Dimension(3, 3));
            update_color();
        }

        public int nbCellulesVoisinesActives() {
            int nbCellulesVoisines = 0;
            for (int i = this.x - 1; i <= this.x + 1; i++) {
                for (int j = this.y - 1; j <= this.y + 1; j++) {
                    if (i >= 0 && i < nbRow && j >= 0 && j < nbCol) { // Vérification des limites
                        if ((i != this.x || j != this.y) && grille[i][j].etatActuel == 1) {
                            nbCellulesVoisines++;
                        }
                    }
                }
            }
            return nbCellulesVoisines;
        }

        public void calculeEtatSuivant() {
            int nbCellulesVoisinesActives = nbCellulesVoisinesActives();
            if (nbCellulesVoisinesActives <= 2 || nbCellulesVoisinesActives >= 4) {
                etatSuivant = 0;
            } else if (nbCellulesVoisinesActives == 3) {
                etatSuivant = 1;
            }
        }

        public void metAJourEtatActuel() {
            this.etatActuel = this.etatSuivant;
            update_color(); // Mettre à jour la couleur après modification de l'état
        }

        private void update_color() {
            if (this.etatActuel == 1) {
                setBackground(Color.green);
            } else {
                setBackground(Color.gray);
            }
        }
    }

    public static void main(String[] args) {
        Monde test = new Monde(20, 20, 0.3);
    }
}
