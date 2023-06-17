package view.board;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import controller.Game;
import model.exception.InvalidPositionException;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import model.Coord;
import model.PieceName;
import model.pieces.PieceColor;
import model.pieces.PieceFactory;
import view.Cell;
import view.PieceView;

import java.util.Set;

import static model.board.BoardConstants.N_COLUMNS;
import static model.board.BoardConstants.N_ROWS;
import static view.board.module.BoardModule.BOARD_GRID_PANE_NAME;

public class MainBoardView extends BoardView {
    private final PieceFactory pieceFactory;

    @Inject
    public MainBoardView(@Named(BOARD_GRID_PANE_NAME) GridPane uiBoard, PieceFactory pieceFactory) {
        this.gridPane = uiBoard;
        this.pieceFactory = pieceFactory;
        this.cellBoard = initializeCellBoard(uiBoard);
    }

    public void bindHandler(Game game) {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                cellBoard[i][j].setOnClickHandler(game);
            }
        }
    }

    public Node placeInitialSetup() {
        placePiecesDefaultMode();
        bindPiecesToBoard();
        return gridPane;
    }

    @Override
    public PieceView move(final Cell source, final Cell destination) throws InvalidPositionException {
        final PieceView sourcePiece = source.getPiece();
        final Coord destinationCord = destination.getCoord();
        final Set<Coord> possibleMovements = sourcePiece.getPossibleMovements(source.getCoord());
        PieceView capturedPiece = null;
        if (possibleMovements.contains(destinationCord)) {
            System.out.println("Removing " + source.getPiece().getClass().getName());
            source.removePiece();
            if (!destination.isEmpty()) {
                capturedPiece = destination.getPiece();
                destination.removePiece();
            }
            destination.setPiece(sourcePiece);
        } else {
            throw new InvalidPositionException();
        }
        return capturedPiece;
    }

    private void placePiecesDefaultMode() {
        // PAWN
        for (int i = 0; i < N_COLUMNS; i++) {
            cellBoard[2][i].setPiece(pieceFactory.getPiece(PieceName.PAWN, PieceColor.WHITE));
            cellBoard[6][i].setPiece(pieceFactory.getPiece(PieceName.PAWN, PieceColor.BLACK));
        }

        // LANCE
        cellBoard[0][0].setPiece(pieceFactory.getPiece(PieceName.LANCE, PieceColor.WHITE));
        cellBoard[0][8].setPiece(pieceFactory.getPiece(PieceName.LANCE, PieceColor.WHITE));
        cellBoard[8][0].setPiece(pieceFactory.getPiece(PieceName.LANCE, PieceColor.BLACK));
        cellBoard[8][8].setPiece(pieceFactory.getPiece(PieceName.LANCE, PieceColor.BLACK));

        // KNIGHT
        cellBoard[0][1].setPiece(pieceFactory.getPiece(PieceName.KNIGHT, PieceColor.WHITE));
        cellBoard[0][7].setPiece(pieceFactory.getPiece(PieceName.KNIGHT, PieceColor.WHITE));
        cellBoard[8][1].setPiece(pieceFactory.getPiece(PieceName.KNIGHT, PieceColor.BLACK));
        cellBoard[8][7].setPiece(pieceFactory.getPiece(PieceName.KNIGHT, PieceColor.BLACK));

        // GOLD
        cellBoard[0][2].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.WHITE));
        cellBoard[0][6].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.WHITE));
        cellBoard[8][2].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.BLACK));
        cellBoard[8][6].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.BLACK));

        // SILVER
        cellBoard[0][3].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.WHITE));
        cellBoard[0][5].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.WHITE));
        cellBoard[8][3].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.BLACK));
        cellBoard[8][5].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.BLACK));

        // KING
        cellBoard[0][4].setPiece(pieceFactory.getPiece(PieceName.KING, PieceColor.WHITE));
        cellBoard[8][4].setPiece(pieceFactory.getPiece(PieceName.KING, PieceColor.BLACK));

        // BISHOP
        cellBoard[1][7].setPiece(pieceFactory.getPiece(PieceName.BISHOP, PieceColor.WHITE));
        cellBoard[7][1].setPiece(pieceFactory.getPiece(PieceName.BISHOP, PieceColor.BLACK));

        // ROOK
        cellBoard[1][1].setPiece(pieceFactory.getPiece(PieceName.ROOK, PieceColor.WHITE));
        cellBoard[7][7].setPiece(pieceFactory.getPiece(PieceName.ROOK, PieceColor.BLACK));
    }

    private void bindPiecesToBoard() {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                if (cellBoard[i][j].getPiece() != null) {
                    cellBoard[i][j].getPiece().setBoard(this);
                }
            }
        }
    }

    private Cell[][] initializeCellBoard(GridPane uiBoard) {
        Cell[][] cellBoard = new Cell[N_ROWS][N_COLUMNS];
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                cellBoard[i][j] = new Cell();
                cellBoard[i][j].setGridPane(uiBoard);
                cellBoard[i][j].setCoord(new Coord(i, j));
            }
        }
        return cellBoard;
    }
}
