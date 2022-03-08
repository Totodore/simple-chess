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
        boolean[] blocked = {false, false, false, false};
        for (int i = 1; i <= Math.max(x, y); i++) {
            // top left diagonal
            int xx1 = x - i;
            int yy1 = y - i;
            if ((!blocked[0] && getBoard().hasPieceAt(xx1, yy1)) || !getBoard().isInBoard(xx1, yy1)) {
                if (hasEnemyPieceAt(xx1, yy1))
                    moves.add(new Vector2i(xx1, yy1));
                blocked[0] = true;
            } else if (!blocked[0])
                moves.add(new Vector2i(xx1, yy1));

            // top right diagonal
            int xx2 = x + i;
            int yy2 = y - i;
            if ((!blocked[1] && getBoard().hasPieceAt(xx2, yy2)) || !getBoard().isInBoard(xx2, yy2)) {
                if (hasEnemyPieceAt(xx2, yy2))
                    moves.add(new Vector2i(xx2, yy2));
                blocked[1] = true;
            } else if (!blocked[1])
                moves.add(new Vector2i(xx2, yy2));

            // bottom left diagonal
            int xx3 = x - i;
            int yy3 = y + i;
            if ((!blocked[2] && getBoard().hasPieceAt(xx3, yy3)) || !getBoard().isInBoard(xx3, yy3)) {
                if (hasEnemyPieceAt(xx3, yy3))
                    moves.add(new Vector2i(xx3, yy3));
                blocked[2] = true;
            } else if (!blocked[2])
                moves.add(new Vector2i(xx3, yy3));

            // bottom right diagonal
            int xx4 = x + i;
            int yy4 = y + i;
            if ((!blocked[3] && getBoard().hasPieceAt(xx4, yy4)) || !getBoard().isInBoard(xx4, yy4)) {
                if (hasEnemyPieceAt(xx4, yy4))
                    moves.add(new Vector2i(xx4, yy4));
                blocked[3] = true;
            } else if (!blocked[3])
                moves.add(new Vector2i(xx4, yy4));
        }
        return moves;
    }

    public String getPieceId() {
        return color == PieceColor.WHITE ? "Q" : "q";
    }
}
