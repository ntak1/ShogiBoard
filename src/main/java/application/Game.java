package application;

import board.CapturedPiecesBoard;
import board.MainBoard;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import exception.InvalidPositionException;
import java.util.Collections;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pieces.Piece;
import pieces.PieceColor;
import utils.Cell;
import utils.Coord;
import utils.UiConfig;


public class Game implements HandleOnClick {
    private PieceColor currentTurn = PieceColor.WHITE;
    private Cell currentlySelectedCell;
    private State state = State.WAITING_SOURCE_PIECE_SELECTION;
    private MainBoard board;

    private Injector injector;

    private Cell[][] blackCapturedArea;
    private Cell[][] whiteCapturedArea;
    private int lastFreeWhiteCapturedCellIndex = 0;
    private int lastFreeBlackCapturedCellIndex = 0;

    public Game() {
    }

    public Scene start(Injector injector) {
        this.injector = injector;

        // Setup board
        setBoard();
        GridPane boardGridPane = (GridPane) board.placeInitialSetup(injector);

        VBox vBox = new VBox();

        createCapturedArea(injector, boardGridPane, vBox);
        return new Scene(vBox, UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
    }

    private void createCapturedArea(Injector injector, GridPane boardGridPane, VBox vBox) {
        CapturedPiecesBoard upperCaptured = injector.getInstance(CapturedPiecesBoard.class);
        CapturedPiecesBoard lowerCaptured = injector.getInstance(CapturedPiecesBoard.class);
        blackCapturedArea = upperCaptured.getCellBoard();
        whiteCapturedArea = lowerCaptured.getCellBoard();
        GridPane upper = upperCaptured.getUiBoard();
        GridPane lower = lowerCaptured.getUiBoard();

        vBox.getChildren().addAll(lower, boardGridPane, upper);
    }

    private void setBoard() {
        board = injector.getInstance(MainBoard.class);
        this.board.bindHandler(this);
    }

    public void handleOnClick(Cell cell) {
        System.out.println("I was clicked");
        if (state == State.WAITING_SOURCE_PIECE_SELECTION) {
            final Piece sourcePiece = cell.getPiece();
            if (sourcePiece == null || sourcePiece.getColor() != currentTurn) {
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

    private boolean isValidDestination(Cell cell) {
        return currentlySelectedCell != null && (cell.isEmpty() || isOppositeColor(cell));
    }

    private void movePieceFromCurrentlySelectedToDestination(Cell cell) {
        Piece capturedPiece;
        try {
            capturedPiece = board.move(currentlySelectedCell, cell);
            cell.getPiece().setCaptured(false);
        } catch (InvalidPositionException e) {
            System.out.println("Invalid Position, skipping");
            return;
        }
        if (capturedPiece != null) {
            int row;
            int col;
            final Piece piece;
            if (currentTurn == PieceColor.WHITE) {
                row = lastFreeWhiteCapturedCellIndex / 9;
                col = lastFreeWhiteCapturedCellIndex % 9;
                piece = injector.getInstance(Key.get(capturedPiece.getClass(), Names.named(PieceColor.WHITE.toString())));
                whiteCapturedArea[row][col].setPiece(piece);
                whiteCapturedArea[row][col].setOnClickHandler(this);
                lastFreeWhiteCapturedCellIndex++;
            } else {
                row = lastFreeBlackCapturedCellIndex / 9;
                col = lastFreeBlackCapturedCellIndex % 9;
                piece = injector.getInstance(Key.get(capturedPiece.getClass(), Names.named(PieceColor.BLACK.toString())));
                blackCapturedArea[row][col].setPiece(piece);
                blackCapturedArea[row][col].setOnClickHandler(this);
                lastFreeBlackCapturedCellIndex++;
            }
            piece.setCaptured(true);
            piece.setBoard(board);
        }
        nextTurn();
    }


    private boolean isValidSourcePiece() {
        return currentlySelectedCell != null && currentlySelectedCell.getPiece() != null
                && currentlySelectedCell.getPiece().getColor() == currentTurn;
    }

    private void highlightPossibleMovements(Cell cell) {
        if (cell.getPiece().getColor() != currentTurn) {
            return;
        }
        final Piece piece = cell.getPiece();
        final List<Coord> cordList = piece.getPossibleMovements(cell.getCoord());
        cell.highlightCellBorder();
        board.highlightCells(cordList);
        if (currentlySelectedCell != null) {
            board.removeHighlightOnCells(getCellPossibleMovements(currentlySelectedCell));
            cell.removeAllHighlights();
        }
        currentlySelectedCell = cell;
        state = State.WAITING_SOURCE_PIECE_SELECTION;
    }

    private List<Coord> getCellPossibleMovements(Cell cell) {
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
            return currentTurn != cell.getPiece().getColor();
        }
        return true;
    }

    private void nextTurn() {
        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
