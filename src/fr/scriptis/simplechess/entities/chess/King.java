package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Window window, Board board, int x, int y, PieceColor color, BasePosition basePosition) {
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
        boolean[] canCastle = canCastle();
        if (canCastle[0]) {
            moves.add(new Vector2i(2, y));
            moves.add(new Vector2i(0, y));
        }
        if (canCastle[1]) {
            moves.add(new Vector2i(6, y));
            moves.add(new Vector2i(7, y));
        }
        return moves;
    }

    /**
     * Check if the king can castle
     * @return an array of 2 booleans, the first one is the left castle, the second one is the right castle
     */
    public boolean[] canCastle() {
        if (isMoved() || isChecked())
            return new boolean[] { false, false };
        int j = getBasePosition() == BasePosition.TOP ? 0 : 7;
        boolean[] canCastle = { true, true };
        if (getBoard().getPieceAt(0, j) instanceof Rook rook && !rook.isMoved()) {
            // Left castle row check
            for (int i = getX() - 1; i > 0; i--) {
                if (getBoard().hasPieceAt(i, j)) {
                    canCastle[0] = false;
                    break;
                }
            }
        } else canCastle[0] = false;
        if (getBoard().getPieceAt(7, j) instanceof Rook rook && !rook.isMoved()) {
            // Right castle row check
            for (int i = getX() + 1; i < 7; i++) {
                if (getBoard().hasPieceAt(i, j)) {
                    canCastle[1] = false;
                    break;
                }
            }
        } else canCastle[1] = false;
        return canCastle;
    }

    public boolean isChecked() {
        return false;
    }

    public String getPieceId() {
        return color == PieceColor.WHITE ? "K" : "k";
    }

}
