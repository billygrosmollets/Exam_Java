import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class FramePendu extends JFrame {
    String Mot;
    Integer nb_a_trouver;
    Integer nb_chances;
    JLabel[] Lettre;
    MyButton[] touches;
    JPanel un;
    JLabel chancesLabel;  // Label pour afficher le nombre de chances restantes
    JLabel lettresRestantesLabel;  // Label pour afficher le nombre de lettres restantes

    public FramePendu(String Mot) {
        this.Mot = Mot;
        this.Lettre = new JLabel[Mot.length()];
        this.touches = new MyButton[26];
        this.nb_a_trouver = Mot.length();
        this.nb_chances = 10;

        char lettre = 'A';

        un = new JPanel();
        JPanel deux = new JPanel();
        JPanel centerPanel = new JPanel(new BorderLayout()); // Panneau central qui regroupera les lettres et le clavier

        un.setLayout(new GridLayout(1, Mot.length()));
        deux.setLayout(new GridLayout(3, 9));

        // Initialisation des lettres à deviner (underscore)
        for (int i = 0; i < Mot.length(); i++) {
            Lettre[i] = new JLabel("_");  // Affichage des underscores initialement
            un.add(Lettre[i]);
        }

        // Création des boutons pour le clavier virtuel
        for (int i = 0; i < 26; i++) {
            final int index = i;
            touches[i] = new MyButton(String.valueOf(lettre));
            touches[i].addActionListener(e -> {
                touches[index].desac();
                maj(touches[index].getText().charAt(0));
            });
            lettre++;
            deux.add(touches[i]);
        }

        setTitle("Jeu du pendu");

        // Ajout des composants à la fenêtre
        lettresRestantesLabel = new JLabel("Il vous reste " + this.nb_a_trouver + " lettres à trouver !");
        this.add(lettresRestantesLabel, BorderLayout.NORTH);

        // Ajout des panneaux de lettres et du clavier dans un panneau central
        centerPanel.add(un, BorderLayout.NORTH);  // Panneau des lettres
        centerPanel.add(deux, BorderLayout.SOUTH);  // Panneau du clavier

        this.add(centerPanel, BorderLayout.CENTER);  // Ajout du panneau central à la fenêtre

        // Ajout du label pour afficher le nombre de chances restantes
        chancesLabel = new JLabel("Il vous reste " + this.nb_chances + " chances");
        this.add(chancesLabel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // Mise à jour des lettres trouvées et du nombre de chances
    public void maj(char lettre) {
        boolean lettre_trouvee = false;

        // Vérification si la lettre est dans le mot
        for (int i = 0; i < Mot.length(); i++) {
            if (Mot.charAt(i) == lettre) {
                Lettre[i].setText(String.valueOf(lettre));
                lettre_trouvee = true;
                nb_a_trouver--;  // Décrémentation du nombre de lettres à trouver
            }
        }

        if (!lettre_trouvee) {
            nb_chances--;  // Réduction des chances si la lettre n'est pas trouvée
        }

        // Mise à jour du label du nombre de chances restantes
        chancesLabel.setText("Il vous reste " + nb_chances + " chances");

        // Mise à jour du label du nombre de lettres à trouver
        lettresRestantesLabel.setText("Il vous reste " + nb_a_trouver + " lettres à trouver !");

        // Mise à jour de l'interface (redessiner le panneau contenant les lettres)
        un.revalidate();
        un.repaint();

        // Vérification de la fin du jeu
        end_game();
    }

    // Vérification de la fin du jeu (victoire ou défaite)
    public void end_game() {
        if (nb_a_trouver == 0) {
            System.out.println("Félicitations, vous avez gagné !");
        } else if (nb_chances == 0) {
            System.out.println("Désolé, vous avez perdu !");
        }
    }

    // Classe interne pour les boutons du clavier
    public class MyButton extends JButton {
        public MyButton(String lettre) {
            super(lettre);
        }

        public void desac() {
            this.setEnabled(false);  // Désactiver le bouton
        }
    }

    public static void main(String[] args) {
        FramePendu test = new FramePendu("AVATAR");
    }
}
