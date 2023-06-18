import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.game.GameFree;
import controller.game.GameWithRule;
import controller.game.HandleOnClick;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GameMode;

import java.util.HashMap;
import java.util.Map;


public class Main extends Application {
    public static final String SINGLE_PLAYER_BOARD_WITH_RULES = "Single Player Board With Rules";
    public static final String SINGLE_PLAYER_FREE_BOARD = "Single Player Free Board";
    public static final String SELECTED_GAME_MODE = "Selected game mode: ";
    public static final String GAME_MODE = "Game Mode";
    public static final String SHOGI_BOARD = "Shogi Board";
    private static Map<GameMode, String> gameModeMap;
    private HandleOnClick game;
    private TextField textField;
    private GameMode gameMode = GameMode.SINGLE_PLAYER_WITH_RULES;
    private Stage primaryStage;
    private Injector injector;

    static {
        gameModeMap = new HashMap<>();
        gameModeMap.put(GameMode.SINGLE_PLAYER_WITH_RULES, SINGLE_PLAYER_BOARD_WITH_RULES);
        gameModeMap.put(GameMode.SINGLE_PLAYER_FREE, SINGLE_PLAYER_FREE_BOARD);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.injector = Guice.createInjector();
        this.primaryStage = primaryStage;

        updateScene(primaryStage);
    }

    private void updateScene(Stage primaryStage) {
        Scene scene = getSceneForGameType();

        ((Pane) scene.getRoot()).getChildren().add(0, createMenuBar());
        ((Pane) scene.getRoot()).getChildren().add(0, createTextField());
        scene.setRoot(scene.getRoot());

        primaryStage.setScene(scene);
        primaryStage.setTitle(SHOGI_BOARD);
        primaryStage.show();
    }


    private Scene getSceneForGameType() {
        Scene scene;
        if (gameMode == GameMode.SINGLE_PLAYER_WITH_RULES) {
            game = injector.getInstance(GameWithRule.class);
            scene = ((GameWithRule) game).createGameScene();
            ((GameWithRule) game).setPrimaryStage(primaryStage);
        } else {
            game = injector.getInstance(GameFree.class);
            scene = ((GameFree) game).createGameScene();
            ((GameFree) game).setPrimaryStage(primaryStage);
        }
        return scene;
    }

    private TextField createTextField() {
        textField = new TextField();
        textField.setEditable(false);
        textField.setText(SELECTED_GAME_MODE + gameModeMap.getOrDefault(gameMode, SINGLE_PLAYER_BOARD_WITH_RULES));
        return textField;
    }

    private MenuBar createMenuBar() {

        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create a menu
        Menu fileMenu = new Menu(GAME_MODE);

        // Create menu items
        MenuItem menuItemwithRules = new MenuItem(SINGLE_PLAYER_BOARD_WITH_RULES);
        menuItemwithRules.setOnAction(event -> handleMenuItemSelected(GameMode.SINGLE_PLAYER_WITH_RULES));
        MenuItem menuItemwithoutRules = new MenuItem(SINGLE_PLAYER_FREE_BOARD);
        menuItemwithoutRules.setOnAction(event -> handleMenuItemSelected(GameMode.SINGLE_PLAYER_FREE));

        // Add menu items to the menu
        fileMenu.getItems().addAll(menuItemwithRules, menuItemwithoutRules);

        // Add the menu to the menu bar
        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }

    private void handleMenuItemSelected(GameMode gameMode) {
        System.out.println(SELECTED_GAME_MODE + gameMode);
        textField.setText( SELECTED_GAME_MODE + gameModeMap.get(gameMode));
        this.gameMode = gameMode;
        updateScene(this.primaryStage);
    }
}
