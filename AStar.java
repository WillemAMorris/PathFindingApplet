import java.util.PriorityQueue;
import java.util.Iterator;
import java.lang.Math;
import java.awt.Color;
import java.util.Comparator;

public class AStar extends PathFindingAlgo {
    private final int STRAIGHT_COST = 10, DIAG_COST = 14; 
    private PriorityQueue<Node> openNodes;

    private class Node{
        Node parent;
        int f, g, h; // g is distance to start, h is distance to end, f = g + h
        int x, y;

        Node(int x, int y, int f, int g, int h, Node p) {
            this.x = x;
            this.y = y;
            this.f = f;
            this.g = g;
            this.h = h;
            this.parent = p;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2)
        {
            if (o1.f < o2.f)
                return -1;
            else if (o1.f > o2.f)
                return 1;
            else if (o1.h < o2.h)
                return -1;
            else if (o1.h < o2.h)
                return 1;
            else
                return 0;
        }
    }

    public AStar(InitialState init) {
        super(init);
        this.addState(super.history.get(super.currState));
        openNodes = new PriorityQueue<Node>(new NodeComparator());
        colorKey.add(new Pair("OPEN", Color.yellow));
        colorKey.add(new Pair("CLOSED", Color.BLUE));
        colorKey.add(new Pair("PATH", Color.MAGENTA));
        colorKey.add(new Pair("NEXT NODE", Color.CYAN));
        int h = hCost(startCell[0], startCell[1]);
        openNodes.add(new Node(startCell[0], startCell[1], 0 + h ,0, h, null));
    }

    public int hCost(int x, int y) // distance to end node
    {
        int horiDiff = Math.abs(endCell[0] - x), vertDiff = Math.abs(endCell[1] - y);
        if (horiDiff > vertDiff)
            return vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
        return horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
    }

    public int gCost(int x, int y) // distance to start node
    {
        int horiDiff = Math.abs(startCell[0] - x), vertDiff = Math.abs(startCell[1] - y);
        if (horiDiff > vertDiff)
            return vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
        return horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
    }

    public void drawPath(Node startNode)
    {
        Node currNode = startNode;
        int[][] board = this.getState();
        while (currNode != null)
        {
            board[currNode.x][currNode.y] = 6;
            currNode = currNode.parent;
        }
        this.addState(board);
    }

    public void update() {
        int[][] board = this.getState(); 
        Node curr = openNodes.poll();   // Get top priority, open node
        board[curr.x][curr.y] = 5;  // set curr node to closed
        if (curr.x == endCell[0] && curr.y == endCell[1]) // if target is reached, return
        {
            this.drawPath(curr);
            this.reachedTarget = true;
            return;
        }
        // for each neighbor
        for (int i = Math.max(curr.x - 1, 0); i < Math.min(curr.x + 2, width - 1); i++)
            for (int j = Math.max(curr.y - 1, 0); j < Math.min(curr.y + 2, height - 1); j++)
            {
                if (board[i][j] == 1 || board[i][j] == 5) continue; // neighbor is a wall or closed
                int g = 0;
                if (curr.x - i == 0 || curr.y - j == 0) 
                    g = curr.g + STRAIGHT_COST;
                else 
                    g = curr.g + DIAG_COST;
                int h = hCost(i, j);
                Node neighbor = new Node(i, j, g + h, g, h, curr);
 
                boolean inside = false;
                Iterator<Node> it = openNodes.iterator();
                while (it.hasNext())
                {
                    Node n = it.next();
                    if (n.x == neighbor.x && n.y == neighbor.y)
                    {
                        if (n.f > neighbor.f) {
                            openNodes.remove(n);
                            openNodes.add(neighbor);
                        }
                        inside = true;
                        break;
                    }
                }
                if (!inside) {
                    openNodes.add(neighbor);
                    board[i][j] = 4;
                }
            }
        Node next = openNodes.peek();
        board[next.x][next.y] = 7;
        this.addState(board);
    }
}
