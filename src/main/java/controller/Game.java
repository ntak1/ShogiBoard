package controller;

import javafx.scene.Scene;
import model.*;
import model.pieces.Piece;

import java.util.Collections;
import java.util.List;


public class Game implements HandleOnClick {
    private PieceColor currentTurn = PieceColor.WHITE;
    private Cell currentlySelectedCell;
    private List<Coord> undoMovements;
    private State state = State.CHOOSE_PIECE;
    private Board board;

    public Game() {
    }

    public Scene start() {
        System.out.println("Board " + board);
        Scene scene = board.placeInitialSetup();
        return scene;
    }

    public void setBoard(Board board) {
        System.out.println("Game starting, turn: " + currentTurn.toString());
        this.board = board;
        this.board.bindHandler(this);
    }

    public void handleOnClick(Cell cell) {
        System.out.println("I was clicked!");
        if (cell.getPiece() != null) {
            if (state == State.CHOOSE_PIECE) {
                final Piece piece = cell.getPiece();
                final List<Coord> cordList = piece.getPossibleMovements(cell.getCoord());
                board.highlightCells(cordList);
                if (currentlySelectedCell != null) {
                    board.removeHighlightOnCells(getCordList(currentlySelectedCell));
                }
                currentlySelectedCell = cell;
                state = State.WAIT_NEW_PLACE;
            } else if (currentlySelectedCell != null && currentlySelectedCell.getPiece() != null) {
                board.removeHighlightOnCells(getCordList(currentlySelectedCell));
                board.move(currentlySelectedCell, cell);
                currentlySelectedCell = null;
                state = State.CHOOSE_PIECE;
            }
        } else if (currentlySelectedCell != null && (cell.isEmpty() || isOppositeColor(cell))) {
            board.removeHighlightOnCells(getCordList(currentlySelectedCell));
            board.move(currentlySelectedCell, cell);
            currentlySelectedCell = null;
            state = State.CHOOSE_PIECE;
        }
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
