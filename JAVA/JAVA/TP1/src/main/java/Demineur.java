import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Demineur extends JFrame {

    private Case[][] grille;
    private Integer nbRow;
    private Integer nbCol;
    private Integer nbMines;
    private Integer nbFlagged = 0;
    private JLabel scoreLabel; // Label pour afficher le score
    private JPanel pControl; // Panel de contrôle

    public Demineur(int nbRow, int nbCol, int nbMines) {
        this.nbRow = nbRow;
        this.nbCol = nbCol;
        this.nbMines = nbMines;

        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pControl = new JPanel();
        scoreLabel = new JLabel("Mines trouvées : 0");
        JButton stopButton = new JButton("STOP");
        stopButton.addActionListener(e -> arreteJeu()); // Gestion du bouton STOP

        pControl.add(scoreLabel);
        pControl.add(stopButton);
        add(pControl, BorderLayout.NORTH);

        creeGrilleVide();
        ajouteMines();
    }

    public void creeGrilleVide() {
        grille = new Case[nbRow][nbCol];

        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j] = new Case(i, j);
            }
        }
    }

    public JPanel getGrillePanel() { // Méthode ajoutée
        JPanel grillePanel = new JPanel();
        grillePanel.setLayout(new GridLayout(nbRow, nbCol));

        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grillePanel.add(grille[i][j]);
            }
        }

        return grillePanel;
    }

    public void arreteJeu() {
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                grille[i][j].setEnabled(false);
                if (grille[i][j].isMine) {
                    if ("FLAGGED".equals(grille[i][j].state)) {
                        grille[i][j].setBackground(Color.GREEN);
                        grille[i][j].setText("M");
                    } else {
                        grille[i][j].setBackground(Color.RED);
                        grille[i][j].setText("M");
                    }
                }
            }
        }
    }

    private void checkVictory() {
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                if (!grille[i][j].isMine && "INVISIBLE".equals(grille[i][j].state)) {
                    return; // Il reste des cases non révélées
                }
            }
        }
        arreteJeu();
        JOptionPane.showMessageDialog(this, "VICTOIRE ROYALE !");
    }

    private class Case extends JButton {
        String state;
        Integer row;
        Integer col;
        Integer nbMinesAutour;
        Boolean isMine;

        public Case(Integer x, Integer y) {
            this.state = "INVISIBLE";
            this.row = x;
            this.col = y;
            this.nbMinesAutour = 0;
            this.isMine = false;

            setText("?");

            // ActionListener pour le clic gauche
            addActionListener(e -> updateCase());

            // MouseAdapter pour le clic droit
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        flagCase();
                    }
                }
            });
        }

        public void updateCase() {
            if ("VISIBLE".equals(this.state) || "FLAGGED".equals(this.state)) {
                return;
            }
            if (this.isMine) {
                setText("M");
                setBackground(Color.RED);
                this.state = "VISIBLE";
                arreteJeu();
                JOptionPane.showMessageDialog(Demineur.this, "Défaite ! Il y avait une mine ici.");
                return;
            }

            if (this.nbMinesAutour > 0) {
                this.state = "VISIBLE";
                setBackground(Color.WHITE);
                setText(" " + this.nbMinesAutour);
            } else {
                this.state = "VISIBLE";
                setBackground(Color.WHITE);
                setText(" ");
                for (int i = this.row - 1; i <= this.row + 1; i++) {
                    for (int j = this.col - 1; j <= this.col + 1; j++) {
                        if (i >= 0 && i < nbRow && j >= 0 && j < nbCol) {
                            grille[i][j].updateCase();
                        }
                    }
                }
            }

            checkVictory(); // Vérifier si la partie est gagnée
        }

        public void flagCase() {
            if ("VISIBLE".equals(this.state)) {
                // Ne rien faire
            } else if ("INVISIBLE".equals(this.state)) {
                if (nbFlagged < nbMines) {
                    this.state = "FLAGGED";
                    setText("F");
                    nbFlagged++;
                    scoreLabel.setText("Mines trouvées : " + nbFlagged); // Met à jour le score
                }
            } else if ("FLAGGED".equals(this.state)) {
                this.state = "INVISIBLE";
                setText("?");
                nbFlagged--;
                scoreLabel.setText("Mines trouvées : " + nbFlagged); // Met à jour le score
            }
        }
    }

    public void ajouteMines() {
        Random rand = new Random();
        for (int i = 0; i < nbMines; i++) {
            int rand_int1, rand_int2;
            do {
                rand_int1 = rand.nextInt(nbRow);
                rand_int2 = rand.nextInt(nbCol);
            } while (!creeMine(rand_int1, rand_int2));
        }
    }

    public boolean creeMine(int row, int col) {
        if (!grille[row][col].isMine) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i >= 0 && i < nbRow && j >= 0 && j < nbCol && (i != row || j != col)) {
                        try {
                            grille[i][j].nbMinesAutour += 1;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // Ignorer l'exception
                        }
                    }
                }
            }
            grille[row][col].isMine = true;
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Demineur demineur = new Demineur(10, 15, 10);
        JPanel pGrille = demineur.getGrillePanel(); // Appel à la méthode getGrillePanel
        demineur.add(pGrille, BorderLayout.CENTER);
        demineur.setVisible(true);
    }
}
