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

    public Game() {
    }

    public Scene start(Injector injector) {
        System.out.println("Board " + board);
        GridPane boardGridPane = (GridPane) board.placeInitialSetup();
        VBox vBox = new VBox();
        // Create areas for captured pieces

        GridPane upper = injector.getInstance(Key.get(GridPane.class, Names.named(BoardModule.CAPTURED_GRID_PANE_NAME)));
        GridPane lower = injector.getInstance(Key.get(GridPane.class, Names.named(BoardModule.CAPTURED_GRID_PANE_NAME)));

        vBox.getChildren().addAll(lower, boardGridPane, upper);

        final Scene scene = new Scene(vBox, UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
        return scene;
    }

    public void setBoard(Board board) {
        this.board = board;
        this.board.bindHandler(this);
    }

    public void handleOnClick(Cell cell) {
        if (cell.getPiece() != null) {
            if (state == State.PIECE_SELECTED) {
                highlightPossibleMovements(cell);
            } else if (sourceCellContainsPiece()) {
                movePieceFromCurrentlySelectedToDestionation(cell);
            }
        } else if (isValidDestination(cell)) {
            movePieceFromCurrentlySelectedToDestionation(cell);
        }
    }

    private boolean isValidDestination(Cell cell) {
        return currentlySelectedCell != null && (cell.isEmpty() || isOppositeColor(cell));
    }

    private void movePieceFromCurrentlySelectedToDestionation(Cell cell) {
        board.removeHighlightOnCells(getCordList(currentlySelectedCell));
        board.move(currentlySelectedCell, cell);
        currentlySelectedCell = null;
        state = State.PIECE_SELECTED;
    }

    private boolean sourceCellContainsPiece() {
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
