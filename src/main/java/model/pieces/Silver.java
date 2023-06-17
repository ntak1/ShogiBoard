package model.pieces;

import model.Coord;
import model.PieceName;
import model.board.BoardConstants;

import java.util.HashSet;
import java.util.Set;

public class Silver extends Piece {

    public Silver(PieceColor color) {
        super(color);
        pieceName = PieceName.SILVER;
    }

    @Override
    protected Set<Coord> getStandardMovements(final Coord coord) {
        final Set<Coord> standardMovements = new HashSet<>();
        final int height = coord.getHeight();
        final int width = coord.getWidth();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (shouldSkippCoord(i, j)) {
                    continue;
                }
                int newPossibleHeight = height + i;
                int newPossibleWidth = width + j;
                Coord newCoord = new Coord(newPossibleHeight, newPossibleWidth);
                if (isValidCoord(newCoord)) {
                    if (isValidMovement(newCoord)) {
                        standardMovements.add(newCoord);
                    }
                }
            }
        }
        return standardMovements;
    }

    private boolean shouldSkippCoord(int verticalStep, int horizontalStep) {
        if (color == PieceColor.BLACK) {
            return (verticalStep == 1 && horizontalStep == 0) || (verticalStep == 0);
        } else {
            return (verticalStep == -1 && horizontalStep == 0) || (verticalStep == 0);
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
