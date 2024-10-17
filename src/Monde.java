import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Monde extends JFrame {
    private Cellule[][] grille;
    private Container container;
    private JPanel panel1;  // Panel pour la grille
    private JPanel panel2;  // Panel pour les boutons
    private JButton start;   // Bouton pour démarrer la simulation
    private JButton stop;    // Bouton pour arrêter la simulation
    private JButton reset;   // Bouton pour réinitialiser la grille
    private boolean simulationEnCours; // Indicateur si la simulation est en cours
    private Thread simulationThread; // Référence au thread de simulation

    Monde(int nbRow, int nbCol, double densite) {
        this.start = new JButton("START");
        this.stop = new JButton("STOP");
        this.reset = new JButton("RESET");
        this.simulationEnCours = false;

        grille = new Cellule[nbRow][nbCol];
        initFrame(nbRow, nbCol);  // Initialisation de la fenêtre
        initGrille(densite); // Initialisation de la grille
        container.revalidate();

        // Action pour le bouton START
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bouton START cliqué !");
                simulation(100); // Démarre la simulation avec un intervalle de 100 ms
            }
        });

        // Action pour le bouton STOP
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bouton STOP cliqué !");
                arreterSimulation(); // Arrête la simulation
            }
        });

        // Action pour le bouton RESET
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bouton RESET cliqué !");
                resetGrille(); // Réinitialisation de la grille
            }
        });
    }

    void initGrille(double densite) {
        panel1.removeAll(); // Vider le panel avant de remplir
        for (int i = 0; i < grille.length; i++) { // Parcours des lignes
            for (int j = 0; j < grille[i].length; j++) { // Parcours des colonnes
                if (Math.random() < densite) {
                    grille[i][j] = new Cellule(grille, i, j, 1);
                } else {
                    grille[i][j] = new Cellule(grille, i, j, 0);
                }
                panel1.add(grille[i][j]);
            }
        }
        panel1.revalidate();
        panel1.repaint();
    }

    void animeGrille() {
        // 1ère étape : Calcul de l'état suivant pour toutes les cellules
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                grille[i][j].calculeEtatSuivant();
            }
        }

        // 2ème étape : Mise à jour de l'état actuel pour toutes les cellules
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                grille[i][j].metAJourEtatActuel();
            }
        }
    }

    void resetGrille() {
        initGrille(0); // Par exemple, réinitialiser avec une densité de 0.5
    }

    void simulation(int tempsMilliS) {
        // Démarre une nouvelle simulation
        if (!simulationEnCours) {
            simulationEnCours = true;
            simulationThread = new Thread(() -> {
                while (simulationEnCours) {
                    animeGrille(); // Met à jour la grille
                    try {
                        Thread.sleep(tempsMilliS); // Pause selon l'intervalle spécifié
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Réinitialise l'état d'interruption
                    }
                }
            });
            simulationThread.start(); // Démarre le thread de simulation
        }
    }

    public void arreterSimulation() {
        simulationEnCours = false; // Indique que la simulation est arrêtée
        if (simulationThread != null) {
            simulationThread.interrupt(); // Interrompt le thread si nécessaire
        }
    }

    void initFrame(int nbRow, int nbCol) {
        this.setTitle("Jeu de la vie");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(nbRow, nbCol, 1, 1));

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());

        panel2.add(start);
        panel2.add(stop);
        panel2.add(reset);

        container.add(panel1, BorderLayout.SOUTH);  // La grille au centre
        container.add(panel2, BorderLayout.NORTH);   // Les boutons en bas

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
