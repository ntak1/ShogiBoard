package model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;

public class Pawn extends Piece {

    public Pawn(Color color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }


    public List<Coord> getPossibleMovements() {
        List<Coord> possibleMovements = new ArrayList<>();
        if (coord == null) { // TODO: case when the pawn is outside the board

        } else {
            Coord newCoord;
            if (color == Color.WHITE) {
                newCoord = Coord.builder()
                                      .height(coord.height + 1)
                                      .width(coord.width)
                                      .build();
            } else {
                newCoord = Coord.builder()
                                      .height(coord.height - 1)
                                      .width(coord.width)
                                      .build();
            }
            possibleMovements.add(newCoord);
        }
        return possibleMovements;
    }

    public Coord getCoord() {
        return coord;
    }

    public void move(Coord coord) {

    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.PAWN, color);
    }

}
