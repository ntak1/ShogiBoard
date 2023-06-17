package model.pieces;

import model.Coord;
import model.PieceName;

import java.util.Set;

public class PromotedKnight extends Gold {
    public PromotedKnight(PieceColor color) {
        super(color);
        pieceName = PieceName.PROMOTED_KNIGHT;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        return super.getStandardMovements(coord);
    }

}
