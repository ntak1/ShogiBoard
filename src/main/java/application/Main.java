package application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Board;
import model.Coord;
import model.PiecesModule;


public class Main extends Application {
    private  Board board;
    private List<Coord> undoMovements = Collections.emptyList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new PiecesModule());
        board = injector.getInstance(Board.class);
        System.out.println("Board " + board);
        Scene scene = board.placeInitialSetup();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shogi Board");
        primaryStage.show();

    }


}
