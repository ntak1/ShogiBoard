package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceImageLoader;
import utils.PieceName;

public class King extends Piece {

    public King(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    protected List<Coord> getStandardMovements(Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Coord newCoord = new Coord(coord.height + i, coord.width + j);
                if (isValidCoord(newCoord)) {
                    if (isValidMovement(newCoord)) {
                        possibleMovements.add(newCoord);
                    }
                }
            }
        }
        return possibleMovements;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KING, color);
    }
}
