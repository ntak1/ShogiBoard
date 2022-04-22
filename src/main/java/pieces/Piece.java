package pieces;


import board.MainBoard;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;
import utils.Coord;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;

@Getter
@Setter
public abstract class Piece implements Promotable {

    protected MainBoard board;
    protected PieceColor color;

    @Getter
    @Setter
    protected boolean captured;

    public Piece(PieceColor color) {
        this.color = color;
        this.captured = false;
    }

    public List<Coord> getPossibleMovements(Coord coord) {
        if (captured) {
            return getPossibleDropMovements();
        }
        return getStandardMovements(coord);
    }

    @Override
    public boolean canPromote(Coord source, Coord destination) {
        return false;
    }

    protected boolean isValidCoord(Coord coord) {
        return !(coord.getHeight() < 0 || coord.getWidth() < 0
                || coord.getHeight() >= N_ROWS || coord.getWidth() >= N_COLUMNS);
    }

    protected boolean isValidMovement(Coord newCoord) {
        return board.getCell(newCoord).isEmpty() ||
                board.getCell(newCoord).getPiece().getColor() != color;
    }

    protected abstract List<Coord> getStandardMovements(Coord coord);

    protected List<Coord> getPossibleDropMovements() {
        List<Coord> possibleMovements = new ArrayList<>();
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                final Coord newCoord = new Coord(i, j);
                if (isValidCoord(newCoord) && board.getCell(newCoord).isEmpty()) {
                    possibleMovements.add(newCoord);
                }
            }
        }
        return possibleMovements;
    }

    public abstract ImagePattern getImage();

    @Override
    public boolean shouldPromote(Coord source, Coord destination) {
        return false;
    }

}
