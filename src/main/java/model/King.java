package model;

import java.util.List;
import javafx.scene.paint.ImagePattern;

public class King extends Piece {

    public King(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    public List<Coord> getPossibleMovements() {
        return null;
    }

    public Coord getCoord() {
        return null;
    }

    public void move(Coord newCoord, Piece[][] businessBoard) {

    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KING, color);
    }
}
