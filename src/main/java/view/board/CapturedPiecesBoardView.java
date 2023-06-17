package view.board;

import javafx.scene.layout.GridPane;
import lombok.Getter;
import model.Coord;
import view.Cell;
import view.PieceView;

import java.util.Set;

import static model.board.BoardConstants.*;

public class CapturedPiecesBoardView extends BoardView {
    @Getter
    private Cell[][] cellBoard;

    @Getter
    private final GridPane uiBoard;

    public CapturedPiecesBoardView(GridPane gridPane) {
        this.uiBoard = gridPane;
        initCapturedAreaCellBoard();
    }

    @Override
    public PieceView move(Cell source, Cell destination) {
        final PieceView sourcePiece = source.getPiece();
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
