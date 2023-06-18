package controller.game;

import com.google.inject.Inject;
import controller.State;
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
import model.Coord;
import model.exception.InvalidPieceException;
import model.pieces.PieceColor;
import model.pieces.PieceFactory;
import view.Cell;
import view.PieceView;
import view.UiConfig;
import view.board.CapturedPiecesBoardView;
import view.board.MainBoardView;

import java.util.Collections;
import java.util.Set;

import static model.board.BoardConstants.CAPTURED_AREA_N_ROWS;
import static model.board.BoardConstants.N_COLUMNS;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public abstract class Game implements HandleOnClick {
    protected final MainBoardView board;
    protected final PieceFactory pieceFactory;

    protected State state = State.WAITING_SOURCE_PIECE_SELECTION;

    protected Cell[][] blackCapturedArea;
    protected Cell[][] whiteCapturedArea;
    protected Cell currentlySelectedCell;

    @Getter
    @Setter
    private Stage primaryStage;

    public Scene createGameScene() {
        // Setup model.board
        board.bindHandler(this);
        GridPane boardGridPane = (GridPane) board.placeInitialSetup();

        VBox vBox = new VBox();

        CapturedPiecesBoardView upperCaptured = new CapturedPiecesBoardView();
        CapturedPiecesBoardView lowerCaptured = new CapturedPiecesBoardView();

        blackCapturedArea = upperCaptured.getCellBoard();
        whiteCapturedArea = lowerCaptured.getCellBoard();
        GridPane upper = upperCaptured.getUiBoard();
        GridPane lower = lowerCaptured.getUiBoard();

        vBox.getChildren().addAll(lower, boardGridPane, upper);
        return new Scene(vBox, UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
    }


    protected Set getCellPossibleMovements(Cell cell) {
        if (cell == null) {
            return Collections.EMPTY_SET;
        }
        return cell.getPiece().getPossibleMovements(cell.getCoord());
    }

    protected void handleCapturedPiece(PieceView capturedPiece) {
        if (capturedPiece == null) return;
        final PieceView piece;
        if (capturedPiece.getColor() == PieceColor.BLACK) {
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

    protected abstract boolean isOppositeColor(Cell cell);

    protected boolean isValidDestination(Cell cell) {
        return currentlySelectedCell != null && (cell.isEmpty() || isOppositeColor(cell));
    }

    protected void handlePromotion(PieceView promotablePiece, Coord source, Coord destination) throws InvalidPieceException {
        if (promotablePiece.getPiece().shouldPromote(source, destination)) {
            openRequiredPromotionModal();
            final Cell destinationCell = board.getCell(destination);
            if (!destinationCell.isEmpty()) {
                destinationCell.removePiece();
            }
            PieceView promotedPiece = pieceFactory.promotePiece(promotablePiece);
            if (promotedPiece != null) {
                destinationCell.setPiece(promotedPiece);
                promotedPiece.setBoard(this.board);
            }
        } else if (promotablePiece.getPiece().canPromote(source, destination)) {
            openOptionalPromotionModal(destination);
        }
    }

    protected void openOptionalPromotionModal(Coord destination) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);

        HBox hButtonBox = new HBox(20);
        Button yesButton = new Button();
        yesButton.setText("Yes");
        yesButton.setOnAction(event -> {
            try {
                promoteCurrentlySelectedPiece(destination);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            } catch (InvalidPieceException e) {
                e.printStackTrace();
            }
        });

        Button noButton = new Button();
        noButton.setText("No");
        hButtonBox.getChildren().addAll(yesButton, noButton);
        hButtonBox.setAlignment(Pos.CENTER);
        noButton.setOnAction(event -> ((Stage) (((Button) event.getSource()).getScene().getWindow())).close());


        dialogVbox.getChildren().add(new Text("Do you wish to promote?"));
        dialogVbox.getChildren().addAll(hButtonBox);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, UiConfig.PROMOTION_WINDOW_WIDTH, UiConfig.PROMOTION_WINDOW_HEIGHT);

        dialog.setScene(dialogScene);
        dialog.show();
    }

    protected void openRequiredPromotionModal() {
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

    protected void promoteCurrentlySelectedPiece(Coord destination) throws InvalidPieceException {
        Cell destinationCell = board.getCell(destination);
        PieceView currPiece = destinationCell.getPiece();
        PieceView promotedPiece = pieceFactory.promotePiece(currPiece);
        if (!destinationCell.isEmpty()) {
            destinationCell.removePiece();
        }
        promotedPiece.setBoard(this.board);
        destinationCell.setPiece(promotedPiece);
    }
}
