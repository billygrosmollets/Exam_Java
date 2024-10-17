import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTaquin1 extends JPanel
{
    private CaseTaquin[][] grille;

    PanelTaquin1(int nbRow, int nbCol)
    {
        this.setLayout(new GridLayout(nbRow, nbCol, 5, 5));
        grille = new CaseTaquin[nbRow][nbCol];

        int count = 1;
        for (int i = 0; i < nbRow; i++)
        {
            for (int j = 0; j < nbCol; j++)
            {
                if (i == nbRow - 1 && j == nbCol - 1)
                {
                    grille[i][j] = new CaseTaquin(i, j, ""); // Taquin (case vide)
                } else
                {
                    grille[i][j] = new CaseTaquin(i, j, String.valueOf(count));
                    count++;
                }
                this.add(grille[i][j]);
            }
        }
    }

    // Classe interne représentant chaque case du taquin
    private class CaseTaquin extends JButton
    {
        private int i;
        private int j;

        CaseTaquin(int i, int j, String text)
        {
            this.i = i;
            this.j = j;
            this.setText(text);

            this.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    deplacement();
                    getinfocase();
                }
            });
        }

        // Vérifie si cette case est la case vide (taquin)
        public boolean isTaquin()
        {
            return this.getText().equals("");
        }

        // Affiche des informations sur la case (pour débogage)
        public void getinfocase()
        {
            if (!isTaquin())
                System.out.println("Case numéro " + getText() + " coordonnées : (" + i + ", " + j + ")");
            else
                System.out.println("Le taquin a pour coordonnées : (" + i + ", " + j + ")");
        }

        // Gestion du déplacement en testant les cases adjacentes
        public void deplacement()
        {
            // Vérifier les cases en haut, en bas, à gauche et à droite
            if (i > 0 && grille[i - 1][j].isTaquin()) // Case au-dessus
            {
                echangeAvec(grille[i - 1][j]);
            }
            else if (i < grille.length - 1 && grille[i + 1][j].isTaquin()) // Case en dessous
            {
                echangeAvec(grille[i + 1][j]);
            }
            else if (j > 0 && grille[i][j - 1].isTaquin()) // Case à gauche
            {
                echangeAvec(grille[i][j - 1]);
            }
            else if (j < grille[0].length - 1 && grille[i][j + 1].isTaquin()) // Case à droite
            {
                echangeAvec(grille[i][j + 1]);
            }
            else
            {
                System.out.println("Déplacement impossible : aucune case adjacente n'est vide.");
            }
        }

        // Méthode pour échanger le texte avec une autre case (ici avec le taquin)
        private void echangeAvec(CaseTaquin caseVide)
        {
            // Échange les labels
            caseVide.setText(this.getText());
            this.setText("");

            // Demande un réaffichage
            revalidate();
            repaint();
        }
    }
}
