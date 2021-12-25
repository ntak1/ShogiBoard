package model;

import controller.HandleOnClick;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import model.pieces.Piece;

import java.util.Stack;

public class Cell {

    @Setter
    @Getter
    private GridPane gridPane;

    @Getter
    private Piece piece;

    @Getter
    @Setter
    private Coord coord;

    private Stack<Rectangle> layers = new Stack<>();

    public void setPiece(Piece piece) {
        this.piece = piece;
        Rectangle uiNode = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        uiNode.setFill(piece.getImage());
    }

    public void addLayer(Rectangle node) {
        layers.add(node);
        gridPane.add(node, coord.width+1, coord.height+1);
    }

    public Rectangle popLayer() {
        Rectangle peek = layers.peek();
        final boolean status = gridPane.getChildren().remove(peek);
        layers.pop();
        return peek;
    }

    public void setOnClickHandler(HandleOnClick handler) {
        Rectangle uiNode = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        uiNode.setOnMouseClicked(x -> handler.handleOnClick(this));
    }

    public void removePiece() {
        this.piece = null;
        Rectangle uiNode = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        uiNode.setFill(javafx.scene.paint.Color.TRANSPARENT);
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
