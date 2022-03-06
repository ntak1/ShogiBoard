package module;

import board.CapturedPiecesBoard;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import board.MainBoard;
import utils.Cell;
import utils.Coord;

import java.io.File;

import static utils.UiConfig.PADDING;
import static utils.UiConfig.SQUARE_SIZE;


public class BoardModule extends AbstractModule {
    public static final int N_COLUMNS = 9;
    public static final int N_ROWS = 9;
    public static final String BOARD_GRID_PANE_NAME = "BoardGridPane";
    public static final String CAPTURED_GRID_PANE_NAME = "CapturedGridPane";
    public static final int CAPTURED_AREA_N_ROWS = 2;

    @Provides
    @Singleton
    @Named(BOARD_GRID_PANE_NAME)
    public GridPane providesGridPane(Background background) {
        GridPane uiBoard = new GridPane();
        uiBoard.getRowConstraints().add(new RowConstraints(PADDING));
        uiBoard.getColumnConstraints().add(new ColumnConstraints(PADDING));
        for (int i = 0; i < 9; i++) {
            uiBoard.getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE));
            uiBoard.getRowConstraints().add(new RowConstraints(SQUARE_SIZE));
        }
        for (int i = 1; i <= N_COLUMNS; i++) {
            for (int j = 1; j <= N_ROWS; j++) {
                Rectangle rect = new Rectangle();
                rect.setWidth(SQUARE_SIZE);
                rect.setHeight(SQUARE_SIZE);
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(3);
                rect.setFill(javafx.scene.paint.Color.TRANSPARENT);
                uiBoard.add(rect, i, j);
            }
        }
        uiBoard.getRowConstraints().add(new RowConstraints(PADDING));

        uiBoard.setBackground(background);
        return uiBoard;
    }

    @Provides
    @Named(CAPTURED_GRID_PANE_NAME)
    protected GridPane providesCapturedAreaGridPane(Background background) {
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

    @Provides
    @Singleton
    protected Background providesBackground() {

        final File tileFile = new File("shogi-pieces/boards/tile_wood2.png");
        final Image tileImage = new Image(tileFile.toURI().toString());

        BackgroundImage bImg = new BackgroundImage(tileImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        return  new Background(bImg);
    }

    @Provides
    public MainBoard providesBoard(@Named(BOARD_GRID_PANE_NAME) GridPane uiBoard) {
        return new MainBoard(uiBoard);
    }

    @Provides
    public CapturedPiecesBoard providesCapturedBoard(@Named(CAPTURED_GRID_PANE_NAME) GridPane gridPane) {
        return new CapturedPiecesBoard(gridPane);
    }
}
