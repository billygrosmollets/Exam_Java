
package labyrinthe;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class FrameLabyrinthe extends JFrame{
    int ligne, colonne;
            
    public FrameLabyrinthe(int ligne, int colonne){
        this.ligne=ligne;
        this.colonne=colonne;
        
        this.setLayout(new BorderLayout());
        
        PanelLabyrinthe test = new PanelLabyrinthe(ligne,colonne,1000);
        
        JLabel ligne_aff = new JLabel("Labyrinthe " + ligne + "X"+ colonne);
        
        add(ligne_aff,BorderLayout.NORTH);
        add(test,BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
            new FrameLabyrinthe(15,17);
    } 
}

