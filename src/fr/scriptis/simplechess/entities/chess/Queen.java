package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Window window, Board board, int x, int y, PieceColor color, BasePosition basePosition) {
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

        // Lateral and horizontal moves
        for (int i = x + 1; i < 8; i++) {
            moves.add(new Vector2i(i, y));
            if (getBoard().hasPieceAt(i, y))
                break;
        }
        for (int i = x - 1; i >= 0; i--) {
            moves.add(new Vector2i(i, y));
            if (getBoard().hasPieceAt(i, y))
                break;
        }
        for (int j = y + 1; j < 8; j++) {
            moves.add(new Vector2i(x, j));
            if (getBoard().hasPieceAt(x, j))
                break;
        }
        for (int j = y - 1; j >= 0; j--) {
            moves.add(new Vector2i(x, j));
            if (getBoard().hasPieceAt(x, j))
                break;
        }

        // Diagonal moves (up-left, up-right, down-left, down-right)
        boolean[] blocked = { false, false, false, false };
        for (int i = 1; i < 8 - x; i++) {
            if (blocked[2] || getBoard().hasPieceAt(x + i, x - i)) {
                blocked[2] = true;
            } else
                moves.add(new Vector2i(x + i, y + i));
            if (blocked[1] || getBoard().hasPieceAt(x + i, y - i)) {
                blocked[1] = true;
            } else
                moves.add(new Vector2i(x + i, y - i));

        }
        for (int i = x - 1; i >= 0; i--) {
            if (blocked[3] || getBoard().hasPieceAt(i, x - i + y)) {
                blocked[3] = true;
            } else
                moves.add(new Vector2i(i, x - i + y));
            if (blocked[0] || getBoard().hasPieceAt(i, y - (x - i))) {
                blocked[0] = true;
            } else
                moves.add(new Vector2i(i, y - (x - i)));
        }

        return moves;
    }

    public String getPieceId() {
        return color == PieceColor.WHITE ? "Q" : "q";
    }
}
