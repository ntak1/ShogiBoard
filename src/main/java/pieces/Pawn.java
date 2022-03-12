package pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceImageLoader;
import utils.PieceName;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;

public class Pawn extends Piece {

    public Pawn(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    protected List<Coord> getStandardMovements(Coord coord) {
        List<Coord> possibleMovements = new ArrayList<>();
        final Coord newCoord;
        final int direction = color == PieceColor.WHITE ? 1 : -1;
        newCoord = Coord.builder()
                        .height(coord.height + direction)
                        .width(coord.width)
                        .build();
        if (isValidMovement(newCoord)) {
            possibleMovements.add(newCoord);
        }
        return possibleMovements;
    }

    private boolean isInTheEdgeOfBoard(final Coord coord) {
        System.out.println("coord.height = " + coord.height);
        if (coord.height == 0 && color == PieceColor.BLACK) {
            return true;
        }
        return coord.height == N_ROWS - 1 && color == PieceColor.WHITE;
    }

    @Override
    protected List<Coord> getPossibleDropMovements() {
        final List<Coord> possibleMovements = new ArrayList<>();
        final List<Integer> possibleDropColumns = new ArrayList<>();

        getValidColumnDrops(possibleDropColumns);

        for (Integer column: possibleDropColumns) {
            for (int i = 0; i < N_ROWS; i++) {
                Coord newCoord = new Coord(i, column);
                if (isValidCoord(newCoord) && board.getCell(newCoord).isEmpty()) {
                    if (!isInTheEdgeOfBoard(newCoord)) {
                        possibleMovements.add(newCoord);
                    }
                }

            }
        }
        return possibleMovements;
    }

    private void getValidColumnDrops(List<Integer> possibleDropColumns) {
        for (int i = 0; i < N_COLUMNS; i++) {
            boolean validColumn = true;
            for (int j = 0; j < N_ROWS; j++) {
                Coord newCoord = new Coord(j, i);
                final Piece piece = board.getCell(newCoord).getPiece();
                if (isValidCoord(newCoord) && piece instanceof Pawn && piece.getColor() == color) {
                    validColumn = false;
                }
            }
            if (validColumn) {
                possibleDropColumns.add(i);
            }
        }
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.PAWN, color);
    }

}
