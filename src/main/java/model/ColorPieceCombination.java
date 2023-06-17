package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.pieces.PieceColor;

@Data
@Builder
@AllArgsConstructor
public class ColorPieceCombination {
    private PieceColor color;
    private PieceName pieceName;
}
