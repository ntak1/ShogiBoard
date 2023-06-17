package model.pieces;

import model.Coord;
import model.PieceName;

import java.util.Set;

public class PromotedLance extends Gold {
    public PromotedLance(PieceColor color) {
        super(color);
        pieceName = PieceName.PROMOTED_LANCE;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        return super.getStandardMovements(coord);
    }

}
