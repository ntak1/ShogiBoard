package model.pieces;

import model.Coord;

public interface Promotable {
    boolean canPromote(Coord source, Coord destination);

    boolean shouldPromote(Coord source, Coord destination);
}
