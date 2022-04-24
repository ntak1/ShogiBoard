package pieces;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exception.InvalidPieceException;
import lombok.RequiredArgsConstructor;
import utils.PieceName;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PieceFactory {
    private final PieceImageLoader pieceImageLoader;

    public Piece getPiece(PieceName pieceName, PieceColor pieceColor) {
        switch (pieceName) {
            case KNIGHT:
                return new Knight(pieceColor);
            case BISHOP:
                return new Bishop(pieceColor);
            case LANCE:
                return new Lance(pieceColor);
            case ROOK:
                return new Rook(pieceColor);
            case PAWN:
                return new Pawn(pieceColor);
            case KING:
                return new King(pieceColor);
            case GOLD:
                return new Gold(pieceColor);
            case SILVER:
                return new Silver(pieceColor);
            case DRAGON_KING:
                return new DragonKing(pieceColor);
            case DRAGON_HORSE:
                return new DragonHorse(pieceColor);
            case PROMOTED_PAWN:
                return new PromotedPawn(pieceColor);
        }
        return null;
    }

    public Piece getPiece(Piece piece, PieceColor pieceColor) {
        String className = piece.getClass().getSimpleName();
        System.out.println(className);

        if (piece instanceof Knight) {
            return new Knight(pieceColor);
        } else if (piece instanceof DragonKing) {
            return new DragonKing(pieceColor);
        } else if (piece instanceof DragonHorse) {
            return new DragonHorse(pieceColor);
        } else if (piece instanceof Bishop) {
            return new Bishop(pieceColor);
        } else if (piece instanceof Lance) {
            return new Lance(pieceColor);
        } else if (piece instanceof Rook) {
            return new Rook(pieceColor);
        } else if (piece instanceof Pawn) {
            return new Pawn(pieceColor);
        } else if (piece instanceof King) {
            return new King(pieceColor);
        } else if (piece instanceof Silver) {
            return new Silver(pieceColor);
        } else if (piece instanceof PromotedPawn) {
            return new Pawn(pieceColor);
        } else if (piece instanceof PromotedKnight) {
            return new Knight(pieceColor);
        } else if (piece instanceof PromotedLance) {
            return new Lance(pieceColor);
        } else if (piece instanceof PromotedSilver) {
            return new Silver(pieceColor);
        } else if (piece instanceof Gold) {
            return new Gold(pieceColor);
        }
        return null;
    }

    public Piece promotePiece(Piece piece) throws InvalidPieceException {
        PieceColor colorPiece = piece.color;
        if (piece instanceof Pawn) {
            return new PromotedPawn(colorPiece);
        } else if (piece instanceof Silver) {
            return new PromotedSilver(colorPiece);
        } else if (piece instanceof Lance) {
            return new PromotedLance(colorPiece);
        } else if (piece instanceof Knight) {
            return new PromotedKnight(colorPiece);
        } else if (piece instanceof Rook) {
            return new DragonKing(colorPiece);
        } else if (piece instanceof Bishop) {
            return new DragonHorse(colorPiece);
        }
        throw new InvalidPieceException();
    }

}
