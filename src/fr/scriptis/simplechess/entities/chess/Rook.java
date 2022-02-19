package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.util.List;
import java.util.Vector;

public class Rook extends Piece {
    public Rook(Window window, Board board, int x, int y, Color color) {
        super(window, board, x, y, color);
    }

    @Override
    public void init() {

    }


    @Override
    public List<Vector> getPossibleMoves(Piece[][] pieces) {
        return null;
    }
}
