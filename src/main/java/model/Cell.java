package model;

import controller.Game;
import controller.HandleOnClick;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class Cell {

    @Getter
    private Rectangle uiNode;

    @Getter
    private Piece piece;

    @Getter
    @Setter
    private Coord coord;

    public void setPiece(Piece piece) {
        this.piece = piece;
        uiNode.setFill(piece.getImage());
    }

    public void setUiNode(Rectangle node) {
        this.uiNode = node;
    }

    public void setOnClickHandler(HandleOnClick handler) {
        this.uiNode.setOnMouseClicked(x -> handler.handleOnClick(this));
    }

    public void removePiece() {
        this.piece = null;
        uiNode.setFill(javafx.scene.paint.Color.TRANSPARENT);
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
