package model;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.util.HashMap;
import java.util.Map;

public class PiecesModule extends AbstractModule {

    @Override
    protected void configure() { }

    @Provides
    @Singleton
    public Map<PieceName, Piece> providesPieces() {
        Map<PieceName, Piece> pieces = new HashMap<>();
        pieces.put(PieceName.PAWN, new Pawn());
        return pieces;
    }

    @Provides
    @Singleton
    @Named("test")
    public String test() {
        return "Testing DI with Google Guice";
    }
}
