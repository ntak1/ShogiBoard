package board;

import application.Game;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import exception.InvalidPositionException;
import handlers.HandlePromotion;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceFactory;
import utils.Cell;
import utils.Coord;
import utils.PieceName;
import static board.BoardConstants.N_COLUMNS;
import static board.BoardConstants.N_ROWS;
import static module.BoardModule.BOARD_GRID_PANE_NAME;

public class MainBoard extends Board {
    private final PieceFactory pieceFactory;

    @Getter
    private HandlePromotion promotionHandler;

    @Inject
    public MainBoard(@Named(BOARD_GRID_PANE_NAME) GridPane uiBoard, PieceFactory pieceFactory) {
        this.gridPane = uiBoard;
        this.pieceFactory = pieceFactory;
        this.cellBoard = initializeCellBoard(uiBoard);
    }

    public void bindHandler(Game game) {
        promotionHandler = game;
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                cellBoard[i][j].setOnClickHandler(game);
            }
        }
    }

    public Node placeInitialSetup() {
        placePieces();
        bindPiecesToBoard();
        return gridPane;
    }

    @Override
    public Piece move(final Cell source, final Cell destination) throws InvalidPositionException {
        final Piece sourcePiece = source.getPiece();
        final Coord destinationCord = destination.getCoord();
        final List<Coord> possibleMovements = sourcePiece.getPossibleMovements(source.getCoord());
        Piece capturedPiece = null;
        if (possibleMovements.contains(destinationCord)) {
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

    private void placePieces() {
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

        // SILVER
        cellBoard[0][2].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.WHITE));
        cellBoard[0][6].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.WHITE));
        cellBoard[8][2].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.BLACK));
        cellBoard[8][6].setPiece(pieceFactory.getPiece(PieceName.SILVER, PieceColor.BLACK));

        // GOLD
        cellBoard[0][3].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.WHITE));
        cellBoard[0][5].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.WHITE));
        cellBoard[8][3].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.BLACK));
        cellBoard[8][5].setPiece(pieceFactory.getPiece(PieceName.GOLD, PieceColor.BLACK));

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
