package fr.scriptis.simplechess.entities.chess;

import java.awt.Color;

public enum PieceColor {
    BLACK(0x6A333333),
    WHITE(0x6AB1B3DC);

    public final Color color;
    PieceColor(int color) {
        this.color = new Color(color, true);
    }
}
