package model;


import java.util.List;
import javafx.scene.paint.ImagePattern;

public abstract class Piece {

    protected Color color;
    protected PieceImageLoader pieceImageLoader;

    public Piece(Color color, PieceImageLoader pieceImageLoader) {
        this.color = color;
        this.pieceImageLoader = pieceImageLoader;
    }

    public abstract List<Coord> getPossibleMovements();

    public abstract Coord getPosition();

    public abstract void move(Coord coord);

    public abstract ImagePattern getImage();

}
