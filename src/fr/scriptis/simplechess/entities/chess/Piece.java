package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Piece extends Entity {

    @Getter
    private final Board board;
    @Getter
    protected final Color color;

    @Getter
    private final BasePosition basePosition;

    @Getter
    private boolean moved;

    public Piece(Window window, Board board, int x, int y, Color color, BasePosition basePosition) {
        super(window);
        this.board = board;
        this.color = color;
        this.basePosition = basePosition;
        setPosition(new Vector2i(x, y));
        setHeight(Board.CELL_SIZE);
        setWidth(Board.CELL_SIZE);
        moved = false;
    }

    public abstract List<Vector2i> getPossibleMoves();

    @Override
    public void draw(Graphics2D g) {
        int x = board.getX() + (Board.CELL_SIZE * getX());
        int y = board.getY() + (Board.CELL_SIZE * getY());
        g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
    }

    @Override
    public void setPosition(int x, int y) {
        if (!new Vector2i(x, y).equals(getPosition()))
            moved = true;
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(Vector2i point) {
        if (!point.equals(getPosition()))
            moved = true;
        super.setPosition(point);
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

    public boolean hasEnemyPieceAt(Vector2i position) {
        return board.getPieceAt(position) != null && board.getPieceAt(position).getColor() != color;
    }
    public boolean hasEnemyPieceAt(int i, int j) {
        return board.getPieceAt(i, j) != null && board.getPieceAt(i, j).getColor() != color;
    }
    public boolean hasFriendPieceAt(Vector2i position) {
        return board.getPieceAt(position) != null && board.getPieceAt(position).getColor() == color;
    }
    public boolean hasFriendPieceAt(int i, int j) {
        return board.getPieceAt(i, j) != null && board.getPieceAt(i, j).getColor() == color;
    }
}
