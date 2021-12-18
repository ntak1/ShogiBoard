package application;

import com.google.inject.Inject;
import java.io.File;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.inject.Named;
import model.Pawn;
import model.Piece;
import model.PieceName;


public class Main extends Application {

    private final int WIDTH = 553;
    private final int HEIGHT = 553;

    @Override
    public void start(Stage primaryStage) {
        GridPane root = initBoard();
        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        final File tileFile = new File("shogi-pieces/boards/tile_wood2.png");
        System.out.println(tileFile.toURI().toString());
        final Image tileImage = new Image(tileFile.toURI().toString());

        BackgroundImage bImg = new BackgroundImage(tileImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);


        Background bGround = new Background(bImg);
        root.setBackground(bGround);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public GridPane initBoard(){
        int squareSize = 60;
        int padding = 35;
        GridPane board = new GridPane();
        board.getRowConstraints().add(new RowConstraints(padding));
        board.getColumnConstraints().add(new ColumnConstraints(padding));
        for(int i = 0; i < 8; i++){
            board.getColumnConstraints().add(new ColumnConstraints(squareSize));
            board.getRowConstraints().add(new RowConstraints(squareSize));
        }
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                Rectangle rect = new Rectangle();
                rect.setWidth(squareSize);
                rect.setHeight(squareSize);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(3);
                rect.setFill(Color.TRANSPARENT);
                board.add(rect, i, j);
            }
        }

        Rectangle test = (Rectangle) getNodeByRowColumnIndex(1,1, board);
        test.setFill(new ImagePattern((new Pawn()).getImage()));

        return board;
    }

    private void placePiece(final Piece piece) {

    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
