import javax.swing.*;
import java.awt.*;

public class FrameEntrainement extends JFrame
{
    JLabel labelTitre;
    JPanel panelTitre;
    JPanel panelGrille;
    JPanel panelInfoDeplacement;

    FrameEntrainement()
    {
        this.setTitle("Test");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        labelTitre = new JLabel("Test");
        panelTitre = new JPanel();
        panelGrille = new GrilleEntrainement(10, 10);
        panelInfoDeplacement = new JPanel();
        panelTitre.setPreferredSize(new Dimension(400, 100));
        panelGrille.setPreferredSize(new Dimension(400, 400));
        panelInfoDeplacement.setPreferredSize(new Dimension(400, 100));
        panelTitre.add(labelTitre);
        this.add((panelTitre), BorderLayout.NORTH);
        this.add((panelGrille), BorderLayout.CENTER);
        this.add((panelInfoDeplacement), BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }
}


