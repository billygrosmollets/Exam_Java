package labyrinthe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

import static labyrinthe.LabyrintheGenerator.getLabyrintheAsStringArray;

public class PanelLabyrinthe extends JPanel implements KeyListener {

    private final int ligne;
    private final int colonne;
    private CaseLabyrinthe[][] Labyrinthe;
    private String[][] lab;
    private boolean premier;
    private Timer timer; // Timer pour l'évolution automatique
    private int exitX; // Coordonnée X de la sortie
    private int exitY; // Coordonnée Y de la sortie

    // Nouveau constructeur pour l'évolution automatique
    public PanelLabyrinthe(int ligne, int colonne, int pasMilliS) {
        this(ligne, colonne);  // Appeler le constructeur existant
        findExit(); // Localiser la case de sortie

        // Configuration du timer pour l'évolution automatique
        timer = new Timer(pasMilliS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer();  // Appeler la méthode movePlayer pour évoluer l'état
                checkExit();   // Vérifier si la case de sortie est OCCUPE
            }
        });
        timer.start();  // Démarrer le timer
    }

    public PanelLabyrinthe(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.premier = false;
        Labyrinthe = new CaseLabyrinthe[ligne][colonne];

        this.setLayout(new GridLayout(ligne, colonne));

        lab = getLabyrintheAsStringArray(ligne, colonne);
        
        // Localiser la case de sortie
        findExit(); // Appeler la méthode pour trouver la sortie

        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                Labyrinthe[i][j] = new CaseLabyrinthe(i, j);

                if (!premier) {
                    if (lab[i][j].equals("#")) {
                        Labyrinthe[i][j].setter("INTERDIT");
                        Labyrinthe[i][j].nextstate = "LIBRE";
                    } else if (lab[i][j].equals(".")) {
                        Labyrinthe[i][j].setter("OCCUPE");
                        Labyrinthe[i][j].nextstate = "LIBRE";
                        premier = true;
                    } else if (lab[i][j].equals("+")) {
                        Labyrinthe[i][j].state = "LIBRE";
                        Labyrinthe[i][j].nextstate = "LIBRE";
                        Labyrinthe[i][j].setBackground(Color.red);
                    }
                } else {
                    if (lab[i][j].equals("#")) {
                        Labyrinthe[i][j].setter("INTERDIT");
                        Labyrinthe[i][j].nextstate = "LIBRE";
                    }
                    if (lab[i][j].equals(".")) {
                        Labyrinthe[i][j].setter("LIBRE");
                        Labyrinthe[i][j].nextstate = "LIBRE";
                    } else if (lab[i][j].equals("+")) {
                        Labyrinthe[i][j].state = "LIBRE";
                        Labyrinthe[i][j].nextstate = "LIBRE";
                        Labyrinthe[i][j].setBackground(Color.red);
                    }
                }
                add(Labyrinthe[i][j]);
            }
        }

        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                if (Labyrinthe[i][j] != null) {  // Vérification si la case n'est pas null
                    Labyrinthe[i][j].updateNextState();
                }
            }
        }

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    // Méthode pour localiser la case de sortie
    private void findExit() {
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                if (lab[i][j].equals("+")) { // Si on trouve la sortie
                    exitX = i; // Récupérer la coordonnée X de la sortie
                    exitY = j; // Récupérer la coordonnée Y de la sortie
                    return; // Sortir de la méthode
                }
            }
        }
    }

    private void movePlayer() {
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                Labyrinthe[i][j].updateNextState();
            }
        }
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                Labyrinthe[i][j].updateState();
            }
        }
    }

    // Méthode pour vérifier si la sortie est OCCUPE
    private void checkExit() {
        // Vérifiez si la case de sortie est OCCUPE
        if (Labyrinthe[exitX][exitY].state.equals("OCCUPE")) {
            timer.stop(); // Arrêter l'évolution
            tracePath(exitX, exitY); // Tracer le chemin vers la sortie
        }
    }

    // Méthode pour tracer le chemin de l'entrée à la sortie
    private void tracePath(int exitX, int exitY) {
        // Pour l'exemple, nous allons remplir le chemin jusqu'à la sortie
        // Vous pouvez améliorer ceci avec des algorithmes de recherche de chemin (ex : DFS, BFS)
        
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                // Si la cellule fait partie du chemin vers la sortie, la colorer en rouge
                if (Labyrinthe[i][j].state.equals("LIBRE")) {
                    Labyrinthe[i][j].setter("EXPLOREE"); // État de chemin exploré
                    Labyrinthe[i][j].setBackground(Color.red);
                }
            }
        }

        // Optionnel : afficher la longueur du chemin
        System.out.println("Longueur du chemin vers la sortie : " + calculatePathLength());
    }

    // Méthode pour calculer la longueur du chemin vers la sortie
    private int calculatePathLength() {
        int pathLength = 0;

        // Compter combien de cases sont sur le chemin vers la sortie
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                if (Labyrinthe[i][j].state.equals("EXPLOREE")) {
                    pathLength++;
                }
            }
        }
        return pathLength;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        movePlayer();
        System.out.println("Touche pressée : " + e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public class CaseLabyrinthe extends JPanel {
        int x, y;
        String state;
        String nextstate;

        public CaseLabyrinthe(int x, int y) {
            this.x = x;
            this.y = y;

            setPreferredSize(new Dimension(10, 10));
        }

        public void setter(String state) {
            this.state = state;

            switch (state) {
                case "INTERDIT":
                    this.setBackground(Color.black);
                    return;
                case "OCCUPE":
                    this.setBackground(Color.blue);
                    return;
                case "LIBRE":
                    this.setBackground(Color.white);
                    return;
                case "EXPLOREE":
                    this.setBackground(Color.red); // Couleur rouge pour l'état exploré
                    return;
            }
        }

        public String getter() {
            return this.state;
        }

        public void updateNextState() {
            if (Labyrinthe[this.x][this.y] != null && Labyrinthe[this.x][this.y].state != null) {
                if (Labyrinthe[this.x][this.y].state.equals("OCCUPE")) {
                    return;
                }
                if (!Labyrinthe[x][y].state.equals("LIBRE")) {
                    Labyrinthe[this.x][this.y].nextstate = Labyrinthe[this.x][this.y].state;
                    return;
                }
                if (testoccupe()) {
                    Labyrinthe[this.x][this.y].nextstate = "OCCUPE";

                    // Gérer les cases adjacentes
                    if (this.x + 1 < ligne && Labyrinthe[this.x + 1][this.y].state.equals("OCCUPE")) {
                        Labyrinthe[this.x + 1][this.y].nextstate = "EXPLOREE";
                    }
                    if (this.x - 1 >= 0 && Labyrinthe[this.x - 1][this.y].state.equals("OCCUPE")) {
                        Labyrinthe[this.x - 1][this.y].nextstate = "EXPLOREE";
                    }
                    if (this.y + 1 < colonne && Labyrinthe[this.x][this.y + 1].state.equals("OCCUPE")) {
                        Labyrinthe[this.x][this.y + 1].nextstate = "EXPLOREE";
                    }
                    if (this.y - 1 >= 0 && Labyrinthe[this.x][this.y - 1].state.equals("OCCUPE")) {
                        Labyrinthe[this.x][this.y - 1].nextstate = "EXPLOREE";
                    }
                }
            }
        }

        public void updateState() {
            if (Labyrinthe[this.x][this.y].nextstate != null) {
                Labyrinthe[this.x][this.y].setter(Labyrinthe[this.x][this.y].nextstate);
            } else {
                Labyrinthe[this.x][this.y].setter(Labyrinthe[this.x][this.y].state);
            }
        }

        private boolean testoccupe() {
            try {
                if (this.x + 1 < ligne && Labyrinthe[this.x + 1][this.y] != null && Labyrinthe[this.x + 1][this.y].state.equals("OCCUPE")) {
                    return true;
                }
                if (this.x - 1 >= 0 && Labyrinthe[this.x - 1][this.y] != null && Labyrinthe[this.x - 1][this.y].state.equals("OCCUPE")) {
                    return true;
                }
                if (this.y + 1 < colonne && Labyrinthe[this.x][this.y + 1] != null && Labyrinthe[this.x][this.y + 1].state.equals("OCCUPE")) {
                    return true;
                }
                if (this.y - 1 >= 0 && Labyrinthe[this.x][this.y - 1] != null && Labyrinthe[this.x][this.y - 1].state.equals("OCCUPE")) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Erreur dans testoccupe : " + e.getMessage() + " Position : " + this.x + ", " + this.y);
            }
            return false;
        }
    }
}
