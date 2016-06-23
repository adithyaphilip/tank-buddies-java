
public class Player {
    private final int mId;
    private int mX;
    private int mY;
    private int mDirection;

    public Player(int id, int x, int y, int direction) {
        mId = id;
        mX = x;
        mY = y;
        mDirection = direction;
    }

    public int getId() {
        return mId;
    }

    public int getX() {
        return mX;
    }

    public void setXY(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public int getY() {
        return mY;
    }

    public int getDirection() {
        return mDirection;
    }

    public void setDirection(int direction) {
        this.mDirection = direction;
    }
}
