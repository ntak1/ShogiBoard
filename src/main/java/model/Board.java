package model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.io.File;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.module.PiecesModule;
import model.pieces.*;

public class Board {
    public static final int N_COLUMNS = 9;
    public static final int N_ROWS = 9;

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

    public Node placeInitialSetup() {
        placePieces();
        bindPiecesToBoard();
        return gridPane;
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

    private void bindPiecesToBoard() {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                if(cellBoard[i][j].getPiece() != null) {
                    cellBoard[i][j].getPiece().setBoard(this);
                }
            }
        }
    }

    public void highlightCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            Rectangle cell = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
            Rectangle rectangle = new Rectangle(cell.getHeight(), cell.getWidth());
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);
            rectangle.setFill(new javafx.scene.paint.Color(0.1, 0.1, 1, 0.25));
            cellBoard[coord.height][coord.width].addLayer(rectangle);
        }
    }

    public void removeHighlightOnCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            cellBoard[coord.height][coord.width].popLayer();
        }
    }

    public void move(final Cell source, final Cell destination) {
        final Piece sourcePiece = source.getPiece();
        final Coord destinationCoord = destination.getCoord();
        final List<Coord> possibleMovements = sourcePiece.getPossibleMovements(source.getCoord());
        if (possibleMovements.contains(destinationCoord)) {
            source.removePiece();
            if (!destination.isEmpty()) {
                destination.removePiece();
            }
            destination.setPiece(sourcePiece);
        }
    }

    public Cell getCell(Coord coord) {
        return cellBoard[coord.height][coord.width];
    }
}
