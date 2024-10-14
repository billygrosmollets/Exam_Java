import javax.swing.*;
import java.awt.*;

public class FrameTaquin extends JFrame {

    FrameTaquin(int nbRow, int nbCol) {
        this.setTitle("Taquin");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel("Bonne chance !");
        label.setPreferredSize(new Dimension(400, 50)); // Largeur : 400, Hauteur : 100
        this.add(label, BorderLayout.NORTH); // Ajoute le label en haut

        PanelTaquin1 panelTaquin = new PanelTaquin1(nbRow, nbCol);
        panelTaquin.setPreferredSize(new Dimension(400, 350)); // Largeur : 400, Hauteur : 300
        this.add(panelTaquin, BorderLayout.CENTER); // Ajoute le panel au centre

        this.setVisible(true);
    }
}
