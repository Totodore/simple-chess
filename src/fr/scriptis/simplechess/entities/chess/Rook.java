package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Window window, Board board, int x, int y, Color color, BasePosition basePosition) {
        super(window, board, x, y, color, basePosition);
    }

    @Override
    public void init() {

    }

    @Override
    public List<Vector2i> getPossibleMoves() {
        List<Vector2i> moves = new ArrayList<>();
        int x = this.getX();
        int y = this.getY();
        for (int i = x + 1; i < 8; i++) {
            if (!hasFriendPieceAt(i, y))
                moves.add(new Vector2i(i, y));
            if (getBoard().hasPieceAt(i, y))
                break;
        }
        for (int i = x - 1; i >= 0; i--) {
            if (!hasFriendPieceAt(i, y))
                moves.add(new Vector2i(i, y));
            if (getBoard().hasPieceAt(i, y))
                break;
        }
        for (int j = y + 1; j < 8; j++) {
            if (!hasFriendPieceAt(x, j))
                moves.add(new Vector2i(x, j));
            if (getBoard().hasPieceAt(x, j))
                break;
        }
        for (int j = y - 1; j >= 0; j--) {
            if (!hasFriendPieceAt(x, j))
                moves.add(new Vector2i(x, j));
            if (getBoard().hasPieceAt(x, j))
                break;
        }
        return moves;
    }
}
