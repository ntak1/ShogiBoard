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

    private Cell[][] whiteCapturedArea;
    private Cell[][] blackCapturedArea;
    private int lastFreeWhiteCapturedCellIndex = 0;
    private int lastFreeBlackCapturedCellIndex = 0;

    public Game() {
    }

    public Scene start(Injector injector) {
        System.out.println("Board " + board);
        GridPane boardGridPane = (GridPane) board.placeInitialSetup();
        VBox vBox = new VBox();

        // Create areas for captured pieces
        whiteCapturedArea = injector.getInstance(Key.get(Cell[][].class, Names.named(BoardModule.CAPTURED_CELL_BOARD)));
        blackCapturedArea = injector.getInstance(Key.get(Cell[][].class, Names.named(BoardModule.CAPTURED_CELL_BOARD)));
        GridPane upper = whiteCapturedArea[0][0].getGridPane();
        GridPane lower = blackCapturedArea[0][0].getGridPane();

        vBox.getChildren().addAll(lower, boardGridPane, upper);
        return new Scene(vBox, UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
    }

    public void setBoard(Board board) {
        this.board = board;
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
                int row = lastFreeWhiteCapturedCellIndex / 2;
                int col = lastFreeWhiteCapturedCellIndex % 9;
                whiteCapturedArea[row][col].setPiece(capturedPiece);
                lastFreeWhiteCapturedCellIndex++;
            } else {
                int row = lastFreeBlackCapturedCellIndex / 2;
                int col = lastFreeBlackCapturedCellIndex % 9;
                blackCapturedArea[row][col].setPiece(capturedPiece);
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
        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
