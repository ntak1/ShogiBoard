package pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class PromotedPawn extends Piece {
    public PromotedPawn(PieceColor color) {
        super(color);
    }

    @Override
    protected List<Coord> getStandardMovements(Coord coord) {
        return null;
    }

    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.PROMOTED_PAWN, color);
    }
}
