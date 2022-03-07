package pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceImageLoader;
import utils.PieceName;

public class Knight extends Piece {
    public Knight(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        if (coord == null) {
            return Collections.emptyList();
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

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KNIGHT, color);
    }
}
