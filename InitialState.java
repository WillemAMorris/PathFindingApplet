import java.awt.Color;

public class InitialState {
    public int[][] state;
    public int width, height;
    public boolean startInit;
    public int[] start;
    public boolean endInit;
    public int[] end;
    public java.util.ArrayList<Pair> colorKey;

    public InitialState(int width, int height)
    {
        this.colorKey = new java.util.ArrayList<Pair>();
        colorKey.add(new Pair("EMPTY", Color.GRAY));
        colorKey.add(new Pair("WALL", Color.BLACK));
        colorKey.add(new Pair("START", Color.GREEN)); 
        colorKey.add(new Pair("END", Color.RED));

        this.width = width;
        this.height = height;
        this.state = new int[width][height];
        this.start = new int[2];
        this.end = new int[2];
    }

    public void setEmpty(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        this.state[x][y] = 0;
    }

    public void setWall(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        this.state[x][y] = 1;
    }

    public void setStart(int x, int y)
    {
        this.setEmpty(start[0], start[1]);
        /* if (end[0] == x && end[1] == y) {
            end[0] = -1;
            end[1] = -1;
        } */
        this.state[x][y] = 2;
        this.start[0] = x;
        this.start[1] = y;
    }

    public void setEnd(int x, int y)
    {
        // set previous square to empty
        // if sq is start, unset start
        // set state[x][y] to 3
        // set end to x and y
        this.setEmpty(end[0], end[1]);
        /* if (start[0] == x && start[1] == y) {
            start[0] = -1;
            start[1] = -1;
        } */
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
