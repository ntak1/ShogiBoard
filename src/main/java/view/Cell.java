package view;

import controller.game.HandleOnClick;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import model.Coord;

import java.util.Stack;

public class Cell {

    public static final int STROKE_WIDTH = 3;
    @Setter
    @Getter
    private GridPane gridPane;

    @Getter
    private PieceView piece;

    @Getter
    @Setter
    private Coord coord;

    @Getter
    @Setter
    private HandleOnClick handler;

    private final Stack<Rectangle> layers = new Stack<>();

    public void setPiece(PieceView pieceView) {
        this.piece = pieceView;
        Rectangle rect = new Rectangle();
        rect.setWidth(UiConfig.SQUARE_SIZE);
        rect.setHeight(UiConfig.SQUARE_SIZE);
        rect.setStroke(javafx.scene.paint.Color.BLACK);
        rect.setStrokeWidth(STROKE_WIDTH);
        rect.setFill(pieceView.getImage());
        addLayer(rect);
    }

    public void highlightCell() {
        Rectangle cell = (Rectangle) GridHelper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        Rectangle rectangle = new Rectangle(cell.getHeight(), cell.getWidth());
        rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        rectangle.setFill(new javafx.scene.paint.Color(0.1, 0.1, 1, 0.25));
        this.addLayer(rectangle);
    }

    private void addLayer(@NonNull Rectangle node) {
        layers.add(node);
        node.setOnMouseClicked(x -> handler.handleOnClick(this, x));
        gridPane.add(node, coord.width + 1, coord.height + 1);
    }

    public Rectangle removeAllHighlights() {
        int size = layers.size();
        Rectangle peek = null;
        if (size > 0) {
            peek = layers.peek();
            gridPane.getChildren().remove(peek);
            layers.pop();
        }
        return peek;
    }

    public void highlightCellBorder() {
        final Rectangle oldCellUi = (Rectangle) GridHelper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        final Rectangle newCellUi = new Rectangle(oldCellUi.getHeight(), oldCellUi.getWidth());
        newCellUi.setFill(Color.TRANSPARENT);
        newCellUi.setStroke(Color.RED);
        newCellUi.setStrokeWidth(STROKE_WIDTH);
        this.addLayer(newCellUi);
    }

    public void setOnClickHandler(HandleOnClick handler) {
        this.handler = handler;
        Rectangle uiNode = (Rectangle) GridHelper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        uiNode.setOnMouseClicked(event -> handler.handleOnClick(this, event));
    }

    public void removePiece() {
        this.piece = null;
        Rectangle uiNode = (Rectangle) GridHelper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        gridPane.getChildren().remove(uiNode);
        Rectangle uiNodeOld = (Rectangle) GridHelper.getNodeByRowColumnIndex(coord.height, coord.width, gridPane);
        if (uiNodeOld == null) {
            return;
        }
        uiNodeOld.setFill(Color.TRANSPARENT);
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
