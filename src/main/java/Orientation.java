public enum Orientation {
    N("NORTH", "W", "E"),
    E("EAST", "N", "S"),
    W("WEST","S", "S"),
    S("SOUTH", "E", "W");

    private final String name;
    private final String left;
    private final String right;

    Orientation(String initial, String left, String right) {
        this.name = initial;
        this.left = left;
        this.right = right;
    }


    public String getName() {
        return name;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
