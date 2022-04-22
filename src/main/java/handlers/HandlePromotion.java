package handlers;

import pieces.Promotable;
import utils.Coord;

public interface HandlePromotion {
    public void handlePromotion(Promotable promotablePiece, Coord source, Coord destination);
}
