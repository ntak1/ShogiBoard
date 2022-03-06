package board;

import javafx.scene.layout.GridPane;
import lombok.Getter;
import pieces.Piece;
import utils.Cell;
import utils.Coord;

import java.util.List;
import static module.BoardModule.CAPTURED_AREA_N_ROWS;
import static module.BoardModule.N_COLUMNS;
import static module.BoardModule.N_ROWS;

public class CapturedPiecesBoard extends Board {
    @Getter
    private Cell[][] cellBoard;

    @Getter
    private final GridPane uiBoard;

    public CapturedPiecesBoard(GridPane gridPane) {
        this.uiBoard = gridPane;
        initCapturedAreaCellBoard();
    }

    @Override
    public Piece move(Cell source, Cell destination) {
        final Piece sourcePiece = source.getPiece();
        final Coord destinationCord = destination.getCoord();
        final List<Coord> possibleMovements = sourcePiece.getPossibleMovements(source.getCoord());
        if (possibleMovements.contains(destinationCord)) {
            source.removePiece();
            destination.setPiece(sourcePiece);
        }
        return null;
    }

    private void initCapturedAreaCellBoard() {
        cellBoard = new Cell[CAPTURED_AREA_N_ROWS][N_COLUMNS];
        for (int i = 0; i < CAPTURED_AREA_N_ROWS; i++) {
            for (int j = 0; j < N_ROWS; j++) {
                cellBoard[i][j] = new Cell();
                cellBoard[i][j].setGridPane(uiBoard);
                cellBoard[i][j].setCoord(new Coord(i, j));
            }
        }
    }
}
