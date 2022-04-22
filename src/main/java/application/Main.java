package application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import module.BoardModule;
import module.PiecesModule;


public class Main extends Application {
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new PiecesModule(), new BoardModule());
        game = injector.getInstance(Game.class);
        Scene scene = game.start();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shogi Board");
        primaryStage.show();

        game.setPrimaryStage(primaryStage);
    }

}
