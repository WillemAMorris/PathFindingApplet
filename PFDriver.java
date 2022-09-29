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
        options.setBounds(0,0, this.width, 60);
        options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
        options.addKeyListener(keyListener);
        
        pathfindingPanel = new PFPanel(this.width, this.height - options.getHeight());
        pathfindingPanel.addKeyListener(keyListener);
        /*  Set up Options buttons
         * - Algorithm drop down menu
         * - Run button
         * - pause/play button
         * - Reset/Clear button
         * - step forward button
         * - step backward button
         */
        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> pathfindingPanel.runAlgo("A-Star"));
        //runButton.setMargin(new java.awt.Insets(5,5,5,5));
        runButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        runButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        runButton.addKeyListener(keyListener);
        options.add(runButton);
        options.add(Box.createRigidArea(new Dimension(10,0)));
        options.add(new JButton("Clear"));

        // Set-up PFPanel


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