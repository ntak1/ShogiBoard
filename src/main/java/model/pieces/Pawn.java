package model.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.paint.ImagePattern;
import model.*;

public class Pawn extends Piece {

    public Pawn(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }


    public List<Coord> getPossibleMovements(Coord coord) {
        List<Coord> possibleMovements = new ArrayList<>();
        if (coord == null) { // TODO: case when the pawn is outside the board

        } else if (isInTheEdgeOfBoard(coord)) {
            return Collections.emptyList();
        } else {
            Coord newCoord = getNewCoord(coord);

            if (isValidMovement(newCoord)) {
                possibleMovements.add(newCoord);
            }
        }
        return possibleMovements;
    }

    private Coord getNewCoord(Coord coord) {
        final Coord newCoord;
        final int direction = color == PieceColor.WHITE ? 1 : -1;
        newCoord = Coord.builder()
                              .height(coord.height + direction)
                              .width(coord.width)
                              .build();
        return newCoord;
    }

    private boolean isInTheEdgeOfBoard(final Coord coord) {
        System.out.println("coord.height = "  + coord.height);
        if (coord.height == 0 && color == PieceColor.BLACK) {
            return true;
        }
        return coord.height == Board.N_ROWS - 1 && color == PieceColor.WHITE;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.PAWN, color);
    }

}
