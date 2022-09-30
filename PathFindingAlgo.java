public abstract class PathFindingAlgo {
    protected boolean reachedTarget;
    protected java.util.ArrayList<int[][]> history;
    protected int currState;
    protected java.util.ArrayList<Pair> colorKey;
    protected int[] startCell;
    protected int[] endCell;
    protected int width, height;

    public PathFindingAlgo(InitialState init){
        this.currState = 0;
        this.colorKey = init.getColorKey();
        this.startCell = init.getStart();
        this.endCell = init.getEnd();
        this.reachedTarget = false;
        this.width = init.getWidth();
        this.height = init.getHeight();
        this.history = new java.util.ArrayList<int[][]>();
        this.history.add(init.getState());
    }
    public void rewindState() 
    { 
        if (currState > 0)
            --currState; 
    }

    public void advanceState()
    {
        if (currState == history.size() - 1)
            this.update();
        else if (currState < history.size() - 1)
            ++currState;
    }

    public int[][] getState() {
        return history.get(currState);
    }

    public java.util.ArrayList<Pair> getColorKey() {
        return colorKey;
    }

    public boolean reachedTarget() {
        return reachedTarget;
    }

    abstract String getStats();

    abstract void update();
}
