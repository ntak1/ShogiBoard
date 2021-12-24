package controller;

import javafx.scene.Scene;
import model.*;

import java.util.Collections;
import java.util.List;


public class Game implements HandleOnClick {
    private PieceColor currentTurn = PieceColor.WHITE;
    private Piece currentlySelectedPiece;
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
        if(isValidMove(cell)) {
            nextTurn();
            undoMovements = Collections.emptyList();
            currentlySelectedPiece = null;
        } else if (!cell.isEmpty() && currentlySelectedPiece == null && cell.getPiece() != null) {
            final Piece piece = cell.getPiece();
            final List<Coord> cordList = piece.getPossibleMovements(cell.getCoord());
            board.highlightCells(cordList);
        }
    }

    private boolean isValidMove(Cell cell) {
        if (!cell.isEmpty() || currentlySelectedPiece == null || cell.getPiece() == null) {
            return false;
        }
        List<Coord> possibleMovements = currentlySelectedPiece.getPossibleMovements(cell.getCoord());
        return (possibleMovements.contains(cell.getCoord()));
    }

    private void nextTurn() {
        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
