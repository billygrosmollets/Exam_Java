package deminor_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;

public class Demineur extends JFrame {
    Case[][] grille;
    int nbRow, nbCol; 
    double nbMines;
    int nb_case_flag;
    
    private JLabel timerLabel; // Label pour le temps
    private long startTime; // Temps de début
    private boolean gameStarted = false; // Indicateur de début du jeu

    public Demineur(int nbRow, int nbCol, double nbMines) {
        this.nbCol = nbCol;
        this.nbRow = nbRow; 
        this.nbMines = nbMines;
        this.nb_case_flag = 0;
        
        creeGrilleVide();
        ajouteMines();
        
        setTitle("Jeu du Demineur");
        
        JPanel grid = new JPanel();
        JLabel intro = new JLabel(" \n Jeu du Demineur : " + nbRow + "X" + nbCol);
        
        add(intro, BorderLayout.NORTH);
        
        grid.setLayout(new GridLayout(nbRow, nbCol));
        
        for (int i = 0; i < this.nbRow; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                grid.add(grille[i][j]);
            }
        }
        
        add(grid, BorderLayout.CENTER);
        
        timerLabel = new JLabel("Temps écoulé : 0s");
        add(timerLabel, BorderLayout.SOUTH); // Ajout du label pour le timer

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void creeGrilleVide() {
        grille = new Case[this.nbRow][this.nbCol];
        
        for (int i = 0; i < this.nbRow; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                grille[i][j] = new Case(i, j);
                grille[i][j].setText("?");
            }
        }
    }
        
    public boolean creeMine(int row, int col) {
        if (grille[row][col].isMine) {
            return false;
        } else {
            grille[row][col].isMine = true;

            if (row - 1 >= 0) grille[row - 1][col].nbMinesAutour++;
            if (row - 1 >= 0 && col - 1 >= 0) grille[row - 1][col - 1].nbMinesAutour++;
            if (row - 1 >= 0 && col + 1 < nbCol) grille[row - 1][col + 1].nbMinesAutour++;
            if (col + 1 < nbCol) grille[row][col + 1].nbMinesAutour++;
            if (col - 1 >= 0) grille[row][col - 1].nbMinesAutour++;
            if (row + 1 < nbRow && col - 1 >= 0) grille[row + 1][col - 1].nbMinesAutour++;
            if (row + 1 < nbRow) grille[row + 1][col].nbMinesAutour++;
            if (row + 1 < nbRow && col + 1 < nbCol) grille[row + 1][col + 1].nbMinesAutour++;

            return true;
        }
    }

    public void ajouteMines() {
        double mines_crees = 0;
        Random ran = new Random();
        while (mines_crees < this.nbMines) {
            int x = ran.nextInt(this.nbRow);
            int y = ran.nextInt(this.nbCol);
            
            if (creeMine(x, y)) {
                mines_crees++;
            }
        }
    }

    public void arreteJeu(int x, int y) {
        for (int i = 0; i < this.nbRow; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                grille[i][j].setEnabled(false);
                grille[i][j].setBackground(Color.red);
            }
        }
        System.out.println("Vous avez perdu");
        gameStarted = false; // Arrêter le timer
    }

    public void verifieVictoire() {
        boolean victoire = true;

        for (int i = 0; i < this.nbRow; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                if ((!grille[i][j].isMine && grille[i][j].state != 0) || 
                    (grille[i][j].isMine && grille[i][j].state != 2)) {
                    victoire = false;
                }
            }
        }

        if (victoire) {
            System.out.println("Félicitations, vous avez gagné !");
            for (int i = 0; i < this.nbRow; i++) {
                for (int j = 0; j < this.nbCol; j++) {
                    grille[i][j].setEnabled(false);
                    grille[i][j].setBackground(Color.green);
                }
            }
            gameStarted = false; // Arrêter le timer
        }
    }

    private class Case extends JButton {
        int state;
        int row, col;
        int nbMinesAutour;
        boolean isMine;
        
        public Case(int x, int y) {
            this.row = x;
            this.col = y;
            this.nbMinesAutour = 0;
            this.isMine = false;
            this.state = 1;
            
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateCase(); 
                }
            });
            // MouseAdapter pour le clic droit
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        flagCase(); 
                    }
                }
            });
        }
        
        public void updateCase() {
            if (this.state == 0 || this.state == 2) {
                return;
            }
            // Démarrer le timer
            if (!gameStarted) {
                startTime = System.currentTimeMillis();
                gameStarted = true;
                startTimer();
            }
            if (this.isMine) {
                this.setText("M");
                this.state = 0;
                this.setBackground(Color.red);
                arreteJeu(this.row, this.col);
                return;
            }
            if (!mine_autour()) {
                this.setText(" ");
                this.state = 0;
                this.setBackground(Color.white);
                try {
                    for (int i = this.row - 1; i < this.row + 2; i++) {
                        for (int j = this.col - 1; j < this.col + 2; j++) {
                            if (!(i == this.row && j == this.col) && i >= 0 && i < nbRow && j >= 0 && j < nbCol) {
                                grille[i][j].updateCase();
                            }
                        }
                    }
                } catch (Exception e) {
                }
                verifieVictoire();
                return;
            }
            if (mine_autour()) {
                this.setText(String.valueOf(this.nbMinesAutour));
                this.state = 0;
                this.setBackground(Color.white);
                verifieVictoire();
                return;
            }
        }

        public void flagCase() {
            if (this.state == 0) {
                return;
            }
            if (this.state == 1) {
                if (nb_case_flag >= nbMines) {
                    return;
                }
                this.setText("F");
                this.state = 2;
                nb_case_flag++;
                verifieVictoire();
                return;
            }
            this.setText("?");
            this.state = 1;
            nb_case_flag--;
            verifieVictoire();
        }
        
        public boolean mine_autour() {
            try {
                for (int i = this.row - 1; i < this.row + 2; i++) {
                    for (int j = this.col - 1; j < this.col + 2; j++) {
                        if (!(i == this.row && j == this.col) && i >= 0 && i < nbRow && j >= 0 && j < nbCol) {
                            if (grille[i][j].isMine) {
                                return true;
                            }
                        }                        
                    }
                }
            } catch (Exception e) {
            }
            return false;
        }
    }

    private void startTimer() {
        new Thread(() -> {
            while (gameStarted) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                timerLabel.setText("Temps écoulé : " + elapsedTime + "s");
                try {
                    Thread.sleep(1000); // Met à jour toutes les secondes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Demineur(10, 10, 20);
    }
}
