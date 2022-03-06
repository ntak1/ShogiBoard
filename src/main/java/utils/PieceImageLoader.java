package utils;


import java.io.File;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import lombok.AllArgsConstructor;
import pieces.ColorPieceCombination;
import pieces.PieceColor;

@AllArgsConstructor
public class PieceImageLoader {
    private final Map<ColorPieceCombination, String> colorPieceCombinationStringMap;

    public ImagePattern getImagePattern(PieceName pieceName, PieceColor color) {
        final ColorPieceCombination colorPieceCombination = new ColorPieceCombination(color, pieceName);
        final String file = colorPieceCombinationStringMap.get(colorPieceCombination);
        final File tileFile = new File(file);
        Image image = new Image(tileFile.toURI().toString());
        return new ImagePattern(image);
    }
}