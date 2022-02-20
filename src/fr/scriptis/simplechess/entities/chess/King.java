package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
    }

    @Override
    public void init() {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    protected boolean isPossibleMove(Piece[][] board, int i, int j) {
        int x = this.getX();
        int y = this.getY();
        return (i == x + 1 || i == x - 1) || (j == y + 1 || j == y - 1) || (i == x + 1 && j == y - 1) || (i == x - 1 && j == y - 1) || (i == x + 1 && j == y + 1) || (i == x - 1 && j == y + 1);
    }
}
