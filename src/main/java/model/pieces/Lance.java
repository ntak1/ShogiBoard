package model.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.Coord;
import model.PieceColor;
import model.PieceImageLoader;
import model.PieceName;

public class Lance extends Piece {
    public Lance(PieceColor color, PieceImageLoader pieceImageLoader) {
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
        final List<Coord> possibleMovements = new ArrayList<>();
        final int delta = color == PieceColor.WHITE ? 1 : -1;
        Coord newCoord = new Coord(coord.getHeight() + delta, coord.getWidth());
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            newCoord = new Coord(newCoord.getHeight() + delta, newCoord.getWidth());
            if (!board.getCell(newCoord).isEmpty()) {
                break;
            }
        }
        return possibleMovements;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.LANCE, color);
    }
}
