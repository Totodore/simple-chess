package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Window window, Board board, int x, int y, Color color, BasePosition basePosition) {
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!hasFriendPieceAt(i, j) && (
                        ((i == x + 1 || i == x - 1) && (j == y + 2 || j == y - 2)) ||
                        ((i == x + 2 || i == x - 2) && (j == y + 1 || j == y - 1))
                )) {
                    moves.add(new Vector2i(i, j));
                }
            }
        }
        return moves;
    }

}
