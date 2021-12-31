package model.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.*;
import model.pieces.Piece;

public class Rook extends Piece {

    public Rook(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        if (coord == null) {
            return Collections.emptyList();
        }
        return getStandardMovements(coord);
    }

    private List<Coord> getStandardMovements(Coord coord) {
        final List<Coord> possibleMovements = new ArrayList<>();
        // Rows upward
        Coord newCoord = new Coord(coord.getHeight() -1, coord.getWidth());

        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) break;
            newCoord = new Coord(newCoord.getHeight() -1, coord.getWidth());
        }
        // Rows downward
        newCoord = new Coord(coord.getHeight() + 1, coord.getWidth());
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) break;
            newCoord = new Coord(newCoord.getHeight() + 1, coord.getWidth());
        }

        // Columns left
        newCoord = new Coord(coord.getHeight(), coord.getWidth() -1);
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) break;
            newCoord = new Coord(coord.getHeight(), newCoord.getWidth() -1);
        }

        // Columns right
        newCoord = new Coord(coord.getHeight(), coord.getWidth() + 1);
        while (isValidCoord(newCoord) && isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
            if (shouldStop(newCoord)) break;
            newCoord = new Coord(coord.getHeight(), newCoord.getWidth() + 1);
        }
        return possibleMovements;
    }

    private boolean shouldStop(Coord newCoord) {
        if (!board.getCell(newCoord).isEmpty() && board.getCell(newCoord).getPiece().getColor() != color) {
            return true;
        }
        return false;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.ROOK, color);
    }
}
