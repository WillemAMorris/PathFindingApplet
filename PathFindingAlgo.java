public abstract class PathFindingAlgo {
    protected boolean reachedTarget;
    protected boolean allowDiagonals;
    protected java.util.ArrayList<int[][]> history;
    protected int currState;
    protected java.util.ArrayList<Pair> colorKey;
    protected int[] startCell;
    protected int[] endCell;
    protected int width, height;

    public PathFindingAlgo(InitialState init){
        this.colorKey = init.getColorKey();
        this.startCell = init.getStart();
        this.endCell = init.getEnd();
        this.reachedTarget = false;
        this.allowDiagonals = true;
        this.width = init.getWidth();
        this.height = init.getHeight();
        this.currState = 0;
        this.history = new java.util.ArrayList<int[][]>();
        this.addState(init.getState());
    }

    private int[][] copy(int[][] board)
    {
        int[][] b = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                b[i][j] = board[i][j];
        return b;
    }

    public void addState(int[][] board)
    {
        this.history.add(copy(board));
        currState = history.size() - 1;
    }

    public void rewindState() 
    { 
        if (currState > 0)
            --currState; 
    }

    public void advanceState()
    {
        if (currState == history.size() - 1 && !this.reachedTarget)
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

    public void setAllowDiagonals(boolean aD) {
        allowDiagonals = aD;
    }

    abstract void update();
}
