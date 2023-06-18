package controller.game;

import javafx.scene.input.MouseEvent;
import view.Cell;


public interface HandleOnClick {
    void handleOnClick(Cell cell, MouseEvent event);
}
