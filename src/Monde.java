import javax.swing.*;
import java.awt.*;

public class Monde extends JFrame{
    private Cellule[][] grille;
    Container container;

    Monde(int nbRow, int nbCol, double densite)
    {
        this.setTitle("Jeu de la vie");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = this.getContentPane();
        initGrille(densite);
        this.setVisible(true);
    }

    void initGrille(double densite)
    {
        if (Math.random() < densite)
        {

        }
    }

    void animeGrille(){} // evoltion grille


}
