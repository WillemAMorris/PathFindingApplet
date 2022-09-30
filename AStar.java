import java.util.PriorityQueue;
import java.util.ArrayList;
import java.lang.Math;
import java.awt.Color;
import java.util.Comparator;

public class AStar extends PathFindingAlgo {
    private final int STRAIGHT_COST = 10, DIAG_COST = 14; 
    private PriorityQueue<Node> openNodes;
    private ArrayList<int[]> closedNodes;
    private int stepsTaken;

    private class Node {
        int f, g, h;
        int x, y;

        Node(int x, int y, int f, int g, int h) {
            this.x = x;
            this.y = y;
            this.f = f;
            this.g = g;
            this.h = h;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2)
        {
            if (o1.h < o2.h)
                return -1;
            else if (o1.h > o2.h)
                return 1;
            else if (o1.f < o2.f)
                return -1;
            else if (o1.f < o2.f)
                return 1;
            else
                return 0;
        }
    }

    public AStar(InitialState init) {
        super(init);
        openNodes = new PriorityQueue<Node>(new NodeComparator());
        closedNodes = new ArrayList<int[]>();
        colorKey.add(new Pair("OPEN", Color.ORANGE));
        colorKey.add(new Pair("CLOSED", Color.BLUE));
        int g = gCost(startCell[0], startCell[1]);
        openNodes.add(new Node(startCell[0], startCell[1], g ,0, g));
    }

    public int gCost(int x, int y)
    {
        int horiDiff = Math.abs(endCell[0] - x), vertDiff = Math.abs(endCell[1] - y);
        if (horiDiff > vertDiff)
            return vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
        return horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
    }

    public int fCost(int x, int y)
    {
        int horiDiff = Math.abs(startCell[0] - x), vertDiff = Math.abs(startCell[1] - y);
        if (horiDiff > vertDiff)
            return vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
        return horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
    }

    public void update() {
        ++stepsTaken;
        Node curr = openNodes.poll();
        closedNodes.add(new int[]{curr.x, curr.y});
        int[][] board = this.getState();
        board[curr.x][curr.y] = 5;
        if (curr.x == endCell[0] && curr.y == endCell[1])
        {
            this.reachedTarget = true;
            return;
        }
        for (int i = Math.max(curr.x - 1, 0); i < Math.min(curr.x + 2, width - 1); i++)
            for (int j = Math.max(curr.y - 1, 0); j < Math.min(curr.y + 2, height - 1); j++)
            {
                if (board[i][j] == 1 || board[i][j] == 5) continue;
                int f1 = 0;
                if (curr.x - i == 0 || curr.y - j == 0) 
                    f1 = curr.f + STRAIGHT_COST;
                else 
                    f1 = curr.f + DIAG_COST;
                int f = Math.min(f1, fCost(i, j));
                int g = gCost(i, j);
                board[i][j] = 4;
                openNodes.add(new Node(i, j, f, g, f + g));
            }
        this.history.add(board.clone());
        currState = history.size() - 1;
    }

    public String getStats()
    {
        return "Steps Taken: " + stepsTaken;
    }
}
