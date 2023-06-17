package view.board;

import model.exception.InvalidPositionException;
import javafx.scene.layout.GridPane;
import model.Coord;
import view.Cell;
import view.PieceView;

import java.util.Set;


public abstract class BoardView {
    protected Cell[][] cellBoard;
    protected GridPane gridPane;

    public void highlightCells(Set<Coord> cordList) {
        for (Coord coord : cordList) {
            cellBoard[coord.height][coord.width].highlightCell();
        }
    }

    public void removeHighlightOnCells(Set<Coord> cordList) {
        for (Coord coord : cordList) {
            cellBoard[coord.height][coord.width].removeAllHighlights();
        }
    }

    public abstract PieceView move(final Cell source, final Cell destination) throws InvalidPositionException;

    public Cell getCell(Coord coord) {
        return cellBoard[coord.height][coord.width];
    }
}
