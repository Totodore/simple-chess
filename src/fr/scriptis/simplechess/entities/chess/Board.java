package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.windows.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

public class Board extends Entity {

    public static final float CELL_SIZE = (float) (685 / 8);
    public static final Color WHITE_COLOR = new Color(0x8CB1B3DC, true);
    public static final Color BLACK_COLOR = new Color(0xFF333333, true);
    private final static Class<? extends Piece>[] PIECES = new Class[]{
            Rook.class, Knight.class, Bishop.class, Queen.class, King.class,
            Bishop.class, Knight.class, Rook.class, Pawn.class
    };
    private final Piece[][] pieces = new Piece[8][8];

    public Board(Window window) {
        super(window);
    }

    public static void load() {
        Logger.getGlobal().info("Loading chess board");
        for(int i = 0; i < PIECES.length; i++) {
            Piece.load(PIECES[i]);
        }
    }

    @Override
    public void init() {
        setX(174);
        setY(50);
        setWidth(685);
        setHeight(685);
        initializePieces();
    }

    @Override
    public void draw(Graphics2D g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor((i + j) % 2 == 0 ? WHITE_COLOR : BLACK_COLOR);
                g.fillRect((int) (getX() + i * CELL_SIZE), (int) (getY() + j * CELL_SIZE), (int) CELL_SIZE, (int) CELL_SIZE);
                if (pieces[i][j] != null) {
                    pieces[i][j].draw(g);
                }
            }
        }
    }

    /**
     * Generate a chess board with pieces
     * The white are at the top and the black are at the bottom
     */
    private void initializePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 1 || j == 6) {
                    pieces[i][j] = new Pawn(window, this, i, j, i == 1 ? Color.WHITE : Color.BLACK);
                } else if (j == 0 || j == 7) {
                    if (i == 0 || i == 7) {
                        pieces[i][j] = new Rook(window, this, i, j, j == 0 ? Color.WHITE : Color.BLACK);
                    } else if (i == 1 || i == 6) {
                        pieces[i][j] = new Knight(window, this, i, j, j == 0 ? Color.WHITE : Color.BLACK);
                    } else if (i == 2 || i == 5) {
                        pieces[i][j] = new Bishop(window, this, i, j, j == 0 ? Color.WHITE : Color.BLACK);
                    } else if (i == 3) {
                        pieces[i][j] = new Queen(window, this, i, j, j == 0 ? Color.WHITE : Color.BLACK);
                    } else {
                        pieces[i][j] = new King(window, this, i, j, j == 0 ? Color.WHITE : Color.BLACK);
                    }
                }
            }
        }
    }
}
