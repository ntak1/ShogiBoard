package view.board;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.exception.InvalidPositionException;
import model.Coord;
import view.Cell;
import view.PieceView;

import java.io.File;
import java.util.Set;


public abstract class BoardView {
    public static final String PATHNAME = "src/main/resources/shogi-pieces/boards/tile_wood2.png";
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

    protected Background loadBackgroundImage() {

        final File tileFile = new File(PATHNAME);
        final Image tileImage = new Image(tileFile.toURI().toString());

        BackgroundImage bImg = new BackgroundImage(tileImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        return new Background(bImg);
    }
}
