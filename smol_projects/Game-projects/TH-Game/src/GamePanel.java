import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // screen settings
    final int originalTileSize = 16; // 16 x 16 title
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48 x 48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    // 16 tiles horizontally // 12 tiles vertically resulting into a ratio of 4 x 3
    final int screenWidth = tileSize * maxScreenCol; // 768 pixel
    final int screenHeight = tileSize * maxScreenRow; // 576 pixel

    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //Player Position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // if DoubleBuffered set true all drawing from this component will be done in
        // an offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
           // System.out.println("Game loop is running");
            /*
            Why use gameThread implementation?
               1.) Update: para mag sige og update og information pariha anang character positioning

               2.) Draw: maka render siya og unsay naa sa screen

                   like if your character is in coordinates like X: 100, Y:100
                   when you press down keyboard the character will change it's position.
                   and if mo hold siya sa down key ang coordinates be like:
                   100 -> 105 -> 110 -> 115 -> 120
             */



            update();
            repaint();  // how you call the paintComponent


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        if (keyH.upPressed == true){
            playerY -= playerSpeed;
        }
        else if(keyH.downPressed == true){
            playerY += playerSpeed;
        }
        else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
        }
        else if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        // the Graphics class (Graphics g) has many functions to draw objects on screen
        // whenever you use this function always use super.PaintComponent(g)
        // because gamePanel class is just a subclass of Jpanel
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        //we use Graphics2d because its just a subclass of Graphics and a little bit more specific

        g2.setColor(Color.white);

        g2.fillRect(playerX,playerY, tileSize, tileSize);


        g2.dispose(); //Dispose: Dispose of this graphics context and release any system resource that is using
        //and save memory


    }
}
