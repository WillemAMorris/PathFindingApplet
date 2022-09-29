import java.util.PriorityQueue;
import java.lang.Math;

public class AStar extends PathFindingAlgo {
    private final int STRAIGHT_COST = 10, DIAG_COST = 14; 
    private int[][] costs;

    public AStar(InitialState init) {
        super(init);
        costs = new int[init.getWidth()][init.getHeight()];

    }

    public void calculateCosts()
    {
        // calculate f-costs
        int[][] board = this.getState();
        for (int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++)
            {
                int cost = 0;

                // Calculate f-cost (distance to start)
                int horiDiff = Math.abs(this.startCell[0] - i), vertDiff = Math.abs(this.startCell[1] - j);
                if (horiDiff > vertDiff)
                    cost = vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
                else
                    cost = horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
                
                // Calculate g-cost (distance to end), add it to f-cost
                horiDiff = Math.abs(this.endCell[0] - i); vertDiff = Math.abs(this.endCell[1] - j);
                if (horiDiff > vertDiff)
                    cost += vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
                else
                    cost += horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
                
                costs[i][j] = cost;
            }
    }

    public void update() {

    }
}
