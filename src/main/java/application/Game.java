package application;

import board.CapturedPiecesBoard;
import board.MainBoard;
import com.google.inject.Inject;
import exception.InvalidPositionException;
import handlers.HandleOnClick;
import handlers.HandlePromotion;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceFactory;
import pieces.Promotable;
import pieces.PromotedPawn;
import utils.Cell;
import utils.Coord;
import utils.UiConfig;
import static board.BoardConstants.CAPTURED_AREA_N_ROWS;
import static board.BoardConstants.N_COLUMNS;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class Game implements HandleOnClick, HandlePromotion {
    private final MainBoard board;
    private final PieceFactory pieceFactory;

    private final CapturedPiecesBoard upperCaptured;
    private final CapturedPiecesBoard lowerCaptured;

    private PieceColor currentTurn = PieceColor.WHITE;
    private Cell currentlySelectedCell;
    private State state = State.WAITING_SOURCE_PIECE_SELECTION;

    private Cell[][] blackCapturedArea;
    private Cell[][] whiteCapturedArea;

    @Getter
    @Setter
    private Stage primaryStage;

    public Scene start() {
        // Setup board
        board.bindHandler(this);
        GridPane boardGridPane = (GridPane) board.placeInitialSetup();

        VBox vBox = new VBox();

        createCapturedArea(boardGridPane, vBox);
        return new Scene(vBox, UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
    }

    private void createCapturedArea(GridPane boardGridPane, VBox vBox) {
        blackCapturedArea = upperCaptured.getCellBoard();
        whiteCapturedArea = lowerCaptured.getCellBoard();
        GridPane upper = upperCaptured.getUiBoard();
        GridPane lower = lowerCaptured.getUiBoard();

        vBox.getChildren().addAll(lower, boardGridPane, upper);
    }

    @Override
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

    private void movePieceFromCurrentlySelectedToDestination(Cell destinationCell) {
        Piece capturedPiece;
        Piece sourcePiece = currentlySelectedCell.getPiece();
        Coord source = currentlySelectedCell.getCoord();
        Coord destination = destinationCell.getCoord();
        boolean sourceWasCaptured = currentlySelectedCell.getPiece().isCaptured();

        try {
            capturedPiece = board.move(currentlySelectedCell, destinationCell);
            destinationCell.getPiece().setCaptured(false);
        } catch (InvalidPositionException e) {
            System.out.println("Invalid Position, skipping");
            return;
        }
        handleCapturedPiece(capturedPiece);
        nextTurn();
        if (sourcePiece != null && !sourceWasCaptured) {
            handlePromotion(sourcePiece, source, destination);
        }
    }

    private void handleCapturedPiece(Piece capturedPiece) {
        if (capturedPiece == null) return;
        final Piece piece;
        if (currentTurn == PieceColor.WHITE) {
            final Coord capturedAreaCoord = positionPieceInCapturedArea(whiteCapturedArea);
            piece = pieceFactory.getPiece(capturedPiece, PieceColor.WHITE);
            whiteCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setPiece(piece);
            whiteCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setOnClickHandler(this);
        } else {
            final Coord capturedAreaCoord = positionPieceInCapturedArea(blackCapturedArea);
            piece = pieceFactory.getPiece(capturedPiece, PieceColor.BLACK);
            blackCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setPiece(piece);
            blackCapturedArea[capturedAreaCoord.getHeight()][capturedAreaCoord.getWidth()].setOnClickHandler(this);
        }
        piece.setCaptured(true);
        piece.setBoard(board);
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
        return new Coord(0, 0); // Overflow
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

    @Override
    public void handlePromotion(Promotable promotablePiece, Coord source, Coord destination) {
        if(promotablePiece.shouldPromote(source, destination)) {
            openRequiredPromotionModal();
            final Cell destinationCell = board.getCell(destination);
            final PieceColor color = destinationCell.getPiece().getColor();
            destinationCell.removePiece();
            if (promotablePiece instanceof Pawn) {
                destinationCell.setPiece(new PromotedPawn(color));
            }
        } else if (promotablePiece.canPromote(source, destination)) {
            openOptionalPromotionModal();
        }
    }

    // TODO make this private after tests
    private void openOptionalPromotionModal() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);

        HBox hButtonBox = new HBox(20);
        Button yesButton = new Button();
        yesButton.setText("Yes");
        Button noButton = new Button();
        noButton.setText("No");
        hButtonBox.getChildren().addAll(yesButton, noButton);
        hButtonBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(new Text("Do you wish to promote?"));
        dialogVbox.getChildren().addAll(hButtonBox);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, UiConfig.PROMOTION_WINDOW_WIDTH, UiConfig.PROMOTION_WINDOW_HEIGHT);

        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void openRequiredPromotionModal() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);

        dialogVbox.getChildren().add(new Text("The piece was promoted"));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, UiConfig.PROMOTION_WINDOW_WIDTH, UiConfig.PROMOTION_WINDOW_HEIGHT);

        dialog.setScene(dialogScene);
        dialog.show();
    }
}
