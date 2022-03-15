package utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pieces.PieceColor;

@Data
@Builder
@AllArgsConstructor
public class ColorPieceCombination {
    private PieceColor color;
    private PieceName pieceName;
}
