import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Target extends JComponent {
    private Image skull;
    private Image scaledskull;
    private Timer removeTimer;
    private int x; 
    private int y;

    //skull object that we try to shoot while playing the game
    public Target(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            this.skull = ImageIO.read(new File("Images/pixelart_skull.png"));
            this.scaledskull = skull.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            setBounds(x, y, 100, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scaledskull != null) {
            g.drawImage(scaledskull, x, y, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    public void startTargetTimer(int delay, ActionListener listener) {
        removeTimer = new Timer(delay, listener);
        removeTimer.setRepeats(false);
        removeTimer.start();
    }

    public void stopTargetTimer() {
        if (removeTimer != null) {
            removeTimer.stop();
        }
    }
}

