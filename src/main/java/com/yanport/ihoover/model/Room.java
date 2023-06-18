package com.yanport.ihoover.model;

public class Room {

    private int x;
    private int y;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Board{" +
            "longueur x= " + x +
            ", largeur y= " + y +
        '}';
    }
}
