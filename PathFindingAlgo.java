import java.awt.Color;

public abstract class PathFindingAlgo {
    protected java.util.Stack<int[][]> history;
    protected int[][] currState;
    protected java.util.ArrayList<Pair> colorKey;
    protected int[] startCell;
    protected int[] endCell;

    public PathFindingAlgo(InitialState init){
        this.currState = init.getState();
        this.colorKey = init.getColorKey();
        this.startCell = init.getStart();
        this.endCell = init.getEnd();
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
