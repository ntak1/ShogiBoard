package view.board;

import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import model.Coord;
import view.Cell;
import view.PieceView;

import java.util.Set;

import static model.board.BoardConstants.*;
import static view.UiConfig.PADDING;
import static view.UiConfig.SQUARE_SIZE;

public class CapturedPiecesBoardView extends BoardView {
    @Getter
    private Cell[][] cellBoard;

    @Getter
    private final GridPane uiBoard;

    public CapturedPiecesBoardView() {
        this.uiBoard = getCapturedAreaGridPane();
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

    private GridPane getCapturedAreaGridPane() {
        Background background = loadBackgroundImage();
        GridPane uiBoard = new GridPane();
        uiBoard.getRowConstraints().add(new RowConstraints(PADDING));
        uiBoard.getColumnConstraints().add(new ColumnConstraints(PADDING));
        for (int i = 0; i < 8; i++) {
            uiBoard.getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE));
        }
        uiBoard.getRowConstraints().add(new RowConstraints(SQUARE_SIZE));
        uiBoard.getRowConstraints().add(new RowConstraints(SQUARE_SIZE));
        for (int i = 1; i <= N_COLUMNS; i++) {
            for (int j = 1; j <= CAPTURED_AREA_N_ROWS; j++) {
                Rectangle rect = new Rectangle();
                rect.setWidth(SQUARE_SIZE);
                rect.setHeight(SQUARE_SIZE);
                rect.setStroke(Color.TRANSPARENT);
                rect.setStrokeWidth(3);
                rect.setFill(javafx.scene.paint.Color.TRANSPARENT);
                uiBoard.add(rect, i, j);
            }
        }
        uiBoard.getRowConstraints().add(new RowConstraints(PADDING));
        uiBoard.setBackground(background);
        return uiBoard;
    }
}
