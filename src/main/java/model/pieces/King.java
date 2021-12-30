package model.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.Coord;
import model.PieceColor;
import model.PieceImageLoader;
import model.PieceName;

public class King extends Piece {

    public King(PieceColor color, PieceImageLoader pieceImageLoader) {
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
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1 ; j++) {
                if (isValidCoord(coord.height + i, coord.width + j)) {
                    final Coord newCoord = new Coord(coord.height + i, coord.width + j);
                    if (isValidMovement(newCoord)) {
                        possibleMovements.add(newCoord);
                    }
                }
            }
        }
        return possibleMovements;
    }

    public Coord getCoord() {
        return null;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KING, color);
    }
}
