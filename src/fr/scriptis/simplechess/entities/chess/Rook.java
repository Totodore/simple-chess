package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;

public class Rook extends Piece {
    public Rook(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
    }

    @Override
    public void init() {

    }


    @Override
    protected boolean isPossibleMove(Piece[][] board, int i, int j) {
        return i == getX() && j == getY();
    }
}
