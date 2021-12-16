package application;

import java.io.File;
import javafx.application.Application;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
        return board;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
