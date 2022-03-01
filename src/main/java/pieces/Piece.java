package pieces;


import java.util.List;

import board.MainBoard;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;
import utils.*;

@Getter
@Setter
public abstract class Piece {

    protected MainBoard board;
    protected PieceColor color;
    protected PieceImageLoader pieceImageLoader;
    protected boolean captured;

    public Piece(PieceColor color, PieceImageLoader pieceImageLoader) {
        this.color = color;
        this.pieceImageLoader = pieceImageLoader;
        this.captured = false;
    }

    public abstract List<Coord> getPossibleMovements(Coord coord);

    protected boolean isValidCoord(Coord coord) {
        int newPossibleHeight = coord.getHeight();
        int newPossibleWidth = coord.getWidth();
        return !(newPossibleHeight < 0 || newPossibleWidth < 0
                || newPossibleHeight >= MainBoard.N_ROWS || newPossibleWidth >= MainBoard.N_COLUMNS);
    }

    protected boolean isValidMovement(Coord newCoord) {
        if (!board.getCell(newCoord).isEmpty())
            System.out.printf("positionColor %s, pieceColor = %s\n", board.getCell(newCoord).getPiece().getColor(), color);
        return board.getCell(newCoord).isEmpty() ||
                board.getCell(newCoord).getPiece().getColor() != color;
    }

    public abstract ImagePattern getImage();

}
