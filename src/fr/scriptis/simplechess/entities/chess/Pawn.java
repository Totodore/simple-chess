package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Window window, Board board, int x, int y, Color color, BasePosition basePosition) {
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
        int offset = getBasePosition() == BasePosition.BOTTOM ? -1 : 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean hasEnemyPiece = hasEnemyPieceAt(i, j);
                if ((!hasFriendPieceAt(i, j)) &&
                    (j == y + offset && ((!hasEnemyPiece && i == x) || (hasEnemyPiece && i == x + 1) || (hasEnemyPiece && i == x - 1))) ||
                    (!isMoved() && j == y + offset * 2 && i == x && !hasEnemyPieceAt(i, j + offset * 2))) {
                    moves.add(new Vector2i(i, j));
                }
            }
        }
        return moves;
    }
}
