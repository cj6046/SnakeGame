import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

/**
 * This is the Panel class that holds all of the game logic
 * 
 * @author Christopher Jones
 * @version 10 Aug 2021
 */
public class SnakePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    /**
     * This constructs the SnakePanel object
     */
    SnakePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    /**
     * This class starts the game by initializing running to true
     */
    public void startGame() {
        spawnApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * This method comes from the parent class
     * and calls the draw() method
     * 
     * @param g The instance of Graphics class we are using
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * This method continuously draws the game
     * and actually puts the apple from spawnApple on the game board
     * 
     * @param g The instance of Graphics class we are using
     */
    public void draw(Graphics g) {
        // Drawing grid lines
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }
        // Drawing apple
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else {
                g.setColor(new Color(45, 150, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    /**
     * This method moves the snake along a vertical or horizontal path
     * and controls the switching of direction
     */
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        // Switch the direction of the snake
        switch (direction) {
            case ('R') :
                x[0] = x[0] + UNIT_SIZE;
                break;
            case ('L') :
                x[0] = x[0] - UNIT_SIZE;
                break;
            case ('U') :
                y[0] = y[0] - UNIT_SIZE;
                break;
            case ('D') :
                y[0] = y[0] + UNIT_SIZE;
                break;
        }
    }

    /**
     * This method sets a random location for an apple to spawn
     * but does NOT draw the apple in game
     */
    public void spawnApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     * This method checks to see if the snake has eaten an apple
     */
    private void checkApple() {

    }

    /**
     * This method checks to see if the snake has gone off the screen
     * or collided with itself
     */
    public void checkCollisions() {
        // Check self-collision
        for (int i = bodyParts; i > 0; i--) {
            if ((x[i] == x[0]) && y[i] == y[0]) {
                running = false;
            }
        }
        // Check out of bounds collisions
        if (x[0] > SCREEN_WIDTH || x[0] < 0) {
            running = false;
        }
        if (y[0] > SCREEN_HEIGHT || y[0] < 0) {
            running = false;
        }
        // If gameOver, stop timer
        if (!running) {
            timer.stop();
        }
    }

    /**
     * This method handles game over conditions
     * and draws over the game board
     * 
     * @param g The instance of the Graphics class we are using
     */
    public void gameOver(Graphics g) {

    }

    /**
     * This class handles what actions are performed each frame
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    /**
     * Inner class for getting user input
     */
    public class myKeyAdapter extends KeyAdapter {
        /**
         * This class gets the key input from the user
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT :
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT :
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN : 
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_UP :
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
            }
        }
    }
    
}
