package application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Collections;
import java.util.List;

import controller.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Board;
import model.Coord;
import model.module.BoardModule;
import model.module.PiecesModule;


public class Main extends Application {
    private Board board;
    private Game game;
    private List<Coord> undoMovements = Collections.emptyList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new PiecesModule(), new BoardModule());

        game = new Game();
        board = injector.getInstance(Board.class);
        game.setBoard(board);
        Scene scene = game.start(injector);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shogi Board");
        primaryStage.show();

    }


}
