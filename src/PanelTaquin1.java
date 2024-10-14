import javax.swing.*;
import java.awt.*;

public class PanelTaquin1 extends JPanel
{
    private CaseTaquin[][] grille;


    PanelTaquin1(int nbRow, int nbCol)
    {
        this.setLayout(new GridLayout(nbRow, nbCol, 5, 5));
        grille = new CaseTaquin[nbRow][nbCol]; // Déclare la grille ici

        for (int i = 0; i < nbRow; i++)
        {
            for (int j = 0; j < nbCol; j++)
            {
                grille[i][j] = new CaseTaquin(i,j);
                this.add(grille[i][j]);

            }
        }
    }

    private class CaseTaquin extends JButton
    {
        private int i;
        private int j;

        CaseTaquin(int i, int j){
            this.setName("12");
            this.i=i;
            this.j=j;
        }
    }
}