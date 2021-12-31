package model.pieces;


import java.util.List;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;
import model.*;

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

    protected boolean isValidCoord(Coord coord) {
        int newPossibleHeight = coord.getHeight();
        int newPossibleWidth = coord.getWidth();
        return !(newPossibleHeight < 0 || newPossibleWidth < 0
                || newPossibleHeight >= Board.N_ROWS || newPossibleWidth >= Board.N_COLUMNS);
    }

    protected boolean isValidMovement(Coord newCoord) {
        return board.getCell(newCoord).isEmpty() ||
                board.getCell(newCoord).getPiece().getColor() != color;
    }

    public abstract ImagePattern getImage();

}
