import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MouseListenerExample extends JFrame {

    public MouseListenerExample() {
        // Créer un JPanel
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        add(panel);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                change_color(panel); 
            }
        });

        // Paramètres de la fenêtre
        setTitle("Exemple de MouseListener");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void change_color(JPanel panel){
        Random ran = new Random();
        int r = ran.nextInt(256);
        int g = ran.nextInt(256);
        int b = ran.nextInt(256);
        
        panel.setBackground(new Color(r,g,b));
        repaint();
    };
    public static void main(String[] args) {
        new MouseListenerExample();
    }
}
