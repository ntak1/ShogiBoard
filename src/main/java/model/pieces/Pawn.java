package model.pieces;

import model.Coord;
import model.PieceName;
import model.board.BoardConstants;
import view.PieceView;

import java.util.HashSet;
import java.util.Set;

import static model.board.BoardConstants.N_COLUMNS;
import static model.board.BoardConstants.N_ROWS;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
        pieceName = PieceName.PAWN;
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        Set<Coord> possibleMovements = new HashSet<>();
        final Coord newCoord;
        final int direction = color == PieceColor.WHITE ? 1 : -1;
        newCoord = Coord.builder()
                .height(coord.height + direction)
                .width(coord.width)
                .build();
        if (isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
        }
        return possibleMovements;
    }

    private boolean isInTheEdgeOfBoard(final Coord coord) {
        if (coord.height == 0 && color == PieceColor.BLACK) {
            return true;
        }
        return coord.height == N_ROWS - 1 && color == PieceColor.WHITE;
    }

    @Override
    protected Set<Coord> getPossibleDropMovements() {
        final Set<Coord> possibleMovements = new HashSet<>();
        final Set<Integer> possibleDropColumns = new HashSet<>();

        getValidColumnDrops(possibleDropColumns);

        for (Integer column : possibleDropColumns) {
            for (int i = 0; i < N_ROWS; i++) {
                Coord newCoord = new Coord(i, column);
                if (isValidCoord(newCoord) && board.getCell(newCoord).isEmpty()) {
                    if (!isInTheEdgeOfBoard(newCoord)) {
                        possibleMovements.add(newCoord);
                    }
                }

            }
        }
        return possibleMovements;
    }

    private void getValidColumnDrops(Set<Integer> possibleDropColumns) {
        for (int i = 0; i < N_COLUMNS; i++) {
            boolean validColumn = true;
            for (int j = 0; j < N_ROWS; j++) {
                Coord newCoord = new Coord(j, i);
                final PieceView piece = board.getCell(newCoord).getPiece();
                if (isValidCoord(newCoord) && piece != null && piece.getPiece() instanceof Pawn && piece.getColor() == color) {
                    validColumn = false;
                }
            }
            if (validColumn) {
                possibleDropColumns.add(i);
            }
        }
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
