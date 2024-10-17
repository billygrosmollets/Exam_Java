import javax.swing.*;
import java.awt.*;

public class FrameLabyrinthe extends JFrame
{
    private int nbRow;
    private int nbCol;

    FrameLabyrinthe(int nbRow, int nbCol)
    {
        this.setTitle("Labyrinthe");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //this.setSize(800,400);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.add(new JLabel("GAUCHE"));
        panel2.add(new JLabel("DROITE"));
        panel3.add(new JLabel("LABYRINTHE"+ nbRow + nbCol ),BorderLayout.NORTH);

        panel1.setPreferredSize(new Dimension(400,400));
        panel2.setPreferredSize(new Dimension(400,400));
        panel3.setPreferredSize(new Dimension(400,400));


        this.add(panel3,BorderLayout.NORTH);
        this.add(panel1,BorderLayout.EAST);
        this.add(panel2,BorderLayout.WEST);
        this.add(new PanelLabyrinthe(nbRow,nbCol),BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }
}
