package model;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import javax.crypto.spec.RC2ParameterSpec;

public class Board {
    private static final int N_COLUMNS = 9;
    private static final int N_ROWS = 9;

    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private Piece[][] businessBoard;

    private GridPane uiBoard;
    private Injector injector;


    @Inject
    public Board(Piece[][] businessBoard) {
        this.businessBoard = businessBoard;
        initBoard();
        injector = Guice.createInjector(new PiecesModule());
    }

    public Scene placeInitialSetup() {
        placePieces();
        final Scene scene = new Scene(uiBoard, WIDTH, HEIGHT);
        final File tileFile = new File("shogi-pieces/boards/tile_wood2.png");
        System.out.println(tileFile.toURI().toString());
        final Image tileImage = new Image(tileFile.toURI().toString());

        BackgroundImage bImg = new BackgroundImage(tileImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);


        Background bGround = new Background(bImg);
        uiBoard.setBackground(bGround);
        return scene;
    }

    private GridPane initBoard() {
        int squareSize = 60;
        int padding = 35;
        uiBoard = new GridPane();
        uiBoard.getRowConstraints().add(new RowConstraints(padding));
        uiBoard.getColumnConstraints().add(new ColumnConstraints(padding));
        for (int i = 0; i < 8; i++) {
            uiBoard.getColumnConstraints().add(new ColumnConstraints(squareSize));
            uiBoard.getRowConstraints().add(new RowConstraints(squareSize));
        }
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                Rectangle rect = new Rectangle();
                rect.setWidth(squareSize);
                rect.setHeight(squareSize);
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(3);
                rect.setFill(javafx.scene.paint.Color.TRANSPARENT);
                uiBoard.add(rect, i, j);
            }
        }

        return uiBoard;
    }

    private void placePieces() {
        // PAWN
        for (int i=0; i < N_COLUMNS; i++) {
            businessBoard[2][i] = injector.getInstance(Key.get(Pawn.class, Names.named(Color.WHITE.toString())));
            businessBoard[6][i] = injector.getInstance(Key.get(Pawn.class, Names.named(Color.BLACK.toString())));

            placePieceByIndex(2, i);
            placePieceByIndex(6, i);
        }

        // LANCE
        businessBoard[0][0] = injector.getInstance(Key.get(Lance.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,0);
        businessBoard[0][8] = injector.getInstance(Key.get(Lance.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,8);
        businessBoard[8][0] = injector.getInstance(Key.get(Lance.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,0);
        businessBoard[8][8] = injector.getInstance(Key.get(Lance.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,8);

        // KNIGHT
        businessBoard[0][1] = injector.getInstance(Key.get(Knight.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,1);
        businessBoard[0][7] = injector.getInstance(Key.get(Knight.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,7);
        businessBoard[8][1] = injector.getInstance(Key.get(Knight.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,1);
        businessBoard[8][7] = injector.getInstance(Key.get(Knight.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,7);

        // SILVER
        businessBoard[0][2] = injector.getInstance(Key.get(Silver.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,2);
        businessBoard[0][6] = injector.getInstance(Key.get(Silver.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,6);
        businessBoard[8][2] = injector.getInstance(Key.get(Silver.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,2);
        businessBoard[8][6] = injector.getInstance(Key.get(Silver.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,6);

        // GOLD
        businessBoard[0][3] = injector.getInstance(Key.get(Gold.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,3);
        businessBoard[0][5] = injector.getInstance(Key.get(Gold.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,5);
        businessBoard[8][3] = injector.getInstance(Key.get(Gold.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,3);
        businessBoard[8][5] = injector.getInstance(Key.get(Gold.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,5);

        // KING
        businessBoard[0][4] = injector.getInstance(Key.get(King.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(0,4);
        businessBoard[8][4] = injector.getInstance(Key.get(King.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(8,4);

        // BISHOP
        businessBoard[1][7] = injector.getInstance(Key.get(Bishop.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(1,7);
        businessBoard[7][1] = injector.getInstance(Key.get(Bishop.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(7,1);

        // ROOK
        businessBoard[1][1] = injector.getInstance(Key.get(Rook.class, Names.named(Color.WHITE.toString())));
        placePieceByIndex(1,1);
        businessBoard[7][7] = injector.getInstance(Key.get(Rook.class, Names.named(Color.BLACK.toString())));
        placePieceByIndex(7,7);

    }

    public void highlightCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            Rectangle cell = (Rectangle) getNodeByRowColumnIndex(coord.height, coord.width);
            Rectangle canvas = new Rectangle(cell.getHeight(), cell.getWidth());
            canvas.setFill(new javafx.scene.paint.Color(0, 0.1, 1, 0.5));
            uiBoard.add(canvas, coord.width+1, coord.height+1);
        }
    }

    private void placePieceByIndex(int row, int column) {
        Rectangle node = (Rectangle) getNodeByRowColumnIndex(row, column);
        if (node == null) {
            return;
        }
        node.setFill(businessBoard[row][column].getImage());
        List<Coord> cordList = new ArrayList<>();
        cordList.add(Coord.builder().height(2).width(0).build());
        node.setOnMouseClicked(x -> highlightCells(cordList));
        businessBoard[row][column].setCoord(Coord.builder().height(row).width(column).build());
    }

    private Node getNodeByRowColumnIndex(int row, int column) {
        row = row +1;
        column = column +1;
        Node result = null;
        ObservableList<Node> childrens = uiBoard.getChildren();

        for (Node node : childrens) {
            if (uiBoard.getRowIndex(node) == row && uiBoard.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

}
