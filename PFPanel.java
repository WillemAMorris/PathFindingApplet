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

    private int edgeWidth;
    private int[] currCell;
    private Point mousePos;
    private int cellsize;

    private PFMouseListener mouseListener;


    public PFPanel(int pW, int pH)
    {
        // Debugging
        this.EventLog = new ArrayList<String>();
        this.history = 20;
        this.showDebug = true;
        
        // Rendering
        this.currCell = new int[]{0,0};
        this.cellsize = 40;
        this.edgeWidth = 2;
        
        // Algorithm
        this.setLocation(0,0);
        this.setSize(pW, pH);
        initState = new InitialState(this.getWidth() / cellsize, this.getHeight() / cellsize);
        this.runningAlgo = false;
        this.algorithm = null;


        this.mousePos = new Point(); // Keeps track of mouse position
        mouseListener = new PFMouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    public void clearInitialState()
    {
        if (!runningAlgo) {
            initState = new InitialState(this.getWidth() / cellsize, this.getHeight() / cellsize);
            Log("Clearing...");
            repaint();
        }
    }

    private void renderInitState(Graphics g)
    {
        int[][] board = initState.getState();
        ArrayList<Pair> key = initState.getColorKey();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
            {
                g.setColor(key.get(board[i][j]).getColor());
                g.fillRect(i * cellsize + edgeWidth, j * cellsize + edgeWidth, cellsize - 2 * edgeWidth, cellsize - 2 * edgeWidth);
            }
    }

    private void renderAlgo(Graphics g)
    {
        int[][] board = algorithm.getState();
        ArrayList<Pair> key = algorithm.getColorKey();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
            {
                g.setColor(key.get(board[i][j]).getColor());
                g.fillRect(i * cellsize + edgeWidth, j * cellsize + edgeWidth, cellsize - 2 * edgeWidth, cellsize - 2 * edgeWidth);
            }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

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
        if (code == KeyEvent.VK_ESCAPE) {
            if (algorithm != null)
                System.out.println(algorithm.getStats());
            System.exit(1);
        }
        if (!runningAlgo)
        {
            switch (code)
            {
                case KeyEvent.VK_1:
                    Log("One Key Pressed! Setting Start Point!");
                    initState.setStart(currCell[0], currCell[1]);
                    break;
                case KeyEvent.VK_2:
                    Log("Two Key Pressed! Setting End Point!");
                    initState.setEnd(currCell[0], currCell[1]);
                    break;
                case KeyEvent.VK_B:
                    showDebug = !showDebug;
                    break;
            }
            repaint();
        }
        else
        {
            switch (code)
            {
                case KeyEvent.VK_RIGHT:
                    Log("Updating board");
                    algorithm.advanceState();
                    break;
                case KeyEvent.VK_LEFT:
                    Log("Rewinding board");
                    algorithm.rewindState();
                    break;
            }
            repaint();
        }
            
    }

    public void resetAlgo()
    {
        if (runningAlgo)
        {
            runningAlgo = false;
            algorithm = null;
            Log("Resetting...");
            repaint();
        }
        
    }

    public void runAlgo(String algoName)
    {
        if (!initState.readyToSend()) {
            Log("Running Path Finding Algorithm: " + algoName);
            runningAlgo = true;
            // Initialize algorithm
            switch(algoName)
            {
                case "A-Star":
                    algorithm = new AStar(initState);
                    break;
            }
        }
        else
        {
            Log("Start/End Point Not Set!");
        }
        repaint();
    }

    public void Log(String message) {
        EventLog.add(message);
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
            
            // Gets the absolute mouse position: MouseInfo.getPointerInfo().getLocation();
            mousePos.x = e.getX(); 
            mousePos.y = e.getY();

            if (getBounds().contains(mousePos))
            {
                currCell[0] = (int)(mousePos.getX() / cellsize);
                currCell[1] = (int)(mousePos.getY() / cellsize);
            }
            if (mouseListener.leftClicked)
            {
                initState.setWall(currCell[0], currCell[1]);
            }
            if (mouseListener.rightClicked)
            {
                initState.setEmpty(currCell[0], currCell[1]);
            }
            repaint();
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            if (!runningAlgo)
            {
                switch (e.getButton())
                {
                    case java.awt.event.MouseEvent.BUTTON1:
                    Log("Mouse One Pressed!");
                    Log("Setting Wall: " + currCell[0] + ", " + currCell[1]);
                    initState.setWall(currCell[0], currCell[1]);
                    this.leftClicked = true;
                    break;
                    case java.awt.event.MouseEvent.BUTTON3:
                    Log("Mouse Three Pressed!");
                    Log("Setting Empty: " + currCell[0] + ", " + currCell[1]);
                    initState.setEmpty(currCell[0], currCell[1]);
                    this.rightClicked = true;
                    break;
                } 
                repaint();
            }
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            if (!runningAlgo)
            {
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
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {}
    
    }

}
