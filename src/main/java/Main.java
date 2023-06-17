import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector();
        game = injector.getInstance(Game.class);
        Scene scene = game.createGameScene();
        game.setPrimaryStage(primaryStage);

        ((Pane) scene.getRoot()).getChildren().add(0, getMenuBar());
        scene.setRoot(scene.getRoot());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Shogi Board");
        primaryStage.show();
    }

    public static MenuBar getMenuBar() {

        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create a menu
        Menu fileMenu = new Menu("Game Mode");

        // Create menu items
        MenuItem newItem = new MenuItem("Single Player Board With Rules");
        MenuItem openItem = new MenuItem("Single Player Free Board");
        MenuItem saveItem = new MenuItem("Single Player vs");
        MenuItem exitItem = new MenuItem("Exit");

        // Add menu items to the menu
        fileMenu.getItems().addAll(newItem, openItem, saveItem, exitItem);

        // Add the menu to the menu bar
        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }
}
