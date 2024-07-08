import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UniqueButton extends JButton {

    private String buttonName;

    //Special button class to produce buttons faster by avoiding writing the same thing in Game class
    UniqueButton(String type) {
        super(type); 
        this.buttonName = type;

        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setFont(new Font("Monospaced", Font.BOLD, 50));
        this.setPreferredSize(new Dimension(300, 80)); 
        this.setMaximumSize(new Dimension(300, 80));  
        this.setFocusPainted(false); 
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 5, true);
        this.setBorder(roundedBorder);
    }

    public String getButtonName() {
        return buttonName;
    }
}