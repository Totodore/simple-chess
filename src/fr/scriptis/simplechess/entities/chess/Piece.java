package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.managers.AssetsManager;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;
import java.util.Vector;

public abstract class Piece extends Entity {

    protected final Board board;
    @Getter
    protected Color color;

    public Piece(Window window, Board board, int x, int y, Color color) {
        super(window);
        this.board = board;
        this.color = color;
        setX(x);
        setY(y);
        setHeight((int) Board.CELL_SIZE);
        setWidth((int) Board.CELL_SIZE);
    }

    public abstract List<Vector> getPossibleMoves(Piece[][] pieces);

    @Override
    public void draw(Graphics2D g) {
        int x = (int) (board.getX() + Board.CELL_SIZE * getX());
        int y = (int) (board.getY() + Board.CELL_SIZE * getY());
        g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
    }

    public static void load(Class<? extends Piece> piece) {
        System.out.println("Loading " + piece.getSimpleName());
        assetsManager.load(piece.getSimpleName() + ".svg", (int) Board.CELL_SIZE, (int) Board.CELL_SIZE);
    }

    public BufferedImage getImage() {
        return assetsManager.getImageAsset(getClass().getSimpleName() + ".svg");
    }
}
