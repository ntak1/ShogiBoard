package controller;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;
import model.module.BoardModule;
import model.pieces.Piece;

import java.util.Collections;
import java.util.List;


public class Game implements HandleOnClick {
    private PieceColor currentTurn = PieceColor.WHITE;
    private Cell currentlySelectedCell;
    private List<Coord> undoMovements;
    private State state = State.PIECE_SELECTED;
    private Board board;

    private Injector injector;

    private Cell[][] blackCapturedArea;
    private Cell[][] whiteCapturedArea;
    private int lastFreeWhiteCapturedCellIndex = 0;
    private int lastFreeBlackCapturedCellIndex = 0;

    public Game() {
    }

    public Scene start(Injector injector) {
        this.injector  = injector;

        // Setup board
        setBoard();
        GridPane boardGridPane = (GridPane) board.placeInitialSetup(injector);

        VBox vBox = new VBox();

        // Create areas for captured pieces
        blackCapturedArea = injector.getInstance(Key.get(Cell[][].class, Names.named(BoardModule.CAPTURED_CELL_BOARD)));
        whiteCapturedArea = injector.getInstance(Key.get(Cell[][].class, Names.named(BoardModule.CAPTURED_CELL_BOARD)));
        GridPane upper = blackCapturedArea[0][0].getGridPane();
        GridPane lower = whiteCapturedArea[0][0].getGridPane();

        vBox.getChildren().addAll(lower, boardGridPane, upper);
        return new Scene(vBox, UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
    }

    private void setBoard() {
        board = injector.getInstance(Board.class);
        this.board.bindHandler(this);
    }

    public void handleOnClick(Cell cell) {
        System.out.println("I was clicked");
        if (cell.getPiece() != null) {
            if (state == State.PIECE_SELECTED) {
                highlightPossibleMovements(cell);
            } else if (validSourcePiece()) {
                movePieceFromCurrentlySelectedToDestination(cell);
                nextTurn();
            }
        } else if (isValidDestination(cell)) {
            movePieceFromCurrentlySelectedToDestination(cell);
            nextTurn();
        }
    }

    private boolean isValidDestination(Cell cell) {
        return currentlySelectedCell != null && (cell.isEmpty() || isOppositeColor(cell));
    }

    private void movePieceFromCurrentlySelectedToDestination(Cell cell) {
        board.removeHighlightOnCells(getCordList(currentlySelectedCell));
        Piece capturedPiece = board.move(currentlySelectedCell, cell);
        if (capturedPiece != null) {
            System.out.println("Captured Piece!");
            if (currentTurn == PieceColor.WHITE) {
                int row = lastFreeWhiteCapturedCellIndex / 9;
                int col = lastFreeWhiteCapturedCellIndex % 9;
                final Piece piece = injector.getInstance(Key.get(capturedPiece.getClass(), Names.named(PieceColor.WHITE.toString())));
                whiteCapturedArea[row][col].setPiece(piece);
                lastFreeWhiteCapturedCellIndex++;
            } else {
                int row = lastFreeBlackCapturedCellIndex / 9;
                int col = lastFreeBlackCapturedCellIndex % 9;
                final Piece piece = injector.getInstance(Key.get(capturedPiece.getClass(), Names.named(PieceColor.BLACK.toString())));
                blackCapturedArea[row][col].setPiece(piece);
                lastFreeBlackCapturedCellIndex++;
            }
        }
        currentlySelectedCell = null;
        state = State.PIECE_SELECTED;
    }

    private boolean validSourcePiece() {
        return currentlySelectedCell != null && currentlySelectedCell.getPiece() != null;
    }

    private void highlightPossibleMovements(Cell cell) {
        final Piece piece = cell.getPiece();
        final List<Coord> cordList = piece.getPossibleMovements(cell.getCoord());
        board.highlightCells(cordList);
        if (currentlySelectedCell != null) {
            board.removeHighlightOnCells(getCordList(currentlySelectedCell));
        }
        currentlySelectedCell = cell;
        state = State.WAIT_NEW_PLACEMENT;
    }

    private List<Coord> getCordList(Cell cell) {
        if (currentlySelectedCell == null) {
            return Collections.emptyList();
        }
        return cell.getPiece().getPossibleMovements(cell.getCoord());
    }

    private boolean isOppositeColor(final Cell cell) {
        if (currentlySelectedCell == null) {
            return true;
        }
        if (currentlySelectedCell.getPiece() != null && cell.getPiece() != null) {
            return currentlySelectedCell.getPiece().getColor() != cell.getPiece().getColor();
        }
        return true;
    }

    private void nextTurn() {
//        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
        currentTurn = PieceColor.BLACK;
    }
}
