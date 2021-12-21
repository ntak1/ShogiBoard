package controller;

import lombok.AllArgsConstructor;
import model.Board;
import model.PieceColor;

@AllArgsConstructor
public class Game {
    private PieceColor currentTurn = PieceColor.WHITE;
    private Board board;
    private State state = State.MOVING;
}
