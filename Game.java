import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class Game extends JPanel implements KeyListener{
    
    private boolean gameStarted = false;
    private int score;
    private int delay;
    private int gameLength;
    private String difficulty;
    private int countDownValue = 5;
    private float panelOpacity = 1.0f;
    private int escClickedCtr = 0;
    private Image crossHair;
    private Image background1;
    private Image foreground;
    private Image builgings;
    private Image bgbuildings;
    private GameClock clock;
    private Timer timer;
    private UniqueButton startButton;
    private UniqueButton optionsButton;
    private UniqueButton quitButton;
    private UniqueButton menuButton;
    private UniqueButton retryButton;
    private UniqueButton hardButton;
    private UniqueButton mediumButton;
    private UniqueButton easyButton;
    private JLabel gameTitle;
    private JLabel countdownTitle;
    private JLabel setDifficultyTitle;
    private JLabel scoreLine;
    private JLabel targetsLeft;
    private Random random;
    private int screenWidth;
    private int screenHeight;
    private JPanel topPanel;

    //Prepares the window for the player
    public Game(JPanel topPanel,int screenWidth, int screenHeight){
    try {
            background1 = ImageIO.read(new File("Images/skill-desc_0003_bg.png"));
            foreground = ImageIO.read(new File("Images/skill-desc_0000_foreground.png"));
            bgbuildings = ImageIO.read(new File("Images/skill-desc_0002_far-buildings.png"));
            builgings = ImageIO.read(new File("Images/skill-desc_0001_buildings.png"));
            crossHair = ImageIO.read(new File("Images/Crosshair 1.png"));
        }catch (IOException e) {
                e.printStackTrace();
        }

            random = new Random();
            this.difficulty = "easy"; //vanilla setting

            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Cursor blankCursor = toolkit.createCustomCursor(crossHair, new Point(0, 0), "blank cursor");
            setCursor(blankCursor);

            //Crosshair
            this.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    repaint();
                }
            });

            
            // Title
            gameTitle = new JLabel("Reflexer");
            gameTitle.setFont(new Font("Monospaced", Font.BOLD, 250));
            gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
            gameTitle.setForeground(new Color(52, 137, 88));
            gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            //Countdown Title
            countdownTitle = new JLabel(countDownValue + " ");
            countdownTitle.setFont(new Font("Monospaced", Font.BOLD, 150));
            countdownTitle.setForeground(new Color(52, 137, 88));
            countdownTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            countdownTitle.setVisible(false);

            //SetDifficulty Title
            setDifficultyTitle = new JLabel("Choose your Difficulty");
            setDifficultyTitle.setFont(new Font("Monospaced", Font.BOLD, 100));
            setDifficultyTitle.setForeground(new Color(52, 137, 88));
            setDifficultyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            setDifficultyTitle.setVisible(false);
            
            // Top Panel for score and targets left
            this.topPanel = topPanel;

            //Score
            scoreLine = new JLabel();
            scoreLine.setFont(new Font("Monospaced", Font.BOLD, 25));
            scoreLine.setForeground(Color.WHITE);
            scoreLine.setAlignmentX(Component.CENTER_ALIGNMENT);
            scoreLine.setVisible(false);

            //Targets Left
            targetsLeft = new JLabel();
            targetsLeft.setFont(new Font("Monospaced", Font.BOLD, 25));
            targetsLeft.setForeground(Color.WHITE);
            targetsLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
            targetsLeft.setVisible(false);

            // Main menu buttons
            startButton = new UniqueButton("Start");
            optionsButton = new UniqueButton("Options");
            quitButton = new UniqueButton("Quit");
            //Ending screen buttons
            retryButton = new UniqueButton("Retry?");
            retryButton.setVisible(false);
            menuButton = new UniqueButton("Menu");
            menuButton.setVisible(false);
            //Difficulty settings buttons
            hardButton = new UniqueButton("Hard");
            hardButton.setVisible(false);
            mediumButton = new UniqueButton("Medium");
            mediumButton.setVisible(false);
            easyButton = new UniqueButton("Easy");
            easyButton.setVisible(false);

            // Add title and buttons with padding
            this.add(Box.createVerticalGlue());
            topPanel.add(scoreLine);
            topPanel.add(targetsLeft);
            this.add(setDifficultyTitle);
            
            this.add(retryButton);
            this.add(Box.createVerticalStrut(10));
            this.add(hardButton);
            this.add(Box.createVerticalStrut(10));
            this.add(mediumButton);
            this.add(Box.createVerticalStrut(10));
            this.add(easyButton);
            this.add(Box.createVerticalStrut(10));
            this.add(menuButton);            
            this.add(gameTitle);
            this.add(countdownTitle);
            this.add(Box.createVerticalStrut(30));
            this.add(startButton);
            this.add(Box.createVerticalStrut(30));
            this.add(optionsButton);
            this.add(Box.createVerticalStrut(30));
            this.add(quitButton);
            this.add(Box.createVerticalGlue());

        
            //Neccessary for keyboard observations
            this.setFocusable(true); 
            this.addKeyListener(this);

            //Making buttons usable
            startButton.addActionListener(e -> startButtonPressed());
            optionsButton.addActionListener(e -> optionsButtonPressed());
            quitButton.addActionListener(e -> quitButtonPressed());
            menuButton.addActionListener(e -> menuButtonPressed());
            retryButton.addActionListener(e -> retryButtonPressed());
            hardButton.addActionListener(e -> setDifficultyH());
            mediumButton.addActionListener(e -> setDifficultyM());
            easyButton.addActionListener(e -> setDifficultyE());
    }

    //First countdown before the game starts
    private void startCountdown() {
        
        timer = new Timer(1000, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownTitle.setVisible(true);
                countdownTitle.setText(countDownValue + "");
                countDownValue--;
                if (countDownValue < 0) {
                    gameStarted = true;
                    countdownTitle.setVisible(false);
                    ((Timer)e.getSource()).stop();
                    run();   
                }
                
            }
        });
        timer.start();  
    }

    //Action Listeners
    private void startButtonPressed() {
        resetStats();
        disableMenu();
        startCountdown();
        
    }

    private void setDifficultyH() {
        difficulty = "hard";
    }

    private void setDifficultyE() {
        difficulty = "easy";
    }

    private void setDifficultyM() {
        difficulty = "medium";
    }

    private void retryButtonPressed() {
        panelOpacity = 1.0f;
        resetStats();
        startCountdown();
        menuButton.setVisible(false);
        retryButton.setVisible(false);
        this.repaint();
    }

    private void optionsButtonPressed() {
        setDifficultyTitle.setVisible(true);
        disableMenu();
        hardButton.setVisible(true);
        mediumButton.setVisible(true);
        easyButton.setVisible(true);
        menuButton.setVisible(true);
        this.repaint();
    }

    private void quitButtonPressed() {
        System.exit(0);
    }

    private void menuButtonPressed() {
        panelOpacity = 1.0f;
        gameStarted = false;
        menuButton.setVisible(false);
        retryButton.setVisible(false);
        scoreLine.setVisible(false);
        hardButton.setVisible(false);
        mediumButton.setVisible(false);
        easyButton.setVisible(false);
        targetsLeft.setVisible(false);
        setDifficultyTitle.setVisible(false);
        enableMenu();
        this.repaint();
    }

    private void disableMenu(){
        gameTitle.setVisible(false);
        optionsButton.setVisible(false);
        quitButton.setVisible(false);
        startButton.setVisible(false);
    }

    private void enableMenu(){
        gameTitle.setVisible(true);
        optionsButton.setVisible(true);
        quitButton.setVisible(true);
        startButton.setVisible(true);
    }

    private void resetStats(){
        this.gameLength = 20;
        this.score = 0;
        scoreLine.setText("Score: " + score);
        targetsLeft.setText("Targets Left: " + gameLength);
        topPanel.repaint();
        this.repaint();
    }

    //Needs improvement
    private void changeScreenOpacity(){
        if(escClickedCtr == 1 ){
            menuButton.setVisible(false);
            quitButton.setVisible(false);
            panelOpacity = 1.0f;
            escClickedCtr = 0;
            clock.resumeClockThread();
        }
        else{
            menuButton.setVisible(true);
            quitButton.setVisible(true);
            panelOpacity = 0.6f;
            escClickedCtr = 1;
            clock.pauseClockThread();
        }
        
        this.repaint(); 
    }

    private void displayEndMenu(){
        menuButton.setVisible(true);
        retryButton.setVisible(true);
        this.repaint(); 
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, panelOpacity)); 
        g2D.drawImage(background1, 0, 0, getWidth(), getHeight(), this);
        g2D.drawImage(bgbuildings, 0, 0, getWidth(), getHeight(), this);
        g2D.drawImage(builgings, 0, 0, getWidth(), getHeight(), this);
        g2D.drawImage(foreground, 0, 0, getWidth(), getHeight(), this);
    }
    
    private void run(){

        switch (difficulty) {
            case "easy":
                delay = 2000;
                break;
            case "medium":
                delay = 1750;
                break;
            case "hard":
                delay = 1250;
                break;
            default:
                break;
        }

        clock = new GameClock(this, "hard");
        clock.start();
        scoreLine.setVisible(true);
        targetsLeft.setVisible(true);
    }

    private void updateScore(){
        score +=100;
        scoreLine.setText("Score: " + score);
    }

    public void displayCurrentTime(int time){
            gameLength = time;
            if(gameLength == 0){
                displayEndMenu();
            }
            targetsLeft.setText(gameLength + " ");
    }

    public void generateTarget() {
        int x = random.nextInt(screenWidth);
        int y = random.nextInt(screenHeight/4);
        Target target = new Target(x,y); 
        add(target);
        revalidate();
        repaint();

        //if we hit the skull it dissapears
        target.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateScore();
                target.stopTargetTimer();
                remove(target);
                revalidate();
                repaint();
            }
        });

        
        target.startTargetTimer(delay, e -> {
            remove(target);
            revalidate();
            repaint();
        });
    }

    //This needs correction, only activated while the game is running, not in the menu or settings
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameStarted) {
            changeScreenOpacity();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Not useful in this particular case
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        //Not useful in this particular case
    }
}
