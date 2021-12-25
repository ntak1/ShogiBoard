package model.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Cell;
import model.Coord;

import static model.UiConfig.PADDING;
import static model.UiConfig.SQUARE_SIZE;


public class BoardModule extends AbstractModule {
    private static final int N_COLUMNS = 9;
    private static final int N_ROWS = 9;

    @Provides
    @Singleton
    public GridPane providesGridPane() {
        GridPane uiBoard = new GridPane();
        uiBoard.getRowConstraints().add(new RowConstraints(PADDING));
        uiBoard.getColumnConstraints().add(new ColumnConstraints(PADDING));
        for (int i = 0; i < 8; i++) {
            uiBoard.getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE));
            uiBoard.getRowConstraints().add(new RowConstraints(SQUARE_SIZE));
        }
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                Rectangle rect = new Rectangle();
                rect.setWidth(SQUARE_SIZE);
                rect.setHeight(SQUARE_SIZE);
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(3);
                rect.setFill(javafx.scene.paint.Color.TRANSPARENT);
                uiBoard.add(rect, i, j);
            }
        }

        return uiBoard;
    }

    @Provides
    public Cell[][] providesCellBoard(GridPane uiBoard) {
        Cell[][] cellBoard = new Cell[N_ROWS][N_COLUMNS];
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                cellBoard[i][j] = new Cell();
                cellBoard[i][j].setGridPane(uiBoard);
                cellBoard[i][j].setCoord(new Coord(i, j));
            }
        }
        return cellBoard;
    }

    @Provides
    public Board providesBoard(GridPane uiBoard, Cell[][] cellBoard) {
        return new Board(uiBoard, cellBoard);
    }
}
