public class GameClock extends Thread {
    private Game game;
    private boolean active;
    private int gameLength;
    private String difficulty;
    private final Object pauseLock = new Object();
    private boolean paused;

    //This is a class Thread that is responsible for the actual game's countdown
    public GameClock(Game game, String difficulty) {
        this.game = game;
        this.gameLength = 19;
        this.difficulty = difficulty;
        this.active = true;
        this.paused = false;
    }

    @Override
    public void run() {
        while (active && gameLength >= 0) {
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                switch (difficulty) {
                    case "easy":
                        game.generateTarget(); 
                        Thread.sleep(2000);
                        break;
                    case "medium":
                        game.generateTarget(); 
                        Thread.sleep(1750);
                        break;
                    case "hard":
                        game.generateTarget(); 
                        Thread.sleep(1250);
                        break;
                    default:
                        break;
                }
                game.displayCurrentTime(gameLength);
                gameLength--; 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopClockThread() {
        active = false;
        resumeClockThread(); 
    }

    public void pauseClockThread() {
        paused = true;
    }

    public void resumeClockThread() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}
