package pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;

public class DragonKing extends Piece {
    public DragonKing(PieceColor color) {
        super(color);
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
