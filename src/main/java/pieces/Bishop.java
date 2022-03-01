package pieces;

import java.util.ArrayList;
import java.util.List;

import board.MainBoard;
import javafx.scene.paint.ImagePattern;
import utils.*;

public class Bishop extends Piece {

    public Bishop(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        if (coord!= null && captured) {
            return getDropMovements();
        }
        return getStandardMovements(coord);
    }

    private List<Coord> getDropMovements() {
        List<Coord> possibleMovements = new ArrayList<>();
        for (int i = 0; i < MainBoard.N_ROWS; i++) {
            for (int j = 0; j < MainBoard.N_COLUMNS; j++) {
                Coord newCoord = new Coord(i, j);
                if (board.getCell(newCoord).isEmpty()) {
                    possibleMovements.add(newCoord);
                }
            }
        }
        return possibleMovements;
    }

    private List<Coord> getStandardMovements(final Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        final int[][] directionCombinations = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] pair: directionCombinations) {
            for (int i = 1; i < MainBoard.N_ROWS; i++) {
                Coord newCoord = new Coord(coord.getHeight() + pair[0]*i, coord.getWidth() - pair[1]*i);
                if (isValidCoord(newCoord)) {
                    if (isValidMovement(newCoord)) {
                        possibleMovements.add(newCoord);
                    }
                    if (shouldStop(newCoord)) {
                        break;
                    }
                }
            }
        }
        return possibleMovements;
    }

    private boolean shouldStop(Coord newCoord) {
        return !board.getCell(newCoord).isEmpty();
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.BISHOP, color);
    }
}
