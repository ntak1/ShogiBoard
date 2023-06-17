package model.pieces;

import model.Coord;

import java.util.Set;

public interface Move {
    Set<Coord> getPossibleMovements(Coord coord);

    void setCaptured(boolean b);

    boolean isCaptured();
}
