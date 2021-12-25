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
    private State state = State.MOVING;
    private Board board;

    public Game(){
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
            final Piece piece = cell.getPiece();
            final List<Coord> cordList = piece.getPossibleMovements(cell.getCoord());
            board.highlightCells(cordList);

            if (currentlySelectedCell != null) {
                board.removeHighlightOnCells(getCordList(currentlySelectedCell));
            }
            currentlySelectedCell = cell;
        } else if (currentlySelectedCell != null && cell.isEmpty() || isOppositeColor(cell)) {
            board.removeHighlightOnCells(getCordList(currentlySelectedCell));
            board.move(currentlySelectedCell, cell);
            currentlySelectedCell = null;
        }
    }

    private List<Coord> getCordList(Cell cell) {
        if (currentlySelectedCell == null) {
            return Collections.emptyList();
        }
        return cell.getPiece().getPossibleMovements(cell.getCoord());
    }

    private boolean isOppositeColor(final Cell cell) {
        return cell.getPiece() != null && cell.getPiece().getColor() != currentTurn;
    }

    private void nextTurn() {
        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
