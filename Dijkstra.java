import java.util.Iterator;
import java.util.PriorityQueue;
import java.awt.Color;

public class Dijkstra extends PathFindingAlgo {
    private final int STRAIGHT_COST = 10, DIAG_COST = 14; 
    private PriorityQueue<GraphNode> openNodes;

    Dijkstra(InitialState init)
    {
        super(init);
        openNodes = new PriorityQueue<GraphNode>();
        openNodes.add(new GraphNode(startCell[0], startCell[1], 0, 0, 0, null));
        
        colorKey.add(new Pair("OPEN", Color.yellow));
        colorKey.add(new Pair("CLOSED", Color.BLUE));
        colorKey.add(new Pair("PATH", Color.MAGENTA));
        colorKey.add(new Pair("NEXT NODE", Color.CYAN));
    }

    public void drawPath(GraphNode startNode)
    {
        GraphNode currNode = startNode;
        int[][] board = this.getState();
        while (currNode != null)
        {
            board[currNode.getX()][currNode.getY()] = 6;
            currNode = currNode.getParent();
        }
        this.addState(board);
    }

    public void update()
    {
        if (openNodes.isEmpty())
            return;
        int[][] board = this.getState(); 
        GraphNode curr = openNodes.poll();   // Get top priority, open node
        int x = curr.getX();
        int y = curr.getY();
        board[x][y] = 5;  // set curr node to closed
        if (curr.equals(endCell)) // if target is reached, return
        {
            this.drawPath(curr);
            this.reachedTarget = true;
            return;
        }
        for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width - 1); i++)
            for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, height - 1); j++)
            {
                if (board[i][j] == 1 || board[i][j] == 5) continue; // neighbor is a wall or closed
                int cost = curr.getCost();
                if (x - i == 0 || y - j == 0) 
                    cost += STRAIGHT_COST;
                else 
                    cost += DIAG_COST;
                GraphNode neighbor = new GraphNode(i, j, cost, 0, 0, curr);

                boolean inside = false;
                Iterator<GraphNode> it = openNodes.iterator();
                while (it.hasNext())
                {
                    GraphNode n = it.next();
                    if (n.equals(neighbor))
                    {
                        if (n.getCost() > neighbor.getCost()) {
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
    
        if (!openNodes.isEmpty())
        {
            GraphNode next = openNodes.peek();
            board[next.getX()][next.getY()] = 7;
        }
        this.addState(board);
    }
}
