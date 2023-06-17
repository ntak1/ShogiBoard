package model.pieces;

import model.Coord;
import model.PieceName;
import model.board.BoardConstants;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
        pieceName = PieceName.ROOK;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        final Set<Coord> possibleMovements = new HashSet<>();
        // Rows upward
        Coord newCoord = new Coord(coord.getHeight() - 1, coord.getWidth());

        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(newCoord.getHeight() - 1, coord.getWidth());
        }
        // Rows downward
        newCoord = new Coord(coord.getHeight() + 1, coord.getWidth());
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(newCoord.getHeight() + 1, coord.getWidth());
        }

        // Columns left
        newCoord = new Coord(coord.getHeight(), coord.getWidth() - 1);
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(coord.getHeight(), newCoord.getWidth() - 1);
        }

        // Columns right
        newCoord = new Coord(coord.getHeight(), coord.getWidth() + 1);
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(coord.getHeight(), newCoord.getWidth() + 1);
        }
        return possibleMovements;
    }

    private boolean shouldStop(Coord newCoord) {
        return !board.getCell(newCoord).isEmpty();
    }

    @Override
    public boolean canPromote(Coord source, Coord destination) {
        return BoardConstants.isPromotableArea(destination, color);
    }

    @Override
    public boolean shouldPromote(Coord source, Coord destination) {
        return BoardConstants.isLastLine(destination, color);
    }
}
