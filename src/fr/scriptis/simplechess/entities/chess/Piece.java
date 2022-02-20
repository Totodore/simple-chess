package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece extends Entity {

    protected final Board board;
    @Getter
    @NotNull
    protected final Color color;

    public Piece(Window window, Board board, int x, int y, Color color) {
        super(window);
        this.board = board;
        this.color = color;
        logger.info("Creating " + (color == Board.WHITE_COLOR ? "white " : "black ") + getClass().getSimpleName() + " at " + x + "," + y);
        setPosition(new Vector2i(x, y));
        setHeight(Board.CELL_SIZE);
        setWidth(Board.CELL_SIZE);
    }

    public List<Vector2i> getPossibleMoves(Piece[][] board) {
        List<Vector2i> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null && isPossibleMove(board, i, j)) {
                    moves.add(new Vector2i(i, j));
                }
            }
        }
        return moves;
    }

    protected abstract boolean isPossibleMove(Piece[][] board, int i, int j);

    @Override
    public void draw(Graphics2D g) {
        int x = board.getX() + (Board.CELL_SIZE * getX());
        int y = board.getY() + (Board.CELL_SIZE * getY());
        g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
    }

    public static void load(Class<? extends Piece> piece) {
        System.out.println("Loading " + piece.getSimpleName());
        assetsManager.load("chess/W_" + piece.getSimpleName() + ".svg", Board.CELL_SIZE, Board.CELL_SIZE);
        assetsManager.load("chess/B_" + piece.getSimpleName() + ".svg", Board.CELL_SIZE, Board.CELL_SIZE);
    }

    public BufferedImage getImage() {
        return assetsManager.getImageAsset(getImageId());
    }

    public String getImageId() {
        return "chess/" + getImageColorId() + "_" + getClass().getSimpleName() + ".svg";
    }

    public String getImageColorId() {
        return color == Board.WHITE_COLOR ? "W" : "B";
    }
}
