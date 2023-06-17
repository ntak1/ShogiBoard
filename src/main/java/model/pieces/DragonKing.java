package model.pieces;

import model.Coord;
import model.PieceName;

import java.util.Set;

public class DragonKing extends Rook {
    public DragonKing(PieceColor color) {
        super(color);
        pieceName = PieceName.DRAGON_KING;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        final Set<Coord> possibleMovement = super.getStandardMovements(coord);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Coord newCoord = new Coord(coord.height + i, coord.width + j);
                if (isValidCoord(newCoord) && isValidMovement(newCoord)) {
                    possibleMovement.add(newCoord);
                }
            }
        }
        return possibleMovement;
    }

    @Override
    public boolean canPromote(Coord source, Coord destination) {
        return false;
    }

    @Override
    public boolean shouldPromote(Coord source, Coord destination) {
        return false;
    }
}
