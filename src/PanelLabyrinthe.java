import java.awt.*;

public class PanelLabyrinthe extends Panel
{
    private CaseLabyrinthe[][] labyrinthes;

    PanelLabyrinthe(int nbRow, int nbCol)
    {
        LabyrintheGenerator labyrintheGenerator = new LabyrintheGenerator();
        String[][] lab = labyrintheGenerator.getLabyrintheAsStringArray(nbRow, nbCol);
        labyrintheGenerator.printLabyrinthe(lab);

        labyrinthes = new CaseLabyrinthe[nbRow][nbCol];
        setLayout(new GridLayout(nbRow, nbCol));

        boolean firstLibreFound = false;

        for (int i = 0; i < nbRow; i++)
        {
            for (int j = 0; j < nbCol; j++)
            {
                switch (lab[i][j])
                {
                    case ".":
                        if (!firstLibreFound)
                        {
                            labyrinthes[i][j] = new CaseLabyrinthe(i, j, CaseLabyrinthe.OCCUPE);
                            firstLibreFound = true;
                        } else
                        {
                            labyrinthes[i][j] = new CaseLabyrinthe(i, j, CaseLabyrinthe.LIBRE);
                        }
                        break;
                    case "#":
                        labyrinthes[i][j] = new CaseLabyrinthe(i, j, CaseLabyrinthe.INTERDIT);
                        break;
                    case "+":
                        labyrinthes[i][j] = new CaseLabyrinthe(i, j, CaseLabyrinthe.LIBRE);
                        labyrinthes[i][j].setIsOutput(true);
                        break;
                    default:
                        labyrinthes[i][j] = new CaseLabyrinthe(i, j, CaseLabyrinthe.LIBRE);
                }
                add(labyrinthes[i][j]);
            }
        }
    }

    private static class CaseLabyrinthe extends Panel
    {
        private int state;
        private boolean isOutput;

        public static final int INTERDIT = 0;
        public static final int OCCUPE = 1;
        public static final int LIBRE = 2;
        public static final int EXPLOREE = 3;

        CaseLabyrinthe(int nbRowIndex, int nbColIndex, int initialState)
        {
            this.setPreferredSize(new Dimension(20, 20));
            setState(initialState);
            this.isOutput = false;
        }

        public void setState(int state)
        {
            this.state = state;
            updateCaseColor();
        }

        public void setIsOutput(boolean isOutput)
        {
            this.isOutput = isOutput;
        }

        private void updateCaseColor()
        {
            switch (state)
            {
                case INTERDIT:
                    this.setBackground(Color.BLACK);
                    break;
                case OCCUPE:
                    this.setBackground(Color.BLUE);
                    break;
                case LIBRE:
                    this.setBackground(Color.WHITE);
                    break;
                case EXPLOREE:
                    this.setBackground(Color.GRAY);
                    break;
                default:
                    this.setBackground(Color.WHITE);
            }
        }
    }
}
