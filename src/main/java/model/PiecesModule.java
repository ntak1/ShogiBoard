package model;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import java.util.HashMap;
import java.util.Map;


public class PiecesModule extends AbstractModule {
    private static final int N_COLUMNS = 9;
    private static final int N_ROWS = 9;
    private Map<ColorPieceCombination, String> colorPieceCombinationStringMap = new HashMap<>();


    @Override
    protected void configure() {
        PieceImageLoader pieceImageLoader = new PieceImageLoader(colorPieceCombinationStringMap);
        // PAWNS
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.PAWN), "scripts/png/1FU.png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.PAWN), "scripts/png/0FU.png");
        bind(Pawn.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Pawn(Color.WHITE, pieceImageLoader));
        bind(Pawn.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Pawn(Color.BLACK, pieceImageLoader));

        // LANCE
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.LANCE), "scripts/png/1KY.png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.LANCE), "scripts/png/0KY.png");
        bind(Lance.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Lance(Color.WHITE, pieceImageLoader));
        bind(Lance.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Lance(Color.BLACK, pieceImageLoader));

        // KNIGHT
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.KNIGHT), "scripts/png/1KE" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.KNIGHT), "scripts/png/0KE" +
                ".png");
        bind(Knight.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Knight(Color.WHITE, pieceImageLoader));
        bind(Knight.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Knight(Color.BLACK, pieceImageLoader));

        // SILVER
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.SILVER), "scripts/png/1GI" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.SILVER), "scripts/png/0GI" +
                ".png");
        bind(Silver.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Silver(Color.WHITE, pieceImageLoader));
        bind(Silver.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Silver(Color.BLACK, pieceImageLoader));

        // GOLD
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.GOLD), "scripts/png/1KI" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.GOLD), "scripts/png/0KI" +
                ".png");
        bind(Gold.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Gold(Color.WHITE, pieceImageLoader));
        bind(Gold.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Gold(Color.BLACK, pieceImageLoader));

        // KING
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.KING), "scripts/png/1OU" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.KING), "scripts/png/0OU" +
                ".png");
        bind(King.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new King(Color.WHITE, pieceImageLoader));
        bind(King.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new King(Color.BLACK, pieceImageLoader));

        // BISHOP
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.BISHOP), "scripts/png/1KA" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.BISHOP), "scripts/png/0KA" +
                ".png");
        bind(Bishop.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Bishop(Color.WHITE, pieceImageLoader));
        bind(Bishop.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Bishop(Color.BLACK, pieceImageLoader));

        // ROOK
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.WHITE, PieceName.ROOK), "scripts/png/1HI" +
                ".png");
        colorPieceCombinationStringMap.put(new ColorPieceCombination(Color.BLACK, PieceName.ROOK), "scripts/png/0HI" +
                ".png");
        bind(Rook.class).annotatedWith(Names.named("WHITE")).toProvider(() -> new Rook(Color.WHITE, pieceImageLoader));
        bind(Rook.class).annotatedWith(Names.named("BLACK")).toProvider(() -> new Rook(Color.BLACK, pieceImageLoader));
    }


    @Provides
    public Piece[][] providedBusinessBoard() {
        return new Piece[N_ROWS][N_COLUMNS];
    }

    @Provides
    public Board providesBoard(Piece[][] businessBoard) {
        return new Board(businessBoard);
    }

    @Provides
    @Singleton
    public Map<ColorPieceCombination, String> providesColorPieceCombinationMap() {
        return colorPieceCombinationStringMap;
    }
}
