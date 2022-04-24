package pieces;


import com.google.inject.Singleton;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import utils.ColorPieceCombination;
import utils.PieceName;

@Singleton
public class PieceImageLoader {
    private static final Map<ColorPieceCombination, String> colorPieceCombinationStringMap = providesColorPieceCombination(
            providesPieceNameStringMap());
    private static final String BASE_IMAGE_DIR = "scripts/png/";
    private static final String BLACK_PREFIX = "0";
    private static final String WHITE_PREFIX = "1";

    public static ImagePattern getImagePattern(PieceName pieceName, PieceColor color) {
        final ColorPieceCombination colorPieceCombination = new ColorPieceCombination(color, pieceName);
        final String file = colorPieceCombinationStringMap.get(colorPieceCombination);
        final File tileFile = new File(file);
        Image image = new Image(tileFile.toURI().toString());
        return new ImagePattern(image);
    }

    private static Map<PieceName, String> providesPieceNameStringMap() {
        final Map<PieceName, String> pieceNameStringMap = new HashMap<>();
        pieceNameStringMap.put(PieceName.PAWN, "FU");
        pieceNameStringMap.put(PieceName.SILVER, "KI");
        pieceNameStringMap.put(PieceName.KING, "GY");
        pieceNameStringMap.put(PieceName.ROOK, "HI");
        pieceNameStringMap.put(PieceName.BISHOP, "KA");
        pieceNameStringMap.put(PieceName.KNIGHT, "KE");
        pieceNameStringMap.put(PieceName.GOLD, "GI");
        pieceNameStringMap.put(PieceName.LANCE, "KY");
        pieceNameStringMap.put(PieceName.PROMOTED_PAWN, "TO");
        pieceNameStringMap.put(PieceName.DRAGON_HORSE, "UM");
        pieceNameStringMap.put(PieceName.DRAGON_KING, "RY");
        pieceNameStringMap.put(PieceName.PROMOTED_KNIGHT, "NG");
        pieceNameStringMap.put(PieceName.PROMOTED_LANCE, "NG");
        pieceNameStringMap.put(PieceName.PROMOTED_SILVER, "NG");
        return pieceNameStringMap;
    }

    private static Map<ColorPieceCombination, String> providesColorPieceCombination(Map<PieceName, String> pieceNameStringMap) {
        final Map<ColorPieceCombination, String> colorPieceCombinationStringMap = new HashMap<>();
        for (Map.Entry<PieceName, String> element : pieceNameStringMap.entrySet()) {
            ColorPieceCombination colorPieceCombination = new ColorPieceCombination(PieceColor.BLACK, element.getKey());
            String imagePath = BASE_IMAGE_DIR + BLACK_PREFIX + element.getValue() + ".png";
            colorPieceCombinationStringMap.put(colorPieceCombination, imagePath);

            colorPieceCombination = new ColorPieceCombination(PieceColor.WHITE, element.getKey());
            imagePath = BASE_IMAGE_DIR + WHITE_PREFIX + element.getValue() + ".png";
            colorPieceCombinationStringMap.put(colorPieceCombination, imagePath);
        }
        return colorPieceCombinationStringMap;
    }
}
