package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Window window, Board board, int x, int y, Color color, BasePosition basePosition) {
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
                if (!hasFriendPieceAt(i, j) && // No friendly piece
                        (((i == x + 1 || i == x - 1) && (j == y + 1 || j == y - 1)) ||  // Diagonal
                                (y == j && (i == x + 1 || i == x - 1)) || // Horizontal
                                (x == i && (j == y + 1 || j == y - 1)))) { // Vertical
                    moves.add(new Vector2i(i, j));
                }
            }
        }
        return moves;
    }

    /**
     * Check if the king can castle
     * @return an array of 2 booleans, the first one is the left castle, the second one is the right castle
     */
    public boolean[] canCastle() {
        boolean[] canCastle = { true, true };
        int j = getBasePosition() == BasePosition.TOP ? 0 : 7;
        if (!isMoved() && !isChecked() && hasEnemyPieceAt(0, j) && getBoard().getPieceAt(0, j) instanceof Rook rook && !rook.isMoved()) {
            for (int i = 1; i < getX(); i++) {
                if (getBoard().hasPieceAt(i, j)) {
                    canCastle[0] = false;
                    break;
                }
            }
            for (int i = getX() + 1; i < 8; i++) {
                if (getBoard().hasPieceAt(i, j)) {
                    canCastle[1] = false;
                    break;
                }
            }
        }
        return canCastle;
    }

    public boolean isChecked() {
        return false;
    }

}
