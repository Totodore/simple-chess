package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;

public class Pawn extends Piece {

    public Pawn(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
    }

    @Override
    public void init() {

    }


    @Override
    protected boolean isPossibleMove(Piece[][] board, int i, int j) {
        int x = getX();
        int y = getY();
        return (i == x + 1 || i == x - 1 || i == x) && j == y + 1;
    }
}
