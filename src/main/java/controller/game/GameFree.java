package controller.game;

import com.google.inject.Inject;
import controller.State;
import javafx.event.Event;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.Coord;
import model.exception.InvalidPieceException;
import model.exception.InvalidPositionException;
import model.pieces.PieceFactory;
import view.Cell;
import view.PieceView;
import view.board.MainBoardView;

import java.util.Set;

public class GameFree extends Game {

    @Getter
    @Setter
    private Stage primaryStage;

    @Inject
    public GameFree(MainBoardView board, PieceFactory pieceFactory) {
        super(board, pieceFactory);
    }

    @Override
    public void handleOnClick(Cell cell, Event event) {
        System.out.println("I was clicked " + state);
        if (state == State.WAITING_SOURCE_PIECE_SELECTION) {
            final PieceView sourcePiece = cell.getPiece();
            if (sourcePiece == null) {
                return;
            }
            highlightPossibleMovements(cell);
            state = State.PIECE_SELECTED;
            currentlySelectedCell = cell;
        } else if (state == State.PIECE_SELECTED) {
            board.removeHighlightOnCells(getCellPossibleMovements(currentlySelectedCell));
            currentlySelectedCell.removeAllHighlights();
            if (isValidSourcePiece() && isValidDestination(cell)) {
                movePieceFromCurrentlySelectedToDestination(cell);
            }
            state = State.WAITING_SOURCE_PIECE_SELECTION;
            currentlySelectedCell = null;
        }
    }

    private void movePieceFromCurrentlySelectedToDestination(Cell destinationCell) {
        PieceView capturedPiece;
        PieceView sourcePiece = currentlySelectedCell.getPiece();
        Coord source = currentlySelectedCell.getCoord();
        Coord destination = destinationCell.getCoord();
        System.out.println("Source: " + source + "Destination " + destination);
        boolean sourceWasCaptured = currentlySelectedCell.getPiece().isCaptured();

        try {
            capturedPiece = board.move(currentlySelectedCell, destinationCell);
            destinationCell.getPiece().setCaptured(false);
        } catch (InvalidPositionException e) {
            System.out.println("Invalid Position, skipping");
            return;
        }
        handleCapturedPiece(capturedPiece);
        if (sourcePiece != null && !sourceWasCaptured) {
            try {
                handlePromotion(sourcePiece, source, destination);
            } catch (InvalidPieceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected boolean isOppositeColor(final Cell cell) {
        if (currentlySelectedCell == null) {
            return true;
        }
        if (currentlySelectedCell.getPiece() != null && cell.getPiece() != null) {
            return currentlySelectedCell.getPiece().getColor() != cell.getPiece().getColor();
        }
        return true;
    }


    private boolean isValidSourcePiece() {
        return currentlySelectedCell != null && currentlySelectedCell.getPiece() != null;
    }

    private void highlightPossibleMovements(Cell cell) {
        final PieceView piece = cell.getPiece();
        final Set<Coord> cordList = piece.getPossibleMovements(cell.getCoord());
        cell.highlightCellBorder();
        board.highlightCells(cordList);
        if (currentlySelectedCell != null) {
            board.removeHighlightOnCells(getCellPossibleMovements(currentlySelectedCell));
            cell.removeAllHighlights();
        }
        currentlySelectedCell = cell;
        state = State.WAITING_SOURCE_PIECE_SELECTION;
    }

}
