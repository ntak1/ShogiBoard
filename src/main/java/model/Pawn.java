package model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;

public class Pawn extends Piece {

    public Pawn(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }


    public List<Coord> getPossibleMovements(Coord coord) {
        List<Coord> possibleMovements = new ArrayList<>();
        if (coord == null) { // TODO: case when the pawn is outside the board

        } else {
            Coord newCoord;
            if (color == PieceColor.WHITE) {
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

    @Override
    public void move(Coord newCoord, Piece[][] businessBoard) {

    }

    public void move(Coord coord, Coord newCoord, Piece[][] businessBoard) {
        businessBoard[newCoord.height][newCoord.width] = this;
        if (coord != null) {
            businessBoard[coord.height][coord.width] = null;
        }
        coord = newCoord;
    }

    @Override
    public ImagePattern getImage() {
        return pieceImageLoader.getImagePattern(PieceName.PAWN, color);
    }

}
