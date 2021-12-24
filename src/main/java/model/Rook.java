package model;

import java.util.List;
import javafx.scene.paint.ImagePattern;

public class Rook extends Piece {

    public Rook(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements(Coord coord) {
        return null;
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
        return pieceImageLoader.getImagePattern(PieceName.ROOK, color);
    }
}
