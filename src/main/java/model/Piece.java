package model;


import java.util.List;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;

public abstract class Piece {

    protected Color color;

    @Getter
    @Setter
    protected Coord coord;
    protected PieceImageLoader pieceImageLoader;

    public Piece(Color color, PieceImageLoader pieceImageLoader) {
        this.color = color;
        this.pieceImageLoader = pieceImageLoader;
    }

    public abstract List<Coord> getPossibleMovements();
    
    public abstract void move(Coord coord);

    public abstract ImagePattern getImage();

}
