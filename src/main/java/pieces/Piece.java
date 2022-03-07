package pieces;


import board.MainBoard;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;
import utils.Coord;
import utils.PieceImageLoader;
import static pieces.BoardConstants.N_COLUMNS;
import static pieces.BoardConstants.N_ROWS;

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
                || newPossibleHeight >= N_ROWS || newPossibleWidth >= N_COLUMNS);
    }

    protected boolean isValidMovement(Coord newCoord) {
        if (!board.getCell(newCoord).isEmpty()) {
            System.out.printf("positionColor %s, pieceColor = %s\n", board.getCell(newCoord).getPiece().getColor(), color);
        }
        return board.getCell(newCoord).isEmpty() ||
                board.getCell(newCoord).getPiece().getColor() != color;
    }

    public abstract ImagePattern getImage();

}
