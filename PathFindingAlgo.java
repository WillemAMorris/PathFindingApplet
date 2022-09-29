public abstract class PathFindingAlgo {
    protected java.util.ArrayList<int[][]> history;
    protected int currState;
    public java.util.ArrayList<Pair> colorKey;
    protected int[] startCell;
    protected int[] endCell;

    public PathFindingAlgo(InitialState init){
        this.currState = 0;
        this.colorKey = init.colorKey;
        this.startCell = init.start;
        this.endCell = init.end;
        this.history = new java.util.ArrayList<int[][]>();
        this.history.add(init.state);
    }
    public void rewindState() 
    { 
        if (currState > 0)
            --currState; 
    }

    public void advanceState()
    {
        if (currState < history.size() - 1)
            ++currState;
    }

    protected void setCellState(int x, int y, int color)
    {
        history.get(currState)[x][y] = color;
    }

    public int[][] getState() {
        return history.get(currState);
    }

    abstract void update();
}
