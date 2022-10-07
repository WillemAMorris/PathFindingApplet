import java.util.Stack;
import java.awt.Color;

public class DFS extends PathFindingAlgo {
    private Stack<GraphNode> openNodes;

    DFS(InitialState init)
    {
        super(init);
        openNodes = new Stack<GraphNode>();
        openNodes.add(new GraphNode(startCell[0], startCell[1], null));

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
        GraphNode curr = openNodes.pop();   // Get top priority, open node
        int x = curr.getX();
        int y = curr.getY();
        board[x][y] = 5;  // set curr node to closed
        if (curr.equals(endCell)) // if target is reached, return
        {
            this.drawPath(curr);
            this.reachedTarget = true;
            return;
        }
        for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width); i++)
            for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, height); j++)
            {
                if (board[i][j] == 1 || board[i][j] == 5) continue; // neighbor is a wall or closed
                boolean inside = false;
                GraphNode neighbor = new GraphNode(i, j, curr);
                for (GraphNode n : openNodes)
                    if (n.equals(neighbor))
                    {
                        inside = true;
                        break;
                    }
                if (!inside) {
                    openNodes.push(neighbor);
                    board[neighbor.getX()][neighbor.getY()] = 4;
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
