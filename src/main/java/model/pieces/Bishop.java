package model.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import model.*;

public class Bishop extends Piece {

    public Bishop(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        if (coord == null) { // TODO treat when the piece is out of the board
            return Collections.emptyList();
        }
        return getStandardMovements(coord);
    }

    private List<Coord> getStandardMovements(final Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        final int[][] directionCombinations = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] pair: directionCombinations) {
            for (int i = 0; i < Board.N_ROWS; i++) {
                Coord newCoord = new Coord(coord.getHeight() + pair[0]*i, coord.getWidth() - pair[1]*i);
                if (isValidCoord(newCoord) && isValidMovement(newCoord)) {
                    possibleMovements.add(newCoord);
                    if (shouldStop(newCoord)) {
                        break;
                    }
                }
            }
        }
        return possibleMovements;
    }

    private boolean shouldStop(Coord newCoord) {
        return !board.getCell(newCoord).isEmpty() && board.getCell(newCoord).getPiece().getColor() != color;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.BISHOP, color);
    }
}
