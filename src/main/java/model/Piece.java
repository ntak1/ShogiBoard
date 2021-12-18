package model;


import java.util.List;

public abstract class Piece {
    private Color color;
    private Coord coord;

    public abstract List<Coord> getPossibleMovements();

    public abstract Coord getPosition();

    public abstract void move(Coord coord);
}
