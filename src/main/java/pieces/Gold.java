package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class Gold extends Piece {
    private Piece promotedFrom;

    public Gold(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    public Gold(PieceColor color, PieceImageLoader pieceImageLoader, Piece promotedFrom) {
        super(color, pieceImageLoader);
        this.promotedFrom = promotedFrom;
    }

    @Override
    protected List<Coord> getStandardMovements(final Coord coord) {
        final List<Coord> standardMovements = new ArrayList<>();
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
        return PieceImageLoader.getImagePattern(PieceName.GOLD, color);
    }
}
