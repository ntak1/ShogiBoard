package model;

import controller.HandleOnClick;
import javafx.event.Event;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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

    @Getter
    @Setter
    private HandleOnClick handler;

    private Stack<Rectangle> layers = new Stack<>();

    public void setPiece(Piece piece) {
        this.piece = piece;
        Rectangle rect = new Rectangle();
        rect.setWidth(UiConfig.SQUARE_SIZE);
        rect.setHeight(UiConfig.SQUARE_SIZE);
        rect.setStroke(javafx.scene.paint.Color.BLACK);
        rect.setStrokeWidth(3);
        if (piece == null) {
            rect.setFill(Color.TRANSPARENT);
        } else {
            rect.setFill(piece.getImage());
        }
        addLayer(rect);
    }

    public void addLayer(Rectangle node) {
        layers.add(node);
        node.setOnMouseClicked(x -> handler.handleOnClick(this));
        gridPane.add(node, coord.width+1, coord.height+1);
    }

    public Rectangle popLayer() {
        Rectangle peek = layers.peek();
        gridPane.getChildren().remove(peek);
        layers.pop();
        return peek;
    }

    public void setOnClickHandler(HandleOnClick handler) {
        this.handler = handler;
        Rectangle uiNode = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        uiNode.setOnMouseClicked(x -> handler.handleOnClick(this));
    }

    public void removePiece() {
        this.piece = null;
        Rectangle uiNode = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        gridPane.getChildren().remove(uiNode);
        Rectangle uiNodeOld = (Rectangle) Helper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        uiNodeOld.setFill(Color.TRANSPARENT);
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
