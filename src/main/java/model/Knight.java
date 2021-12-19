package model;

import java.util.List;
import javafx.scene.paint.ImagePattern;

public class Knight extends Piece {
    public Knight(Color color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    public List<Coord> getPossibleMovements() {
        return null;
    }

    @Override
    public Coord getPosition() {
        return null;
    }

    @Override
    public void move(Coord coord) {

    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.KNIGHT, color);
    }
}
