package pieces;


import com.google.inject.Inject;
import java.util.List;

public abstract class Piece {
    @Inject
    private Color color;

    @Inject
    private Coord coord;

    public abstract List<Coord> getPossibleMovements();

    public abstract Coord getPosition();
}
