package board;

import java.util.List;
import java.util.Set;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import pieces.Piece;
import utils.Cell;
import utils.Coord;
import static board.BoardConstants.CAPTURED_AREA_N_ROWS;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;

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
        final Set<Coord> possibleMovements = sourcePiece.getPossibleMovements(source.getCoord());
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
