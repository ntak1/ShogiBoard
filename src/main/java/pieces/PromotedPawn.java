package pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class PromotedPawn extends Gold {
    public PromotedPawn(PieceColor color) {
        super(color);
    }

    @Override
    protected List<Coord> getStandardMovements(Coord coord) {
        return super.getStandardMovements(coord);
    }

    // TODO: extract the possibility of loading the image from the Piece classes.
    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.PROMOTED_PAWN, color);
    }
}
