package pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceImageLoader;
import utils.PieceName;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;

public class Knight extends Piece {
    public Knight(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        if (captured) {
            return getDropMovements(coord);
        }
        return getStandardMovements(coord);
    }

    private List<Coord> getStandardMovements(final Coord coord) {
        List<Coord> possibleMovements = new ArrayList<>();
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

    private List<Coord> getDropMovements(final Coord coord) {
        int upperRowLimit = color == PieceColor.BLACK ? N_ROWS: N_ROWS -2;
        int lowerRowLimit = color == PieceColor.BLACK ? 2 : 0;
        System.out.println(color);
        List<Coord> possibleMovements = new ArrayList<>();
        for (int i = lowerRowLimit; i < upperRowLimit; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                final Coord newCoord = new Coord(i, j);
                if (isValidCoord(coord) && board.getCell(newCoord).isEmpty()) {
                    possibleMovements.add(newCoord);
                }
            }
        }
        return possibleMovements;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KNIGHT, color);
    }
}
