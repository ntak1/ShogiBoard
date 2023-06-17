package model.pieces;

import model.Coord;
import model.PieceName;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(PieceColor color) {
        super(color);
        pieceName = PieceName.KING;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        final Set<Coord> possibleMovements = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Coord newCoord = new Coord(coord.height + i, coord.width + j);
                if (isValidCoord(newCoord)) {
                    if (isValidMovement(newCoord)) {
                        possibleMovements.add(newCoord);
                    }
                }
            }
        }
        return possibleMovements;
    }

}
