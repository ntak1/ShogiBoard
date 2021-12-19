package model;

import java.util.List;
import javafx.scene.paint.ImagePattern;

public class Pawn extends Piece {

    public Pawn(Color color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }


    public List<Coord> getPossibleMovements() {
        return null;
    }

    public Coord getPosition() {
        return null;
    }

    public void move(Coord coord) {

    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.PAWN, color);
    }

}
