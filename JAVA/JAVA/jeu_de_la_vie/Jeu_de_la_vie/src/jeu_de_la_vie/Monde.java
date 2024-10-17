package jeu_de_la_vie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Monde extends JFrame {
    Cellule[][] grille;
    int nbRow;
    int nbCol;
    double densite;
    JPanel gridPanel;
    JButton step;
    JButton pause;
    boolean isPaused;
    Thread simulationThread;

    public Monde(int nbRow, int nbCol, double densite) {
        this.nbRow = nbRow;
        this.nbCol = nbCol;
        this.densite = densite;
        this.grille = new Cellule[nbRow][nbCol];
        this.isPaused = true; // La simulation commence en pause

        initFrame();
    }

    public void initFrame() {
        setTitle("Jeu de la vie");

        JPanel buttonPanel = new JPanel();
        step = new JButton("step");
        JButton reset = new JButton("reset");
        pause = new JButton("pause");

        step.addActionListener(e -> animeGrille());
        reset.addActionListener(e -> {
            initGrille(this.densite);
            if (simulationThread != null && simulationThread.isAlive()) {
                simulationThread.interrupt(); // Interrompre la simulation si elle est en cours
            }
            pause.setText("start");
            isPaused = true; // Revenir en mode pause après le reset
        });

        pause.addActionListener(e -> toggleSimulation());

        buttonPanel.add(step, BorderLayout.WEST);
        buttonPanel.add(reset, BorderLayout.CENTER);
        buttonPanel.add(pause, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.NORTH);

        initGrille(this.densite);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initGrille(double densite) {
        // Si une ancienne grille existe, la retirer avant d'ajouter une nouvelle
        if (gridPanel != null) {
            remove(gridPanel);
        }

        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                if (Math.random() < densite) {
                    grille[i][j] = new Cellule(grille, i, j, 1);
                } else {
                    grille[i][j] = new Cellule(grille, i, j, 0);
                }
            }
        }

        gridPanel = new JPanel(new GridLayout(nbRow, nbCol));

        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                gridPanel.add(grille[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        pack();
    }

    public void animeGrille() {
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j].calculeEtatSuivant();
            }
        }
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j].metAJourEtatActuel();
            }
        }
        revalidate();
        repaint();
    }

    public void toggleSimulation() {
        if (isPaused) {
            pause.setText("pause");
            isPaused = false;
            simulation(100);
        } else {
            pause.setText("start");
            isPaused = true;
            if (simulationThread != null) {
                simulationThread.interrupt(); // Suspendre la simulation
            }
        }
    }

    public void simulation(int tempsMilliS) {
        step.setEnabled(false);

        simulationThread = new Thread(() -> {
            try {
                while (!isPaused) {
                    animeGrille();
                    Thread.sleep(tempsMilliS);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Monde.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                step.setEnabled(true);
            }
        });
        simulationThread.start();
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

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    etatActuel = (etatActuel == 1) ? 0 : 1;
                    update_color();
                }
            });
        }

        public int nbCellulesVoisinesActives() {
            int nbCellulesVoisines = 0;
            for (int i = this.x - 1; i <= this.x + 1; i++) {
                for (int j = this.y - 1; j <= this.y + 1; j++) {
                    if (i >= 0 && i < nbRow && j >= 0 && j < nbCol) {
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
            update_color();
        }

        private void update_color() {
            if (this.etatActuel == 1) {
                setBackground(Color.white);
            } else {
                setBackground(Color.black);
            }
        }
    }

    public static void main(String[] args) {
        Monde test = new Monde(100, 100, 0.3);
    }
}
