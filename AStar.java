import java.util.PriorityQueue;
import java.util.Iterator;
import java.lang.Math;
import java.awt.Color;

public class AStar extends PathFindingAlgo {
    private final int STRAIGHT_COST = 10, DIAG_COST = 14; 
    private PriorityQueue<GraphNode> openNodes;

    public AStar(InitialState init) {
        super(init);
        this.addState(super.history.get(super.currState));
        openNodes = new PriorityQueue<GraphNode>(/* GraphNode.getComparator() */);
        int h = heuristic(startCell[0], startCell[1]);
        openNodes.add(new GraphNode(startCell[0], startCell[1], 0 + h, 0, h, null));
        
        colorKey.add(new Pair("OPEN", Color.yellow));
        colorKey.add(new Pair("CLOSED", Color.BLUE));
        colorKey.add(new Pair("PATH", Color.MAGENTA));
        colorKey.add(new Pair("NEXT NODE", Color.CYAN));
    }

    public int heuristic(int x, int y) // distance to end node
    {
        int horiDiff = Math.abs(endCell[0] - x), vertDiff = Math.abs(endCell[1] - y);
        if (horiDiff > vertDiff)
            return vertDiff * DIAG_COST + (horiDiff - vertDiff) * STRAIGHT_COST;
        return horiDiff * DIAG_COST + (vertDiff - horiDiff) * STRAIGHT_COST;
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

    public void update() {
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
        // for each neighbor
        if (this.allowDiagonals)
            for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width); i++)
                for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, height); j++)
                {
                    if (board[i][j] == 1 || board[i][j] == 5) continue; // neighbor is a wall or closed
                    int g = curr.getG();
                    if (i == x || j == y) 
                        g += STRAIGHT_COST;
                    else 
                        g += DIAG_COST;
                    int h = heuristic(i, j);
                    GraphNode neighbor = new GraphNode(i, j, g + h, g, h, curr);
    
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
        else {
            for (int i=-1; i < 2; i+=2)
            {
                int ix = x + i;
                if (ix > -1 && ix < width)
                {
                    if (board[ix][y] != 1 && board[ix][y] != 5)
                    {
                        int g = curr.getG() + STRAIGHT_COST;
                        int h = heuristic(ix,y);
                        GraphNode neighbor = new GraphNode(ix, y, g + h, g, h, curr);
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
                            board[ix][y] = 4;
                        }
                    }
                }
                int iy = y + i;
                if (iy > -1 && iy < height)
                {
                    if (board[x][iy] == 1 || board[x][iy] == 5) continue;
                    int g = curr.getG() + STRAIGHT_COST;
                    int h = heuristic(x,iy);
                    GraphNode neighbor = new GraphNode(x, iy, g + h, g, h, curr);
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
                        board[x][iy] = 4;
                    }
                }
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