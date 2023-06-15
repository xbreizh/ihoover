public enum Orientation {
    N("NORTH", "W", "E"),
    E("EAST", "N", "S"),
    W("WEST","S", "S"),
    S("SOUTH", "E", "W");

    private String name;
    private String left;
    private String right;

    Orientation(String initial, String left, String right) {
        this.name = initial;
        this.left = left;
        this.right = right;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
