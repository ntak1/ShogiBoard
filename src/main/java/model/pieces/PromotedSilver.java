package model.pieces;

import model.Coord;
import model.PieceName;

import java.util.Set;

public class PromotedSilver extends Gold {
    public PromotedSilver(PieceColor color) {
        super(color);
        pieceName = PieceName.PROMOTED_SILVER;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        return super.getStandardMovements(coord);
    }
}
