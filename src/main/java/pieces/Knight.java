package pieces;

import board.BoardConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;

public class Knight extends Piece {
    public Knight(PieceColor color) {
        super(color);
    }


    protected Set<Coord> getStandardMovements(final Coord coord) {
        Set<Coord> possibleMovements = new HashSet<>();
        final int delta = color == PieceColor.WHITE ? 2 : -2;
        Coord newCoord = new Coord(coord.getHeight() + delta, coord.getWidth() - 1);
        if (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
        }
        newCoord = new Coord(coord.getHeight() + delta, coord.getWidth() + 1);
        if (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
        }

        return possibleMovements;
    }

    @Override
    protected Set<Coord> getPossibleDropMovements() {
        int upperRowLimit = color == PieceColor.BLACK ? N_ROWS : N_ROWS - 2;
        int lowerRowLimit = color == PieceColor.BLACK ? 2 : 0;
        System.out.println(color);
        Set<Coord> possibleMovements = new HashSet<>();
        for (int i = lowerRowLimit; i < upperRowLimit; i++) {
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
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.KNIGHT, color);
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
