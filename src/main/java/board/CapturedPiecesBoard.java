package board;

import pieces.Piece;
import utils.Cell;
import utils.Coord;

import java.util.List;

public class CapturedPiecesBoard extends Board {

    @Override
    public Piece move(Cell source, Cell destination) {
        final Piece sourcePiece = source.getPiece();
        final Coord destinationCord = destination.getCoord();
        final List<Coord> possibleMovements = sourcePiece.getPossibleMovements(source.getCoord());
        if (possibleMovements.contains(destinationCord)) {
            source.removePiece();
            destination.setPiece(sourcePiece);
        }
        return null;
    }
}
