package pieces;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pieces.PieceColor;
import utils.PieceName;

@Data
@Builder
@AllArgsConstructor
public class ColorPieceCombination {
    private PieceColor color;
    private PieceName pieceName;
}
