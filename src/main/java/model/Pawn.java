package model;

import java.io.File;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;

public class Pawn extends Piece {
    private Image image;
    private Color color;
    private Coord coord;

    public Pawn() {
        loadImage();
    }

    public Pawn(Color color, Coord coord) {
        loadImage();
        this.color = color;
        this.coord = coord;
    }

    public List<Coord> getPossibleMovements() {
        return null;
    }

    public Coord getPosition() {
        return null;
    }

    public void move(Coord coord) {

    }

    public Image getImage() {
        return image;
    }

    private void loadImage() {
        final File tileFile = new File("shogi-pieces/kanji_light/test.png");
        image = new Image(tileFile.toURI().toString());
    }
}
