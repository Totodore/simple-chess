package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
    }

    @Override
    public void init() {

    }

    @Override
    protected boolean isPossibleMove(Piece[][] board, int i, int j) {
        int x = this.getX();
        int y = this.getY();
        return (i == x + 1 || i == x - 1) && (j == y + 2 || j == y - 2);
    }
}
