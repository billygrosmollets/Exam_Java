import javax.swing.*;
import java.awt.*;

public class Cellule
{
    private int etatActuel;
    private int etatSuivant;
    Cellule[][] grille;
    int x, y;

    Cellule(Cellule[][] grille, int x, int y, int etatInitial)
    {
        etatActuel = etatInitial;
    }

    int nbCellulesVoisinesActives()
    {
        int nbCellulesVoisinesActives = 0;
        try
        {

        }
        catch (Exception e)
        {

        }
        return nbCellulesVoisinesActives;
    }

    void calculeEtatSuivant()
    {
        nbCellulesVoisinesActives();
    }

    void metAJourEtatActuel()
    {
    }
}
