import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameFrame extends JFrame {
    
    private Game gamePanel;
    private JPanel topPanel;
    private int screenHeight;
    private int screenWidth;
    //Just the Main frame that holds the responsive Panel
    public GameFrame(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth =  (int) ((int) screenSize.width * 0.85);
        screenHeight =  (int) ((int) screenSize.height * 0.85);
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        topPanel.setPreferredSize(new Dimension(screenWidth, 50));
        topPanel.setBackground(new Color(0, 0, 0, 0.2f)); // Semi-transparent background
        //Responsive Menu && Game Panel
        gamePanel = new Game(topPanel,screenWidth,screenHeight);
       
        
        try {
            Image icon = ImageIO.read(new File("Images/pistol.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Frame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
