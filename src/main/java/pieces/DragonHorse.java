package pieces;

import java.util.Set;
import javafx.scene.paint.ImagePattern;
import utils.Coord;
import utils.PieceName;

public class DragonHorse extends Bishop {
    public DragonHorse(PieceColor color) {
        super(color);
    }

    @Override
    protected Set<Coord> getStandardMovements(Coord coord) {
        final Set<Coord> possibleMovement = super.getStandardMovements(coord);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Coord newCoord = new Coord(coord.height + i, coord.width + j);
                if (isValidCoord(newCoord) && isValidMovement(newCoord)) {
                    possibleMovement.add(newCoord);
                }
            }
        }
        return possibleMovement;
    }

    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(PieceName.DRAGON_HORSE, color);
    }

    @Override
    public boolean canPromote(Coord source, Coord destination) {
        return false;
    }

    @Override
    public boolean shouldPromote(Coord source, Coord destination) {
        return false;
    }
}
