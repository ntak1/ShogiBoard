package module;

import com.google.inject.AbstractModule;
import java.util.HashMap;
import java.util.Map;
import utils.ColorPieceCombination;


public class PiecesModule extends AbstractModule {
    private final Map<ColorPieceCombination, String> colorPieceCombinationStringMap = new HashMap<>();
    private static final String BASE_IMAGE_DIR = "scripts/png/";
    private static final String BLACK_PREFIX = "0";
    private static final String WHITE_PREFIX = "1";

    // TODO refactor this to use Factory pattern
    @Override
    protected void configure() {
        //        PieceImageLoader pieceImageLoader = new PieceImageLoader(colorPieceCombinationStringMap);
        //        // PAWNS
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.PAWN), "scripts/png/1FU.png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.PAWN), "scripts/png/0FU.png");
        //        bind(Pawn.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Pawn(PieceColor.WHITE, pieceImageLoader));
        //        bind(Pawn.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Pawn(PieceColor.BLACK, pieceImageLoader));
        //
        //        // LANCE
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.LANCE), "scripts/png/1KY.png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.LANCE), "scripts/png/0KY.png");
        //        bind(Lance.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Lance(PieceColor.WHITE, pieceImageLoader));
        //        bind(Lance.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Lance(PieceColor.BLACK, pieceImageLoader));
        //
        //        // KNIGHT
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.KNIGHT), "scripts/png/1KE" +
        //                ".png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.KNIGHT), "scripts/png/0KE" +
        //                ".png");
        //        bind(Knight.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Knight(PieceColor.WHITE, pieceImageLoader));
        //        bind(Knight.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Knight(PieceColor.BLACK, pieceImageLoader));
        //
        //        // SILVER
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.SILVER), "scripts/png/1GI" +
        //                ".png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.SILVER), "scripts/png/0GI" +
        //                ".png");
        //        bind(Silver.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Silver(PieceColor.WHITE, pieceImageLoader));
        //        bind(Silver.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Silver(PieceColor.BLACK, pieceImageLoader));
        //
        //        // GOLD
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.GOLD), "scripts/png/1KI" +
        //                ".png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.GOLD), "scripts/png/0KI" +
        //                ".png");
        //        bind(Gold.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Gold(PieceColor.WHITE, pieceImageLoader));
        //        bind(Gold.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Gold(PieceColor.BLACK, pieceImageLoader));
        //
        //        // KING
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.KING), "scripts/png/1OU" +
        //                ".png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.KING), "scripts/png/0OU" +
        //                ".png");
        //        bind(King.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new King(PieceColor.WHITE, pieceImageLoader));
        //        bind(King.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new King(PieceColor.BLACK, pieceImageLoader));
        //
        //        // BISHOP
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.BISHOP), "scripts/png/1KA" +
        //                ".png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.BISHOP), "scripts/png/0KA" +
        //                ".png");
        //        bind(Bishop.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Bishop(PieceColor.WHITE, pieceImageLoader));
        //        bind(Bishop.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Bishop(PieceColor.BLACK, pieceImageLoader));
        //
        //        // ROOK
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.WHITE, PieceName.ROOK), "scripts/png/1HI" +
        //                ".png");
        //        colorPieceCombinationStringMap.put(new ColorPieceCombination(PieceColor.BLACK, PieceName.ROOK), "scripts/png/0HI" +
        //                ".png");
        //        bind(Rook.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Rook(PieceColor.WHITE, pieceImageLoader));
        //        bind(Rook.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Rook(PieceColor.BLACK, pieceImageLoader));
    }

    //    @Provides
    //    @Singleton
    //    public Map<PieceName, String> providesPieceNameStringMap() {
    //        final Map<PieceName, String> pieceNameStringMap = new HashMap<>();
    //        pieceNameStringMap.put(PieceName.PAWN, "FU");
    //        pieceNameStringMap.put(PieceName.SILVER, "KI");
    //        pieceNameStringMap.put(PieceName.KING, "GY");
    //        pieceNameStringMap.put(PieceName.ROOK, "HI");
    //        pieceNameStringMap.put(PieceName.BISHOP, "KA");
    //        pieceNameStringMap.put(PieceName.KNIGHT, "KE");
    //        pieceNameStringMap.put(PieceName.GOLD, "KI");
    //        pieceNameStringMap.put(PieceName.LANCE, "KY");
    //        pieceNameStringMap.put(PieceName.PROMOTED_PAWN, "TO");
    //        pieceNameStringMap.put(PieceName.DRAGON_HORSE, "UM");
    //        pieceNameStringMap.put(PieceName.DRAGON_KING, "RY");
    //        return pieceNameStringMap;
    //    }
    //
    //    @Provides
    //    @Singleton
    //    public Map<ColorPieceCombination, String> providesColorPieceCombination(Map<PieceName, String> pieceNameStringMap) {
    //        final Map<ColorPieceCombination, String> colorPieceCombinationStringMap = new HashMap<>();
    //        for (Map.Entry<PieceName, String> element : pieceNameStringMap.entrySet()) {
    //            ColorPieceCombination colorPieceCombination = new ColorPieceCombination(PieceColor.BLACK, element.getKey());
    //            String imagePath = BASE_IMAGE_DIR + BLACK_PREFIX + element.getValue();
    //            colorPieceCombinationStringMap.put(colorPieceCombination, imagePath);
    //
    //            colorPieceCombination = new ColorPieceCombination(PieceColor.BLACK, element.getKey());
    //            imagePath = BASE_IMAGE_DIR + WHITE_PREFIX + element.getValue();
    //            colorPieceCombinationStringMap.put(colorPieceCombination, imagePath);
    //        }
    //        return colorPieceCombinationStringMap;
    //    }

    //    @Provides
    //    @Singleton
    //    public PieceImageLoader pieceImageLoader() {
    //
    //    }

    //    @Provides
    //    @Singleton
    //    public PieceFactory providesPieceFactory(PieceImageLoader pieceImageLoader) {
    //        return new PieceFactory(pieceImageLoader);
    //    }
}

