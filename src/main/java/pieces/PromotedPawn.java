package pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;

public class PromotedPawn extends Piece {
    public PromotedPawn(PieceColor color, PieceImageLoader pieceImageLoader) {
        super(color, pieceImageLoader);
    }

    @Override
    protected List<Coord> getStandardMovements(Coord coord) {
        return null;
    }

    @Override
    public ImagePattern getImage() {
        return null;
    }
}
