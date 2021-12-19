package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ColorPieceCombination {
    private Color color;
    private PieceName pieceName;
}
