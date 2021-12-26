package model.pieces;


import java.util.List;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Coord;
import model.PieceColor;
import model.PieceImageLoader;

@Getter
@Setter
public abstract class Piece {

    protected Board board;
    protected PieceColor color;
    protected PieceImageLoader pieceImageLoader;

    public Piece(PieceColor color, PieceImageLoader pieceImageLoader) {
        this.color = color;
        this.pieceImageLoader = pieceImageLoader;
    }

    public abstract List<Coord> getPossibleMovements(Coord coord);
    
    public abstract void move(Coord newCoord, Piece[][] businessBoard);

    public abstract ImagePattern getImage();

}
