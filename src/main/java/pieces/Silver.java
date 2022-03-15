package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class Silver extends Piece {

    public Silver(PieceColor color) {
        super(color);
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
            return (verticalStep == 1 && horizontalStep == 0) || (verticalStep == 0);
        } else {
            return (verticalStep == -1 && horizontalStep == 0) || (verticalStep == 0);
        }
    }

    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.SILVER, color);
    }
}
