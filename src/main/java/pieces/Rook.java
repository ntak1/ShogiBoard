package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class Rook extends Piece {

    public Rook(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    protected List<Coord> getStandardMovements(Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        // Rows upward
        Coord newCoord = new Coord(coord.getHeight() - 1, coord.getWidth());

        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(newCoord.getHeight() - 1, coord.getWidth());
        }
        // Rows downward
        newCoord = new Coord(coord.getHeight() + 1, coord.getWidth());
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(newCoord.getHeight() + 1, coord.getWidth());
        }

        // Columns left
        newCoord = new Coord(coord.getHeight(), coord.getWidth() - 1);
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(coord.getHeight(), newCoord.getWidth() - 1);
        }

        // Columns right
        newCoord = new Coord(coord.getHeight(), coord.getWidth() + 1);
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) {
                break;
            }
            newCoord = new Coord(coord.getHeight(), newCoord.getWidth() + 1);
        }
        return possibleMovements;
    }

    private boolean shouldStop(Coord newCoord) {
        return !board.getCell(newCoord).isEmpty();
    }

    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.ROOK, color);
    }
}
