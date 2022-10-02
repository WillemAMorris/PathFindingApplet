import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PFDriver extends JFrame
{
    private PFPanel pathfindingPanel;
    private int width, height;

    public PFDriver(String title)
    {
        super(title);
        Dimension screenDimensions = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenDimensions.width;
        this.height = screenDimensions.height;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setResizable(true);
        PFKeyListener keyListener = new PFKeyListener();
        this.addKeyListener(keyListener);
        // Set-up options panel
        JPanel options = new JPanel();
        options.setMaximumSize(new Dimension(this.width, 40));
        options.setBounds(0,0, this.width, 60);
        options.setLayout(new FlowLayout());
        options.addKeyListener(keyListener);

        String[] algorithms = new String[]{"A-Star", "DFS", "BFS", "Dijkstra's"};

        JComboBox<String>algoList = new JComboBox<String>(algorithms);
        JButton runButton = new JButton("Run");
        JButton resetButton = new JButton("Clear Board");
        JButton pauseButton = new JButton("Play");
        
        algoList.addKeyListener(keyListener);
        algoList.setSelectedItem("A-Star");

        runButton.addKeyListener(keyListener);
        runButton.addActionListener(e -> {
            pathfindingPanel.runAlgo((String)(algoList.getSelectedItem()));
            resetButton.setText("Reset Algo");
            //pauseButton.setEnabled(true);
        });

        resetButton.addKeyListener(keyListener);
        resetButton.addActionListener(e -> {
            if (resetButton.getText() == "Reset Algo") {
                pathfindingPanel.resetAlgo();
                resetButton.setText("Clear Board");
            }
            else {
                pathfindingPanel.clearInitialState();
            }
            pathfindingPanel.repaint();
        });

        pauseButton.addKeyListener(keyListener);
        pauseButton.addActionListener(e -> {
            pathfindingPanel.togglePause();
            if (pauseButton.getText() == "Pause")
                pauseButton.setText("Play");
            else
                pauseButton.setText("Pause");
        });

        options.add(new JLabel("Algorithm"));
        options.add(algoList);
        options.add(Box.createRigidArea(new Dimension(10,0)));
        options.add(runButton);
        options.add(Box.createRigidArea(new Dimension(10,0)));
        options.add(resetButton);
        options.add(Box.createRigidArea(new Dimension(10,0)));
        options.add(pauseButton);

        pathfindingPanel = new PFPanel(this.width, this.height - options.getHeight(), pauseButton);
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
        PFDriver driver = new PFDriver("Path Finding Applet");
        
        driver.setVisible(true);
    }

    private class PFKeyListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            pathfindingPanel.keyPressed(e.getKeyCode());
        }
        public void keyReleased(KeyEvent e) {
        }
        public void keyTyped(KeyEvent e){
        }
    } 
}