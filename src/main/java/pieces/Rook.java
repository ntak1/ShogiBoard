package pieces;

import board.BoardConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        final Set<Coord> possibleMovements = new HashSet<>();
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

    @Override
    public boolean canPromote(Coord source, Coord destination) {
        return BoardConstants.isPromotableArea(destination, color);
    }

    @Override
    public boolean shouldPromote(Coord source, Coord destination) {
        return BoardConstants.isLastLine(destination, color);
    }
}
