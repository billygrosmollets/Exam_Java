import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GrilleEntrainement2 extends JPanel {
    private Cellule[][] grille;
    private int nbRow, nbCol;
    private volatile boolean running;
    private Thread parcoursThread;

    GrilleEntrainement2(int nbRow, int nbCol) {
        this.nbRow = nbRow;
        this.nbCol = nbCol;
        this.setLayout(new GridLayout(nbRow, nbCol));
        grille = new Cellule[nbRow][nbCol];
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j] = new Cellule(i, j);
                this.add(grille[i][j]);
            }
        }
    }

    public synchronized void lancerThreadParcoursGrille() {
        if (running) {
            System.out.println("Un thread est déjà en cours.");
            return;  // Empêche de lancer un nouveau thread si un autre est déjà en cours
        }

        running = true;

        // Création d'un nouveau thread pour le parcours de la grille
        parcoursThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < nbRow && running; i++) {
                    for (int j = 0; j < nbCol && running; j++) {
                        try {
                            Thread.sleep(200);  // Simule une pause pour le parcours
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                        System.out.println("Parcours de la cellule: Ligne = " + i + ", Colonne = " + j);
                    }
                }
                System.out.println("Thread arrêté.");
                running = false;  // Remet à false après l'arrêt du thread
            }
        };

        parcoursThread.start();
    }

    public synchronized void stopperThreadParcours() {
        if (!running) {
            System.out.println("Aucun thread en cours.");
            return;  // Empêche d'essayer d'arrêter un thread qui n'est pas actif
        }

        running = false;
        if (parcoursThread != null) {
            parcoursThread.interrupt();  // Interrompt le thread en cours
            try {
                parcoursThread.join();  // Attend la fin du thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class Cellule extends JButton {
        private int i;
        private int j;

        Cellule(int i, int j) {
            this.i = i;
            this.j = j;

            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Clique sur la cellule: Ligne = " + i + ", Colonne = " + j);
                    lancerThreadParcoursGrille();
                }
            });

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Clic sur la cellule: Ligne = " + (i + 1) + ", Colonne = " + (j + 1));
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });

            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_Z) {
                        System.out.println("Touche Z pressée. Arrêt du thread.");
                        stopperThreadParcours();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }
    }
}
