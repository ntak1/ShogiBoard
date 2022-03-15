package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;
import static board.BoardConstants.N_ROWS;

public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color);
    }


    @Override
    protected List<Coord> getStandardMovements(final Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        final int[][] directionCombinations = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] pair : directionCombinations) {
            for (int i = 1; i < N_ROWS; i++) {
                Coord newCoord = new Coord(coord.getHeight() + pair[0] * i, coord.getWidth() - pair[1] * i);
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
        return PieceImageLoader.getImagePattern(PieceName.BISHOP, color);
    }
}
