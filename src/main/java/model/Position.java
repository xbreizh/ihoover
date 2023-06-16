package model;

public class Position {

    private final int x;
    private final int y;
    private final Orientation orientation;

    public Position(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Orientation getOrientation() {
        return orientation;
    }


    @Override
    public String toString() {
        return "model.Position{" +
            "x=" + x +
            ", y=" + y +
            ", orientation='" + orientation.getName() + '\'' +
        '}';
    }
}
