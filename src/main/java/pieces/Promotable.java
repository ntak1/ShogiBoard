package pieces;

import utils.Coord;

public interface Promotable {
    public boolean canPromote(Coord source, Coord destination);

    public boolean shouldPromote(Coord source, Coord destination);
}
