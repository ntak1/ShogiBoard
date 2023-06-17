package model.pieces;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.exception.InvalidPieceException;
import lombok.RequiredArgsConstructor;
import model.PieceName;
import view.PieceView;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PieceFactory {

    public PieceView getPiece(PieceName pieceName, PieceColor pieceColor) {
        switch (pieceName) {
            case KNIGHT:
                return new PieceView(new Knight(pieceColor));
            case BISHOP:
                return new PieceView(new Bishop(pieceColor));
            case LANCE:
                return new PieceView(new Lance(pieceColor));
            case ROOK:
                return new PieceView(new Rook(pieceColor));
            case PAWN:
                return new PieceView(new Pawn(pieceColor));
            case KING:
                return new PieceView(new King(pieceColor));
            case GOLD:
                return new PieceView(new Gold(pieceColor));
            case SILVER:
                return new PieceView(new Silver(pieceColor));
            case DRAGON_KING:
                return new PieceView(new DragonKing(pieceColor));
            case DRAGON_HORSE:
                return new PieceView(new DragonHorse(pieceColor));
            case PROMOTED_PAWN:
                return new PieceView(new PromotedPawn(pieceColor));
        }
        return null;
    }

    public PieceView getPiece(PieceView pieceView, PieceColor pieceColor) {
        Piece piece = pieceView.getPiece();
        String className = piece.getClass().getSimpleName();
        System.out.println(className);

        if (piece instanceof Knight) {
            return new PieceView(new Knight(pieceColor));
        } else if (piece instanceof DragonKing) {
            return new PieceView(new DragonKing(pieceColor));
        } else if (piece instanceof DragonHorse) {
            return new PieceView(new DragonHorse(pieceColor));
        } else if (piece instanceof Bishop) {
            return new PieceView(new Bishop(pieceColor));
        } else if (piece instanceof Lance) {
            return new PieceView(new Lance(pieceColor));
        } else if (piece instanceof Rook) {
            return new PieceView(new Rook(pieceColor));
        } else if (piece instanceof Pawn) {
            return new PieceView(new Pawn(pieceColor));
        } else if (piece instanceof King) {
            return new PieceView(new King(pieceColor));
        } else if (piece instanceof Silver) {
            return new PieceView(new Silver(pieceColor));
        } else if (piece instanceof PromotedPawn) {
            return new PieceView(new Pawn(pieceColor));
        } else if (piece instanceof PromotedKnight) {
            return new PieceView(new Knight(pieceColor));
        } else if (piece instanceof PromotedLance) {
            return new PieceView(new Lance(pieceColor));
        } else if (piece instanceof PromotedSilver) {
            return new PieceView(new Silver(pieceColor));
        } else if (piece instanceof Gold) {
            return new PieceView(new Gold(pieceColor));
        }
        return null;
    }

    public PieceView promotePiece(PieceView pieceView) throws InvalidPieceException {
        Piece piece = pieceView.getPiece();
        PieceColor colorPiece = piece.color;
        if (piece instanceof Pawn) {
            return new PieceView(new PromotedPawn(colorPiece));
        } else if (piece instanceof Silver) {
            return new PieceView(new PromotedSilver(colorPiece));
        } else if (piece instanceof Lance) {
            return new PieceView(new PromotedLance(colorPiece));
        } else if (piece instanceof Knight) {
            return new PieceView(new PromotedKnight(colorPiece));
        } else if (piece instanceof Rook) {
            return new PieceView(new DragonKing(colorPiece));
        } else if (piece instanceof Bishop) {
            return new PieceView(new DragonHorse(colorPiece));
        }
        throw new InvalidPieceException();
    }

}
