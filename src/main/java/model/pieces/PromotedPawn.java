package model.pieces;

import model.Coord;
import model.PieceName;

import java.util.Set;

public class PromotedPawn extends Gold {
    public PromotedPawn(PieceColor color) {
        super(color);
        pieceName = PieceName.PROMOTED_PAWN;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        return super.getStandardMovements(coord);
    }

}
