package pieces;

import java.util.List;
import javafx.scene.paint.ImagePattern;
import utils.Coord;

public class DragonHorse extends Piece {
    public DragonHorse(PieceColor color) {
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
