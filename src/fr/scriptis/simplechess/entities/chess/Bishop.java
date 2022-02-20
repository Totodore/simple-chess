package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;

public class Bishop extends Piece {
    public Bishop(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
        System.out.println("Bishop created at " + x + " " + y);
    }

    @Override
    public void init() {

    }

    @Override
    protected boolean isPossibleMove(Piece[][] board, int i, int j) {
        return (i - getX() == j - getY()) || (i - getX() == -(j - getY()));
    }
}
