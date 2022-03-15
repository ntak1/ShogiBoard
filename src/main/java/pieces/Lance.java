package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class Lance extends Piece {
    public Lance(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    protected List<Coord> getStandardMovements(final Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        final int delta = color == PieceColor.WHITE ? 1 : -1;
        Coord newCoord = new Coord(coord.getHeight() + delta, coord.getWidth());
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            newCoord = new Coord(newCoord.getHeight() + delta, newCoord.getWidth());
            if (!board.getCell(newCoord).isEmpty()) {
                if (isValidMovement(newCoord)) {
                    possibleMovements.add(newCoord);
                }
                break;
            }
        }
        return possibleMovements;
    }

    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.LANCE, color);
    }
}
