package board;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import pieces.Piece;
import utils.Cell;
import utils.Coord;
import utils.Helper;

import java.util.List;

public abstract class Board {
    protected Cell[][] cellBoard;
    protected GridPane gridPane;

    public void highlightCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            Rectangle cell = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
            Rectangle rectangle = new Rectangle(cell.getHeight(), cell.getWidth());
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);
            rectangle.setFill(new javafx.scene.paint.Color(0.1, 0.1, 1, 0.25));
            cellBoard[coord.height][coord.width].addLayer(rectangle);
        }
    }

    public void removeHighlightOnCells(List<Coord> cordList) {
        for(Coord coord: cordList) {
            cellBoard[coord.height][coord.width].popLayer();
        }
    }

    public abstract Piece move(final Cell source, final Cell destination);

    public Cell getCell(Coord coord) {
        return cellBoard[coord.height][coord.width];
    }
}
