package model;

import java.util.List;
import javafx.scene.paint.ImagePattern;

public class Knight extends Piece {
    public Knight(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements() {
        return null;
    }

    @Override
    public Coord getCoord() {
        return null;
    }

    @Override
    public void move(Coord newCoord, Piece[][] businessBoard) {

    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KNIGHT, color);
    }
}
