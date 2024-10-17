package examcao_hamaidi;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class FramePendu extends JFrame {
    private int nb_a_trouver;
    private JLabel[] lettres;
    private String mot;
    private MyButton[] touches;
    private int nb_chances;
    
    public FramePendu(String mot){
        this.mot = mot;
        this.nb_a_trouver = mot.length();
        this.lettres = new JLabel[mot.length()];
        this.touches = new MyButton[26];
        this.nb_chances = 6;
        
        this.setTitle("Jeu du pendu");
        // Définir la taille de la fenêtre
        this.setSize(500, 300);
        // Définir le comportement par défaut à la fermeture
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centrer la fenêtre au milieu de l'écran
        this.setLocationRelativeTo(null);
        // Utiliser un BorderLayout pour la fenêtre
        this.setLayout(new GridLayout(4, 1));
        
        JLabel text_a_trouver = new JLabel("  Il vous reste " + nb_a_trouver + " lettres a trouver");
        this.add(text_a_trouver, BorderLayout.NORTH);
        
        JPanel lettres_panel = new JPanel();
        lettres_panel.setLayout(new GridLayout(1, mot.length()));
        for (int i = 0; i< mot.length(); i++){
            lettres[i]= new JLabel("_");
            lettres_panel.add(lettres[i]);
        }
        lettres_panel.setBackground(Color.white);
        this.add(lettres_panel, BorderLayout.CENTER);
        
        JPanel touches_panel = new JPanel();
        touches_panel.setLayout(new GridLayout(3, 9)); 
        
        for (char c = 'A'; c <= 'Z'; c++){
            int index = c - 'A'; // Calculer l'index de 0 à 25
            touches[index] = new MyButton(String.valueOf(c)); // Créer le bouton
            touches_panel.add(touches[index]); // Ajouter le bouton au panneau
        }
        this.add(touches_panel, BorderLayout.SOUTH);
        
        JLabel text_nb_chances = new JLabel("  Il vous reste " + nb_chances + " chances"); 
        this.add(text_nb_chances);
        // Rendre la fenêtre visible
        this.setVisible(true);
        
        
    }
    class MyButton extends JButton{
            public MyButton(String label){
                super(label);
            }
    }
    
    public static void main(String[] args) {
        FramePendu frame = new FramePendu("AVATAR");
    }
}
