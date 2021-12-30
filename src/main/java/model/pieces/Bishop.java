package model.pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.*;

public class Bishop extends Piece {

    public Bishop(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        return null;
    }


    public Coord getCoord() {
        return null;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.BISHOP, color);
    }
}
