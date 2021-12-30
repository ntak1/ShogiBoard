package model.pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.Coord;
import model.PieceColor;
import model.PieceImageLoader;
import model.PieceName;
import model.pieces.Piece;

public class Silver extends Piece {

    public Silver(PieceColor color, PieceImageLoader pieceImageLoader) {
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

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.SILVER, color);
    }
}
