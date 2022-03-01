package board;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.util.List;

import application.Game;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import pieces.*;
import utils.Cell;
import utils.Coord;
import pieces.PieceColor;


public class MainBoard extends Board {
    public static final int N_COLUMNS = 9;
    public static final int N_ROWS = 9;

    public MainBoard(GridPane uiBoard) {
        this.gridPane = uiBoard;
        this.cellBoard = initializeCellBoard(uiBoard);
    }

    public void bindHandler(Game game) {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                cellBoard[i][j].setOnClickHandler(game);
            }
        }
    }

    public Node placeInitialSetup(final Injector injector) {
        placePieces(injector);
        bindPiecesToBoard();
        return gridPane;
    }

    @Override
    public Piece move(final Cell source, final Cell destination) {
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
        }
        return capturedPiece;
    }

    private void placePieces(final Injector injector) {
        // PAWN
        for (int i=0; i < N_COLUMNS; i++) {
            cellBoard[2][i].setPiece(injector.getInstance(Key.get(Pawn.class, Names.named(PieceColor.WHITE.toString()))));
            cellBoard[6][i].setPiece(injector.getInstance(Key.get(Pawn.class, Names.named(PieceColor.BLACK.toString()))));
        }

        // LANCE
        cellBoard[0][0].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][8].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][0].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][8].setPiece(injector.getInstance(Key.get(Lance.class, Names.named(PieceColor.BLACK.toString()))));

        // KNIGHT
        cellBoard[0][1].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][7].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][1].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][7].setPiece(injector.getInstance(Key.get(Knight.class, Names.named(PieceColor.BLACK.toString()))));

        // SILVER
        cellBoard[0][2].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][6].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][2].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][6].setPiece(injector.getInstance(Key.get(Silver.class, Names.named(PieceColor.BLACK.toString()))));

        // GOLD
        cellBoard[0][3].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[0][5].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][3].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.BLACK.toString()))));
        cellBoard[8][5].setPiece(injector.getInstance(Key.get(Gold.class, Names.named(PieceColor.BLACK.toString()))));

        // KING
        cellBoard[0][4].setPiece(injector.getInstance(Key.get(King.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[8][4].setPiece(injector.getInstance(Key.get(King.class, Names.named(PieceColor.BLACK.toString()))));

        // BISHOP
        cellBoard[1][7].setPiece(injector.getInstance(Key.get(Bishop.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[7][1].setPiece(injector.getInstance(Key.get(Bishop.class, Names.named(PieceColor.BLACK.toString()))));

        // ROOK
        cellBoard[1][1].setPiece(injector.getInstance(Key.get(Rook.class, Names.named(PieceColor.WHITE.toString()))));
        cellBoard[7][7].setPiece(injector.getInstance(Key.get(Rook.class, Names.named(PieceColor.BLACK.toString()))));
    }

    private void bindPiecesToBoard() {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                if(cellBoard[i][j].getPiece() != null) {
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
