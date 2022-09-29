import java.awt.Color;

public class InitialState {
    private int[][] state;
    private boolean startInit;
    private int[] start;
    private boolean endInit;
    private int[] end;
    private java.util.ArrayList<Pair> colorKey;

    public InitialState(int width, int height)
    {
        this.colorKey = new java.util.ArrayList<Pair>();
        colorKey.add(new Pair("EMPTY", Color.GRAY));
        colorKey.add(new Pair("WALL", Color.BLACK));
        colorKey.add(new Pair("START", Color.GREEN)); 
        colorKey.add(new Pair("END", Color.RED));

        this.state = new int[width][height];
        this.startInit = false;
        this.start = new int[2];
        this.endInit = false;
        this.end = new int[2];
    }

    public void clearCell(int x, int y)
    {
        if (state[x][y] == 2)
            startInit = false;
        else if (state[x][y] == 3)
            endInit = false;
        this.state[x][y] = 0;
    }

    public void setWall(int x, int y)
    {
        if (state[x][y] == 2)
            startInit = false;
        else if (state[x][y] == 3)
            endInit = false;
        this.state[x][y] = 1;
    }

    public void setStart(int x, int y)
    {
        this.startInit = true;
        if (state[x][y] == 3)
            endInit = false;
        this.state[x][y] = 2;
        this.start[0] = x;
        this.start[1] = y;
        
    }

    public void setEnd(int x, int y)
    {
        this.endInit = true;
        if (state[x][y] == 2)
            startInit = false;
        this.state[x][y] = 3;
        this.end[0] = x;
        this.end[1] = y;
    }

    public Pair getCell(int x, int y)
    {
        return this.colorKey.get(state[x][y]);
    }

    public java.util.ArrayList<Pair> getColorKey()
    {
        return this.colorKey;
    }

    public int getWidth()
    {
        return state.length;
    }

    public int getHeight()
    {
        return state[0].length;
    }

    public int[][] getState()
    {
        return this.state;
    }

    public int[] getStart()
    {
        return this.start;
    }

    public int[] getEnd()
    {
        return this.end;
    }

    public boolean readyToSend()
    {
        return (startInit && endInit);
    }
}
