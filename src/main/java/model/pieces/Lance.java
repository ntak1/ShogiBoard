package model.pieces;

import model.Coord;
import model.PieceName;
import model.board.BoardConstants;

import java.util.HashSet;
import java.util.Set;

import static model.board.BoardConstants.N_COLUMNS;
import static model.board.BoardConstants.N_ROWS;

public class Lance extends Piece {
    public Lance(PieceColor color) {
        super(color);
        pieceName = PieceName.LANCE;
    }

    @Override
    protected Set<Coord> getStandardMovements(final Coord coord) {
        final Set<Coord> possibleMovements = new HashSet<>();
        final int delta = color == PieceColor.WHITE ? 1 : -1;
        Coord newCoord = new Coord(coord.getHeight() + delta, coord.getWidth());
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            newCoord = new Coord(newCoord.getHeight() + delta, newCoord.getWidth());
            if (isValidCoord(newCoord) && !board.getCell(newCoord).isEmpty()) {
                if (isValidMovement(newCoord)) {
                    possibleMovements.add(newCoord);
                }
                break;
            }
        }
        return possibleMovements;
    }

    @Override
    protected Set<Coord> getPossibleDropMovements() {
        Set<Coord> possibleMovements = new HashSet<>();
        int startRow = color == PieceColor.WHITE ? 0 : 1;
        int endRow = color == PieceColor.WHITE ? N_ROWS - 1 : N_ROWS;

        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                final Coord newCoord = new Coord(i, j);
                if (isValidCoord(newCoord) && board.getCell(newCoord).isEmpty()) {
                    possibleMovements.add(newCoord);
                }
            }
        }
        return possibleMovements;
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
