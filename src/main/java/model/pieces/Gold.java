package model.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.*;

public class Gold extends Piece {
    public Gold(PieceColor color, PieceImageLoader pieceImageLoader) {
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
        final List<Coord> standardMovements = new ArrayList<>();
        final int height = coord.getHeight();
        final int width = coord.getWidth();

        for (int i = -1; i <= 1 ; i++) {
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
            if (verticalStep == 1 && horizontalStep == -1) {
                return true;
            }
            return verticalStep == 1 && horizontalStep == 1;
        } else {
            if (verticalStep == -1 && horizontalStep == -1) {
                return true;
            }
            return verticalStep == -1 && horizontalStep == 1;
        }
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.GOLD, color);
    }
}
