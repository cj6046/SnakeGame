import javax.swing.JFrame;

/**
 * This class is the frame for the Snake game.
 * 
 * @author Christopher Jones
 * @version 10 Aug 2021
 */
public class SnakeFrame extends JFrame {

    SnakeFrame() {
        this.add(new SnakePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
