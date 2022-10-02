public class GraphNode implements Comparable<GraphNode>{
    private int x, y;
    private int cost;
    private int g;
    private int heuristic;
    private GraphNode parent;


    GraphNode(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.cost = Integer.MAX_VALUE;
        this.g = 0;
        this.heuristic = 0;
        this.parent = null;
    }

    GraphNode(int x, int y, int cost, int g, int heuristic, GraphNode parent)
    {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.g = g;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    public boolean equals(GraphNode other)
    {
        if (this.getX() == other.getX() && this.getY() == other.getY())
            return true;
        return false;
    }

    public boolean equals(int[] other)
    {
        if (this.getX() == other[0] && this.getY() == other[1])
            return true;
        return false;
    }

    public int compareTo(GraphNode other)
    {
        if (this.getCost() < other.getCost())
            return -1;
        if (this.getCost() > other.getCost())
            return 1;
        if (this.getHeuristic() < other.getHeuristic())
            return -1;
        if (this.getHeuristic() > other.getHeuristic())
            return 1;
        return 0;
    }

    public int getY()
    {
        return this.y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getCost() 
    {
        return cost;
    }

    public void setCost(int cost) 
    {
        this.cost = cost;
    }

    public int getG() 
    {
        return g;
    }

    public void setG(int g) 
    {
        this.g = g;
    }

    public int getHeuristic() 
    {
        return heuristic;
    }

    public void setHeuristic(int heuristic) 
    {
        this.heuristic = heuristic;
    }

    public GraphNode getParent() 
    {
        return parent;
    }

    public void setParent(GraphNode parent) 
    {
        this.parent = parent;
    }
}
