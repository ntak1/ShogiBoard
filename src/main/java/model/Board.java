package model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.io.File;
import java.util.Collections;
import java.util.List;

import controller.Game;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import lombok.Setter;
import model.module.PiecesModule;

public class Board {
    private static final int N_COLUMNS = 9;
    private static final int N_ROWS = 9;

    private final Cell[][] cellBoard;
    private final GridPane gridPane;
    private final Injector injector;

    public Board(GridPane uiBoard, Cell[][] cellBoard) {
        injector = Guice.createInjector(new PiecesModule());
        this.gridPane = uiBoard;
        this.cellBoard = cellBoard;
    }

    public void bindHandler(Game game) {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                cellBoard[i][j].setOnClickHandler(game);
            }
        }
    }

    public Scene placeInitialSetup() {
        placePieces();
        int HEIGHT = 600;
        int WIDTH = 600;
        final Scene scene = new Scene(gridPane, WIDTH, HEIGHT);
        final File tileFile = new File("shogi-pieces/boards/tile_wood2.png");
        System.out.println(tileFile.toURI());
        final Image tileImage = new Image(tileFile.toURI().toString());

        BackgroundImage bImg = new BackgroundImage(tileImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);


        Background bGround = new Background(bImg);
        gridPane.setBackground(bGround);
        return scene;
    }



    private void placePieces() {
        // PAWN
        for (int i=0; i < N_COLUMNS; i++) {
            cellBoard[2][i].setPiece(injector.getInstance(Key.get(Pawn.class, Names.named(PieceColor.WHITE.toString()))));
            cellBoard[6][i].setPiece(injector.getInstance(Key.get(Pawn.class, Names.named(PieceColor.BLACK.toString()))));
        }

        // LANCE
        cellBoard[0][0].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][8].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][0].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][8].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.BLACK.toString()))));

        // KNIGHT
        cellBoard[0][1].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][7].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][1].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][7].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.BLACK.toString()))));

        // SILVER
        cellBoard[0][2].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][6].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][2].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][6].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.BLACK.toString()))));

        // GOLD
        cellBoard[0][3].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][5].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][3].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][5].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.BLACK.toString()))));

        // KING
        cellBoard[0][4].setPiece(injector.getInstance(Key.get(King.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][4].setPiece(injector.getInstance(Key.get(King.class, Names.named(PieceColor.BLACK.toString()))));

        // BISHOP
        cellBoard[1][7].setPiece(injector.getInstance(Key.get(Bishop.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[7][1].setPiece(injector.getInstance(Key.get(Bishop.class, Names.named(PieceColor.BLACK.toString()))));

        // ROOK
        cellBoard[1][1].setPiece(injector.getInstance(Key.get(Rook.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[7][7].setPiece(injector.getInstance(Key.get(Rook.class, Names.named(PieceColor.BLACK.toString()))));

    }

    public void highlightCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            Rectangle cell = (Rectangle) getNodeByRowColumnIndex(coord.height, coord.width);
            Rectangle rectangle = new Rectangle(cell.getHeight(), cell.getWidth());
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);
            rectangle.setFill(new javafx.scene.paint.Color(0.1, 0.1, 1, 0.25));
            gridPane.add(rectangle, coord.width+1, coord.height+1);
        }
    }

    public void removeHighlightOnCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            Rectangle cell = (Rectangle) getNodeByRowColumnIndex(coord.height, coord.width);
            if (cell == null || cell.getFill() == javafx.scene.paint.Color.TRANSPARENT) {
                System.out.println("Cell " + cell);
                return;
            }
            gridPane.getChildren().remove(cell);
        }
    }

   private Node getNodeByRowColumnIndex(int row, int column) {
        row = row +1;
        column = column +1;
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

}
