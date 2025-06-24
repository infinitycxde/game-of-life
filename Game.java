import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Game extends JPanel implements ActionListener,KeyListener {

    private static final int NUM_CELLS = 1000; //amount of cells that show up when starting the game
    private static final int UNIT_SIZE = 10;
    private static final int UNITS = 75;
    private static final int DELAY = 75;
    private int xRandomizer,yRandomizer;
    private boolean running = false;
    private Timer timer;
    private int[][] cells; //0 = dead; 1 = alive

    
    public Game() {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(UNIT_SIZE*UNITS,UNIT_SIZE*UNITS));
        this.setBackground(Color.black);
        if(!running)
            startGame();
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        g.setColor(Color.white);
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[0].length; j++) {
                if(cells[i][j]==1) {
                    g.fillRect(UNIT_SIZE*i,UNIT_SIZE*j,UNIT_SIZE,UNIT_SIZE);
                }
            }
        }
        
    }

    public void startGame() {

        running = true;
        cells = new int[UNITS][UNITS];
        for(int i = 0; i < NUM_CELLS; i++) {
            xRandomizer = (int)(Math.random()*UNITS);
            yRandomizer = (int)(Math.random()*UNITS);
            if(cells[xRandomizer][yRandomizer]!=1)
                cells[xRandomizer][yRandomizer] = 1;
            else
                i-=1;
        }
        cells[25][25] = 1;
        cells[26][26] = 1;
        cells[27][27] = 1;
        System.out.println("running");
        timer = new Timer(DELAY,this);
        timer.start();

    }

    public void update() {
        int neighbors;
        int[][] cellsUpdated = cells;
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[0].length; j++) {

                neighbors = 0;
                if(i!=0 && cells[i-1][j]==1) //top
                    neighbors++;
                if(j!=UNITS-1 && cells[i][j+1]==1) //right
                    neighbors++;
                if(i!=UNITS-1 && cells[i+1][j]==1) //bottom
                    neighbors++;
                if(j!=0 && cells[i][j-1]==1) //left
                    neighbors++;
                if(i!=0 && j!=0 && cells[i-1][j-1]==1) //top left
                    neighbors++;
                if(i!=0 && j!=UNITS-1 && cells[i-1][j+1]==1) //top right
                    neighbors++;
                if(i!=UNITS-1 && j!=0 && cells[i+1][j-1]==1) //bottom left
                    neighbors++;
                if(i!=UNITS-1 && j!=UNITS-1 && cells[i+1][j+1]==1) //bottom right
                    neighbors++;
                if(cells[i][j]==1) { //checks for underpopulation/overpopulation
                    if(neighbors<2 || neighbors>3) {
                        cellsUpdated[i][j] = 0;
                    }
                }
                else { //checks for reproduction
                    if(neighbors==3) {
                        cellsUpdated[i][j] = 1;
                    }
                }

            }
        }
        cells = cellsUpdated;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        update();
        repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='r') {
            timer.stop();
            System.out.println("stopped");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new Frame();
    }

}