package model.board;

import lombok.Data;
import model.Coord;
import model.pieces.PieceColor;

@Data
public class BoardConstants {
    public static final int N_COLUMNS = 9;
    public static final int N_ROWS = 9;
    public static final int CAPTURED_AREA_N_ROWS = 2;

    public static boolean isPromotableArea(Coord coord, PieceColor color) {
        return (color == PieceColor.BLACK && coord.getHeight() >= 0 && coord.getHeight() < 3)
                || (color == PieceColor.WHITE && coord.getHeight() >= 6 && coord.getHeight() <= 8);
    }

    public static boolean isLastLine(Coord coord, PieceColor color) {
        return ((color == PieceColor.BLACK && coord.getHeight() == 0) || (color == PieceColor.WHITE && coord.getHeight() == 8));
    }
}
