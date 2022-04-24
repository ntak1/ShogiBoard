package pieces;

import java.util.Set;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class PromotedKnight extends Gold{
    public PromotedKnight(PieceColor color) {
        super(color);
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        return super.getStandardMovements(coord);
    }

    // TODO: extract the possibility of loading the image from the Piece classes.
    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.PROMOTED_KNIGHT, color);
    }
}
