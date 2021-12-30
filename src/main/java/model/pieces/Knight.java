package model.pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.Coord;
import model.PieceColor;
import model.PieceImageLoader;
import model.PieceName;

public class Knight extends Piece {
    public Knight(PieceColor color, PieceImageLoader pieceImageLoader) {
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
        return pieceImageLoader.getImagePattern(PieceName.KNIGHT, color);
    }
}
