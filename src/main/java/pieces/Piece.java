package pieces;


import board.MainBoard;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;
import utils.Coord;
import utils.PieceImageLoader;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;

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
        return !(coord.getHeight() < 0 || coord.getWidth() < 0
                || coord.getHeight() >= N_ROWS || coord.getWidth() >= N_COLUMNS);
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
