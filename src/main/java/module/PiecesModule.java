package module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import java.util.HashMap;
import java.util.Map;
import pieces.Bishop;
import pieces.ColorPieceCombination;
import pieces.Gold;
import pieces.King;
import pieces.Knight;
import pieces.Lance;
import pieces.Pawn;
import pieces.PieceColor;
import pieces.Rook;
import pieces.Silver;
import utils.PieceImageLoader;
import utils.PieceName;


public class PiecesModule extends AbstractModule {
    private final Map<ColorPieceCombination, String> colorPieceCombinationStringMap = new HashMap<>();


    @Override
    protected void configure() {
        PieceImageLoader pieceImageLoader = new PieceImageLoader(colorPieceCombinationStringMap);
        // PAWNS
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.PAWN), "scripts/png/1FU.png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.PAWN), "scripts/png/0FU.png");
        bind(Pawn.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Pawn(PieceColor.WHITE, pieceImageLoader));
        bind(Pawn.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Pawn(PieceColor.BLACK, pieceImageLoader));

        // LANCE
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.LANCE), "scripts/png/1KY.png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.LANCE), "scripts/png/0KY.png");
        bind(Lance.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Lance(PieceColor.WHITE, pieceImageLoader));
        bind(Lance.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Lance(PieceColor.BLACK, pieceImageLoader));

        // KNIGHT
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.KNIGHT), "scripts/png/1KE" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.KNIGHT), "scripts/png/0KE" +
                ".png");
        bind(Knight.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Knight(PieceColor.WHITE, pieceImageLoader));
        bind(Knight.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Knight(PieceColor.BLACK, pieceImageLoader));

        // SILVER
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.SILVER), "scripts/png/1GI" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.SILVER), "scripts/png/0GI" +
                ".png");
        bind(Silver.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Silver(PieceColor.WHITE, pieceImageLoader));
        bind(Silver.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Silver(PieceColor.BLACK, pieceImageLoader));

        // GOLD
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.GOLD), "scripts/png/1KI" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.GOLD), "scripts/png/0KI" +
                ".png");
        bind(Gold.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Gold(PieceColor.WHITE, pieceImageLoader));
        bind(Gold.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Gold(PieceColor.BLACK, pieceImageLoader));

        // KING
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.KING), "scripts/png/1OU" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.KING), "scripts/png/0OU" +
                ".png");
        bind(King.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new King(PieceColor.WHITE, pieceImageLoader));
        bind(King.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new King(PieceColor.BLACK, pieceImageLoader));

        // BISHOP
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.BISHOP), "scripts/png/1KA" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.BISHOP), "scripts/png/0KA" +
                ".png");
        bind(Bishop.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Bishop(PieceColor.WHITE, pieceImageLoader));
        bind(Bishop.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Bishop(PieceColor.BLACK, pieceImageLoader));

        // ROOK
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.ROOK), "scripts/png/1HI" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.ROOK), "scripts/png/0HI" +
                ".png");
        bind(Rook.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Rook(PieceColor.WHITE, pieceImageLoader));
        bind(Rook.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Rook(PieceColor.BLACK, pieceImageLoader));
    }

    @Provides
    @Singleton
    public Map<ColorPieceCombination, String> providesColorPieceCombinationMap() {
        return colorPieceCombinationStringMap;
    }
}
