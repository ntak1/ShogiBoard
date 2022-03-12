package board;

import exception.InvalidPositionException;
import java.util.List;
import javafx.scene.layout.GridPane;
import pieces.Piece;
import utils.Cell;
import utils.Coord;

public abstract class Board {
    protected Cell[][] cellBoard;
    protected GridPane gridPane;

    public void highlightCells(List<Coord> cordList) {
        for (Coord coord : cordList) {
            cellBoard[coord.height][coord.width].highlightCell();
        }
    }

    public void removeHighlightOnCells(List<Coord> cordList) {
        for (Coord coord : cordList) {
            cellBoard[coord.height][coord.width].removeAllHighlights();
        }
    }

    public abstract Piece move(final Cell source, final Cell destination) throws InvalidPositionException;

    public Cell getCell(Coord coord) {
        return cellBoard[coord.height][coord.width];
    }
}
