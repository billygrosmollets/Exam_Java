import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cellule extends JPanel {
    private int etatActuel;   // 1 pour active (noire), 0 pour inactive (blanche)
    private int etatSuivant;  // Utilisé pour stocker l'état suivant temporairement
    private Cellule[][] grille;
    private int x, y;

    // Constructeur
    public Cellule(Cellule[][] grille, int x, int y, int etatInitial) {
        this.grille = grille;
        this.x = x;
        this.y = y;
        this.etatActuel = etatInitial;
        this.setBackground(etatActuel == 1 ? Color.BLACK : Color.WHITE);
        this.setMinimumSize(new Dimension(3, 3));

        // Ajouter un MouseListener pour détecter les clics sur la cellule
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleEtat();  // Change l'état de la cellule lorsqu'elle est cliquée
            }
        });
    }

    // Bascule entre l'état actif et inactif lors d'un clic
    private void toggleEtat() {
        etatActuel = (etatActuel == 1) ? 0 : 1;
        this.setBackground(etatActuel == 1 ? Color.BLACK : Color.WHITE);
        this.repaint();
    }

    // Retourne l'état actuel de la cellule
    public int getEtatActuel() {
        return etatActuel;
    }

    // Compte le nombre de cellules voisines actives
    public int nbCellulesVoisinesActives() {
        int voisinsActifs = 0;

        // Parcourir les voisins dans les 8 directions (diagonales incluses)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;  // Ignorer la cellule elle-même
                try {
                    // Vérifier si les voisins sont actifs
                    if (grille[x + i][y + j].getEtatActuel() == 1) {
                        voisinsActifs++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Ignore les voisins hors de la grille
                }
            }
        }
        return voisinsActifs;
    }

    // Calcule l'état suivant de la cellule en fonction des règles du jeu
    public void calculeEtatSuivant() {
        int voisinsActifs = nbCellulesVoisinesActives();

        if (etatActuel == 1) {
            // Si la cellule est vivante
            if (voisinsActifs < 2 || voisinsActifs > 3) {
                etatSuivant = 0;  // Meurt
            } else {
                etatSuivant = 1;  // Reste vivante
            }
        } else {
            // Si la cellule est morte
            if (voisinsActifs == 3) {
                etatSuivant = 1;  // Revient à la vie
            } else {
                etatSuivant = 0;  // Reste morte
            }
        }
    }

    // Met à jour l'état actuel avec l'état suivant calculé
    public void metAJourEtatActuel() {
        etatActuel = etatSuivant;
        this.setBackground(etatActuel == 1 ? Color.BLACK : Color.WHITE);
        this.repaint();
    }
}
