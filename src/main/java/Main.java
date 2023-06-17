import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.board.module.BoardModule;


public class Main extends Application {
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new BoardModule());
        game = injector.getInstance(Game.class);
        Scene scene = game.start();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shogi Board");
        primaryStage.show();

        game.setPrimaryStage(primaryStage);
    }

}
