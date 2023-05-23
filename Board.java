import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public  class Board  extends JPanel implements ActionListener{


        static final int SCREEN_WIDTH = 600;
        static final int SCREEN_HEIGHT = 600;

        static final int UNIT_SIZE = 25;//object size
        static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT) /UNIT_SIZE;//object number
        static final int DELAY = 75;

        final int X[] = new int[GAME_UNIT];
        final int Y[] = new int[GAME_UNIT];//snake never higher than size
        int bodyParts= 6;//6 body part of snake
        int appleEaten;

        int apple_X;// x cordinate of apple
        int apple_Y;

        char direction ='R';
        boolean  running = false;

        Timer timer ;
        Random rand;
       
    Board(){

                rand = new Random();

                this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
                this.setBackground(Color.GREEN);
                this.setFocusable(true);
                this.addKeyListener(new keyadapeter());   
                startGame();
        }

        public void startGame(){

            new_apple();
            running = true;
            timer = new Timer(DELAY,this);
            timer.start();

        }

        public void paintComponent(Graphics g){

            super.paintComponent(g);
            draw(g);

        }

        public void draw(Graphics g){
            
            if (running) {
                   /*      
                for (int i = 0; i <SCREEN_HEIGHT/UNIT_SIZE; i++) {

                    g.drawLine(i*UNIT_SIZE, 0,i*UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH, i*UNIT_SIZE);

                }
                */
                g.setColor(Color.red);
                g.fillOval(apple_X, apple_Y, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {

                    if (i == 0) {

                        g.setColor(Color.blue);
                        g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);

                    }
                    else{

                        g.setColor(new Color(0, 0, 255));
                        //g.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
                        g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }

             }
            else{
                gameOver(g);
            }
            g.setColor(Color.red);
            g.setFont(new Font(" SansSerif",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:"+appleEaten, (SCREEN_WIDTH-metrics.stringWidth("Score:"+appleEaten))/2,g.getFont().getSize());
            
        }
        
        public void new_apple(){

                apple_X = rand.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
                apple_Y = rand.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
                
            }

        public void move(){
            for (int i = bodyParts; i >0; i--) {
                
                X[i]=X[i-1];
                Y[i]=Y[i-1];
                
            }
            switch (direction) {
                
            case 'U':
                    
            Y[0] = Y[0] -UNIT_SIZE;
                    
            break;
                    
            case 'D':
                    
            Y[0] = Y[0] + UNIT_SIZE;
                    
            break;
                    
            case 'L':
                    
            X[0] = X[0] -UNIT_SIZE;
                    
            break;
                    
            case 'R':
                    
            X[0] = X[0] +UNIT_SIZE;
                    
            break;                                    
            }

        }

        public void checkApple(){
            if ((X[0] == apple_X) && (Y[0] == apple_Y)) {
                
                bodyParts++;
                appleEaten++;
                new_apple();
            }

        }

        public void check_collisions(){
            
            //check if head collides with body
            for (int i = bodyParts; i >0; i--) {
                
                    if ((X[0] == X[i]) && (Y[0] == Y[i])) {

                        running = false;

                    }
                
            }
            
            //check if head touches left border
            if (X[0] < 0) {
                
                running = false;
                
            }
            
            // check if head touches right border 
             if (X[0] > SCREEN_WIDTH) {
                
                running = false;
                
            }
             //check to see if head touches right border 
              if (Y[0] < 0) {
                
                running = false;
                
            }
            //check if head touches bottum border
             if (Y[0] > SCREEN_HEIGHT) {
                
                running = false;
                
            }
            if(!running){//if running is false 
                
                timer.stop();
                
            }

        }

        public void gameOver(Graphics g){
            
            //score
            g.setColor(Color.red);
            g.setFont(new Font(" SansSerif",Font.BOLD,75));
            FontMetrics score = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIDTH-score.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
            
            //gameover test
            g.setColor(Color.red);
            g.setFont(new Font(" SansSerif",Font.BOLD,75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
            

        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (running) {
                     
                    move();
                    checkApple();
                    check_collisions();
                }
                repaint();
        }
        public class keyadapeter extends KeyAdapter{
            
            @Override
            public void keyPressed(KeyEvent e){
                
                            switch (e.getKeyCode()) {
                       
                            case KeyEvent.VK_LEFT:
                           
                                if (direction != 'R') {
                               
                                direction = 'L';  
                               
                            }
                           
                            break;
                           
                            case KeyEvent.VK_RIGHT:
                           
                                if (direction != 'L') {
                               
                                    direction = 'R';  
                               
                            }
                            break;
                            
                            
                            case KeyEvent.VK_UP:
                           
                                if (direction != 'D') {
                               
                                     direction = 'U';  
                               
                           }
                           break;
                           
                           case KeyEvent.VK_DOWN:
                           
                                if (direction != 'U') {
                               
                                     direction = 'D';  
                               
                           }
                           break;
                       
                   }

            }
         } 
}
