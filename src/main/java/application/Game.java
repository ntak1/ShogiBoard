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
import static board.BoardConstants.CAPTURED_AREA_N_ROWS;
import static board.BoardConstants.N_COLUMNS;


public class Game implements HandleOnClick {
    private PieceColor currentTurn = PieceColor.WHITE;
    private Cell currentlySelectedCell;
    private State state = State.WAITING_SOURCE_PIECE_SELECTION;
    private MainBoard board;

    private Injector injector;

    private Cell[][] blackCapturedArea;
    private Cell[][] whiteCapturedArea;

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
            final Piece piece;
            if (currentTurn == PieceColor.WHITE) {
                final Coord capturedAreaCoord = positionPieceInCapturedArea(whiteCapturedArea);
                piece = injector.getInstance(Key.get(capturedPiece.getClass(), Names.named(PieceColor.WHITE.toString())));
                whiteCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setPiece(piece);
                whiteCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setOnClickHandler(this);
            } else {
                final Coord capturedAreaCoord = positionPieceInCapturedArea(blackCapturedArea);
                piece = injector.getInstance(Key.get(capturedPiece.getClass(), Names.named(PieceColor.BLACK.toString())));
                blackCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setPiece(piece);
                blackCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setOnClickHandler(this);
            }
            piece.setCaptured(true);
            piece.setBoard(board);
        }
        nextTurn();
    }

    private Coord positionPieceInCapturedArea(Cell[][] capturedArea) {
        for (int i = 0; i < CAPTURED_AREA_N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                final Coord newCoord = new Coord(i, j);
                if (capturedArea[i][j].isEmpty()) {
                    System.out.println(newCoord);
                    return newCoord;
                }
            }
        }
        return new Coord(0,0); // Overflow
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
