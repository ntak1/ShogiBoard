package model.pieces;


import lombok.Getter;
import lombok.Setter;
import model.Coord;
import model.PieceName;
import view.board.MainBoardView;

import java.util.HashSet;
import java.util.Set;

import static model.board.BoardConstants.N_COLUMNS;
import static model.board.BoardConstants.N_ROWS;

@Getter
@Setter
public abstract class Piece implements Promotable, Move {

    protected MainBoardView board;
    protected PieceColor color;
    protected PieceName pieceName;

    @Getter
    @Setter
    protected boolean captured;

    public Piece(PieceColor color) {
        this.color = color;
        this.captured = false;
    }

    @Override
    public Set<Coord> getPossibleMovements(Coord coord) {
        if (captured) {
            return getPossibleDropMovements();
        }
        return getStandardMovements(coord);
    }

    @Override
    public boolean canPromote(Coord source, Coord destination) {
        return false;
    }

    protected boolean isValidCoord(Coord coord) {
        return !(coord.getHeight() < 0 || coord.getWidth() < 0
                || coord.getHeight() >= N_ROWS || coord.getWidth() >= N_COLUMNS);
    }

    protected boolean isValidMovement(Coord newCoord) {
        return board.getCell(newCoord).isEmpty() ||
                board.getCell(newCoord).getPiece().getColor() != color;
    }

    protected abstract Set<Coord> getStandardMovements(Coord coord);

    protected Set<Coord> getPossibleDropMovements() {
        Set<Coord> possibleMovements = new HashSet<>();
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                final Coord newCoord = new Coord(i, j);
                if (isValidCoord(newCoord) && board.getCell(newCoord).isEmpty()) {
                    possibleMovements.add(newCoord);
                }
            }
        }
        return possibleMovements;
    }


    @Override
    public boolean shouldPromote(Coord source, Coord destination) {
        return false;
    }

}
