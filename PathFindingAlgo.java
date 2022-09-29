public abstract class PathFindingAlgo {
    protected java.util.Stack<int[][]> history;
    protected int[][] currState;
    protected java.util.ArrayList<Pair> colorKey;
    protected int[] startCell;
    protected int[] endCell;

    public PathFindingAlgo(InitialState init){
        this.currState = init.state;
        this.colorKey = init.colorKey;
        this.startCell = init.start;
        this.endCell = init.end;
        this.history = new java.util.Stack<int[][]>();
        this.history.push(this.currState);
    }
    public void rewindState() 
    { 
        currState = history.pop(); 
    }
    protected void setCellState(int x, int y, int color)
    {
        currState[x][y] = color;
    }

    public java.util.ArrayList<Pair> getColorKey() {
        return colorKey;
    }

    public int[][] getState() {
        return currState;
    }

    abstract void algoRun();
}
