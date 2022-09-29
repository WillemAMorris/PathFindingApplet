import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PFPanel extends JPanel {
    // Debugging tools
    private java.util.ArrayList<String> EventLog;
    private boolean showDebug;
    private int history;

    // Variables for rendering the panel to the screen
    private boolean runningAlgo;
    private InitialState initState;
    private PathFindingAlgo algorithm;
    private java.util.ArrayList<Pair> colorLegend;

    
    private int[][] initialState;
    private int[] currCell;
    private int[] startCell;
    private int[] endCell;
    private Rectangle panelDimensions;
    private Point mousePos;
    private int cellsize;

    // Variables for controlling the pathfinding algorithm
    private final int STRAIGHT_COST = 10, DIAGONAL_COST = 14;

    private PFDriver driver;
    private PFMouseListener mouseListener;


    public PFPanel(PFDriver driver, int pW, int pH)
    {
        // Debugging
        this.EventLog = new ArrayList<String>();
        this.history = 20;
        this.showDebug = true;
        
        this.driver = driver;
        this.mousePos = new Point();
        // Rendering
        this.currCell = new int[]{0,0};
        this.startCell = new int[]{0,0};
        this.endCell = new int[]{0,0};
        this.panelDimensions = new Rectangle(this.getX(), this.getY(), pW, pH);
        this.cellsize = 50;
        
        // Algorithm
        clearinitialState();
        this.runningAlgo = false;


        mouseListener = new PFMouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    private void clearinitialState()
    {
        initState = new InitialState(panelDimensions.width / cellsize - 3, panelDimensions.height / cellsize);
        panelDimensions.setSize(initialState.length * cellsize, initialState[0].length * cellsize);
    }

    private void renderInitState(Graphics g)
    {
        for (int i = 0; i < initState.getWidth(); i++)
            for (int j = 0; j < initState.getHeight(); j++)
            {
                g.setColor(initState.getCell(i, j).getColor());
                g.fillRect(i * cellsize, j * cellsize, cellsize, cellsize);
            }
    }

    private void renderAlgo(Graphics g)
    {
        int[][] board = algorithm.getState();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
            {
                g.setColor(colorLegend.get(board[i][j]).getColor());
                g.fillRect(i * cellsize, j * cellsize, cellsize, cellsize);
            }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // draw start and end cell

        // draw the rest of the cells
        // draw the current cell
        if (runningAlgo)
            this.renderAlgo(g);
        else
            this.renderInitState(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        int count = 0;
        for (int i=EventLog.size() - 1; i>=0 && i > EventLog.size() - history; i--)
        {
            g.drawString(EventLog.get(i), 20, 20 + 20 * (count++));
        }
    }

    public void keyPressed(int code)
    {
        if (!runningAlgo)
        {
            switch (code)
            {
                case KeyEvent.VK_1:
                EventLog.add("One Key Pressed! Setting Start Point!");
                startCell[0] = currCell[0];
                startCell[1] = currCell[1];
                break;
                case KeyEvent.VK_2:
                EventLog.add("Two Key Pressed! Setting End Point!");
                endCell[0] = currCell[0];
                endCell[1] = currCell[1];
                break;
                case KeyEvent.VK_B:
                showDebug = !showDebug;
                break;
                case KeyEvent.VK_R:
                runningAlgo = true;
                // Pass initState to algorithm constructor based on field in algorithm option
                break;
                
            }
            repaint();
        }
        else
        {

        }
            
    }

    private class PFMouseListener implements MouseListener, MouseMotionListener
    {
        public boolean leftClicked;
        public boolean rightClicked;

        @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {        
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        
        mousePos = MouseInfo.getPointerInfo().getLocation();
        if (panelDimensions.contains(mousePos))
        {
            currCell[0] = (int)(mousePos.getX() / cellsize);
            currCell[1] = (int)(mousePos.getY() / cellsize);
        }
        if (mouseListener.leftClicked)
        {
            initialState[currCell[0]][currCell[1]] = 1;
        }
        if (mouseListener.rightClicked)
        {
            initialState[currCell[0]][currCell[1]] = 0;
        }
        repaint();
    }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            switch (e.getButton())
            {
                case java.awt.event.MouseEvent.BUTTON1:
                EventLog.add("Mouse One Pressed!");
                initialState[currCell[0]][currCell[1]] = 1;
                this.leftClicked = true;
                break;
                case java.awt.event.MouseEvent.BUTTON3:
                EventLog.add("Mouse Three Pressed!");
                initialState[currCell[0]][currCell[1]] = 0;
                this.rightClicked = true;
                break;
            } 
            repaint();
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            switch (e.getButton())
            {
                case java.awt.event.MouseEvent.BUTTON1:
                this.leftClicked = false;
                break;
                case java.awt.event.MouseEvent.BUTTON3:
                this.rightClicked = false;
            } 
            repaint();
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {}
    }


}
