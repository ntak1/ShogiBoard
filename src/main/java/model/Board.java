package model;

public class Board {
    private static final int N_COLUMNS = 9;
    private static final int N_ROWS = 9;

    private final Piece[][] board;

    public Board() {
        board = new Piece[N_ROWS][N_COLUMNS];
    }

    private void initBoard() {
        for (int i=0; i < N_COLUMNS; i++) {
            board[2][i] = new Pawn(Color.WHITE, new Coord(2, i));
            board[6][i] = new Pawn(Color.BLACK, new Coord(6, i));
        }
    }

}
