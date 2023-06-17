package view;

import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import model.Coord;
import model.pieces.Move;
import model.pieces.Piece;
import model.pieces.PieceColor;
import model.pieces.PieceImageLoader;
import view.board.MainBoardView;

import javax.annotation.Nonnull;
import java.util.Set;

public class PieceView implements ImageLoader, Move {
    @Getter
    private final Piece piece;

    public PieceView(@Nonnull Piece piece) {
        this.piece = piece;
    }

    public PieceColor getColor() {
        return piece.getColor();
    }

    @Override
    public ImagePattern getImage() {
        return PieceImageLoader.getImagePattern(piece.getPieceName(), piece.getColor());
    }

    @Override
    public Set<Coord> getPossibleMovements(Coord coord) {
        return piece.getPossibleMovements(coord);
    }

    @Override
    public void setCaptured(boolean captured) {
        piece.setCaptured(captured);
    }

    @Override
    public boolean isCaptured() {
        return piece.isCaptured();
    }

    public void setBoard(MainBoardView mainBoardView) {
        piece.setBoard(mainBoardView);
    }
}
