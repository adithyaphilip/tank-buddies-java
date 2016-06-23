import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;

public class Gui extends JPanel {
    Game game;
    State state;

    private final static Color bgColor = Color.BLACK;
    private final static Color playerColor = Color.BLUE;
    private final static Color bulletColor = Color.RED;

    public void start(final BlockingQueue<Integer> moveQueue) {
        JFrame f = new JFrame();
        f.setSize(600, 600);
        f.add(this);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                moveQueue.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void draw(Game game, State state) {
        this.game = game;
        this.state = state;
        repaint();
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        int cellSize = getWidth() / game.map.length;

        int[][] map = game.map;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                g.setColor(bgColor);
                boolean present = false;
                for (Player player : state.getPlayers()) {
                    if (player.getX() == j && player.getY() == i) {
                        g.setColor(playerColor);
                        present = true;
                        g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                    }
                }

                for (Bullet bullet : state.getBullets()) {
                    if (bullet.getX() == j && bullet.getY() == i) {
                        g.setColor(bulletColor);
                        present = true;
                        g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                    }
                }
                if (!present) {
                    g.drawRect(j*cellSize, i*cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
