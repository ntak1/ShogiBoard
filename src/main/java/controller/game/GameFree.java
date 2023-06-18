package controller.game;

import com.google.inject.Inject;
import controller.State;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.Coord;
import model.exception.InvalidPieceException;
import model.exception.InvalidPositionException;
import model.pieces.PieceFactory;
import view.Cell;
import view.PieceView;
import view.UiConfig;
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
    public void handleOnClick(Cell cell, MouseEvent event) {
        System.out.println("I was clicked " + state + " mouse event = " + event);

        if (state == State.WAITING_SOURCE_PIECE_SELECTION) {
            if (event != null && event.getButton() == MouseButton.SECONDARY) {
                currentlySelectedCell = cell;
                handleRightClick(cell);
                return;
            }
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

    private void handleRightClick(Cell cell) {
        PieceView pieceView = cell.getPiece();
        if (pieceView == null) {
            return;
        }
        System.out.println("Should remove from board?");
        openRemovePieceFromBoardDialog(cell);
    }

    private void openRemovePieceFromBoardDialog(Cell cell) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);

        HBox hButtonBox = new HBox(20);
        Button yesButton = new Button();
        yesButton.setText("Yes");
        yesButton.setOnAction(event -> {
            PieceView pieceView = cell.getPiece();
            cell.removePiece();
            handleCapturedPiece(pieceView);
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        });

        Button noButton = new Button();
        noButton.setText("No");
        hButtonBox.getChildren().addAll(yesButton, noButton);
        hButtonBox.setAlignment(Pos.CENTER);
        noButton.setOnAction(event -> ((Stage) (((Button) event.getSource()).getScene().getWindow())).close());

        dialogVbox.getChildren().add(new Text("Remove piece from the board?"));
        dialogVbox.getChildren().addAll(hButtonBox);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, UiConfig.PROMOTION_WINDOW_WIDTH, UiConfig.PROMOTION_WINDOW_HEIGHT);

        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void movePieceFromCurrentlySelectedToDestination(Cell destinationCell) {
        PieceView capturedPiece;
        PieceView sourcePiece = currentlySelectedCell.getPiece();
        Coord source = currentlySelectedCell.getCoord();
        Coord destination = destinationCell.getCoord();
        System.out.println("Source: " + source + "Destination " + destination);
        boolean sourceWasCaptured = currentlySelectedCell.getPiece().isCaptured();

        if (isValidDestination(destinationCell)) {
            capturedPiece = board.moveUnconditionally(currentlySelectedCell, destinationCell);
            destinationCell.getPiece().setCaptured(false);
            handleCapturedPiece(capturedPiece);
        }

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
