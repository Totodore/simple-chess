package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Pawn extends Piece {

    public Pawn(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
    }

    @Override
    public void init() {

    }


    @Override
    public List<Vector> getPossibleMoves(Piece[][] board) {
        List<Vector> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && (i == getX() + 1 || i == getX() - 1) && j == getY() - 1) {
                    moves.add(new Vector(i, j));
                }
            }
        }
        return moves;
    }
}
