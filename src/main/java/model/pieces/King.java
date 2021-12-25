package model.pieces;

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
