package model.pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import model.Coord;
import model.PieceColor;
import model.PieceImageLoader;
import model.PieceName;

public class Gold extends Piece {
    public Gold(PieceColor color, PieceImageLoader pieceImageLoader) {
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
    public void move(Coord newCoord, Piece[][] businessBoard) {

    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.GOLD, color);
    }
}
