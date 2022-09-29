import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PFDriver extends JFrame
{
    private boolean paused;
    private PFPanel pathfindingPanel;
    private int width, height;

    public PFDriver(String title)
    {
        super(title);
        Dimension screenDimensions = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenDimensions.width;
        this.height = screenDimensions.height;
        this.paused = false;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setResizable(true);
        PFKeyListener keyListener = new PFKeyListener();
        this.addKeyListener(keyListener);
        // Set-up options panel
        JPanel options = new JPanel();
        options.setSize(this.width, 50);
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        /*
         * - Algorithm drop down menu
         * - Run button
         * - pause/play button
         * - Reset/Clear button
         * - step forward button
         * - step backward button
         */
        options.add(new JButton("Test"));
        options.addKeyListener(keyListener);
        System.out.println("Option Dimensions: " + options.getWidth() + " by " + options.getHeight());

        // Set-up PFPanel
        pathfindingPanel = new PFPanel(this.width, this.height - options.getHeight());
        pathfindingPanel.addKeyListener(keyListener);

        // Set-up main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(pathfindingPanel);
        mainPanel.add(options);
        mainPanel.addKeyListener(keyListener);
        this.add(mainPanel);
    }

    public static void main(String[] args)
    {
        PFDriver driver = new PFDriver("A* Algorithm");
        
        driver.setVisible(true);
    }        

    public boolean isPaused()
    {
        return paused;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    private class PFKeyListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                System.exit(1);
            pathfindingPanel.keyPressed(e.getKeyCode());
        }
        public void keyReleased(KeyEvent e)
        {

        }
        public void keyTyped(KeyEvent e)
        {

        }
    } 
}