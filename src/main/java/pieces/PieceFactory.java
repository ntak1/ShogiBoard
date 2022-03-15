package pieces;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import utils.PieceName;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PieceFactory {
    private final PieceImageLoader pieceImageLoader;

    public Piece getPiece(PieceName pieceName, PieceColor pieceColor) {
        switch (pieceName) {
            case KNIGHT:
                return new Knight(pieceColor, pieceImageLoader);
            case BISHOP:
                return new Bishop(pieceColor, pieceImageLoader);
            case LANCE:
                return new Lance(pieceColor, pieceImageLoader);
            case ROOK:
                return new Rook(pieceColor, pieceImageLoader);
            case PAWN:
                return new Pawn(pieceColor, pieceImageLoader);
            case KING:
                return new King(pieceColor, pieceImageLoader);
            case GOLD:
                return new Gold(pieceColor, pieceImageLoader);
            case SILVER:
                return new Silver(pieceColor, pieceImageLoader);
            case DRAGON_KING:
                return new DragonKing(pieceColor, pieceImageLoader);
            case DRAGON_HORSE:
                return new DragonHorse(pieceColor, pieceImageLoader);
            case PROMOTED_PAWN:
                return new PromotedPawn(pieceColor, pieceImageLoader);
        }
        return null;
    }

    public Piece getPiece(Piece piece, PieceColor pieceColor) {
        String className = piece.getClass().getSimpleName();
        System.out.println(className);

        if (piece instanceof Knight) {
            return new Knight(pieceColor, pieceImageLoader);
        } else if (piece instanceof Bishop) {
            return new Bishop(pieceColor, pieceImageLoader);
        } else if (piece instanceof Lance) {
            return new Lance(pieceColor, pieceImageLoader);
        } else if (piece instanceof Rook) {
            return new Rook(pieceColor, pieceImageLoader);
        } else if (piece instanceof Pawn) {
            return new Pawn(pieceColor, pieceImageLoader);
        } else if (piece instanceof King) {
            return new King(pieceColor, pieceImageLoader);
        } else if (piece instanceof Gold) {
            return new Gold(pieceColor, pieceImageLoader);
        } else if (piece instanceof Silver) {
            return new Silver(pieceColor, pieceImageLoader);
        } else if (piece instanceof DragonKing) {
            return new DragonKing(pieceColor, pieceImageLoader);
        } else if (piece instanceof DragonHorse) {
            return new DragonHorse(pieceColor, pieceImageLoader);
        } else if (piece instanceof PromotedPawn) {
            return new PromotedPawn(pieceColor, pieceImageLoader);
        }
        return null;
    }

}
