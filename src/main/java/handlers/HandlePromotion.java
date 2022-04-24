package handlers;

import exception.InvalidPieceException;
import pieces.Promotable;
import utils.Coord;

public interface HandlePromotion {
    void handlePromotion(Promotable promotablePiece, Coord source, Coord destination) throws InvalidPieceException;
}
