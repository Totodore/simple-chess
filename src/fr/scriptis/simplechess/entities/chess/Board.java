package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;
import lombok.Setter;
import org.apache.xpath.operations.Bool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class Board extends Entity {

    public static final int CELL_SIZE = 685 / 8;
    public static final Color WHITE_COLOR = new Color(0x6AB1B3DC, true);
    public static final Color BLACK_COLOR = new Color(0x6A333333, true);
    private final static Class<? extends Piece>[] PIECES = new Class[]{
            Rook.class, Knight.class, Bishop.class, Queen.class, King.class,
            Bishop.class, Knight.class, Rook.class, Pawn.class
    };
    private final List<Piece> pieces = new ArrayList<>(32);
    private Piece focusedPiece;
    private List<Vector2i> possibleMoves;

    @Setter
    private Consumer<Boolean> onBoardStateUpdate;

    @Getter
    private Color currentPlayingColor = Color.WHITE;

    public Board(Window window) {
        super(window);
    }

    public static void load() {
        Logger.getGlobal().info("Loading chess board");
        for (int i = 0; i < PIECES.length; i++) {
            Piece.load(PIECES[i]);
        }
    }

    @Override
    public void init() {
        setPosition(174, 50);
        setWidth(685);
        setHeight(685);
        initializePieces();
        getPieceAt(2, 0).setPosition(5, 3);
    }

    @Override
    public void draw(Graphics2D g) {
        logger.info("Drawing board");
        int radius = 15;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor((i + j) % 2 == 0 ? WHITE_COLOR : BLACK_COLOR);
                int x = getX() + i * CELL_SIZE;
                int y = getY() + j * CELL_SIZE;
                g.clipRect(x, y, CELL_SIZE, CELL_SIZE);
                if (i == 0 && j == 0)
                    g.fillRoundRect(x, y, CELL_SIZE + radius, CELL_SIZE + radius, radius, radius);
                else if (i == 7 && j == 0)
                    g.fillRoundRect(x - radius, y, CELL_SIZE + radius, CELL_SIZE + radius, radius, radius);
                else if (i == 0 && j == 7)
                    g.fillRoundRect(x, y - radius, CELL_SIZE + radius, CELL_SIZE + radius, radius, radius);
                else if (i == 7 && j == 7)
                    g.fillRoundRect(x - radius, y - radius, CELL_SIZE + radius, CELL_SIZE + radius, radius, radius);
                else
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                g.setClip(null);
            }
        }
        for (Piece piece : pieces)
            piece.draw(g);
        if (possibleMoves != null) {
            for (Vector2i move : possibleMoves) {
                g.setColor(new Color(255, 0, 0, 127));
                g.fillRect(getX() + move.x * CELL_SIZE, getY() + move.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Debug
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                g.setFont(g.getFont().deriveFont(11f));
//                g.setColor(Color.green);
//                g.drawString("(" + i + ", " + j + ")", getX() + i * CELL_SIZE + CELL_SIZE - 35, getY() + j * CELL_SIZE + CELL_SIZE - 10);
//            }
//        }
    }

    @Override
    public boolean onClick(Vector2i position) {
        position = new Vector2i((int) Math.floor(position.x / CELL_SIZE), (int) Math.floor(position.y / CELL_SIZE));
        if (position.x < 0 || position.x > 7 || position.y < 0 || position.y > 7) return true;
        Piece piece = getPieceAt(position);
        setDirty(true);
        // If we clicked on a piece and that it is a friendly piece
        if (piece != null && (focusedPiece == null || focusedPiece.hasFriendPieceAt(position))) {
            possibleMoves = piece.getPossibleMoves();
            focusedPiece = piece;
        }
        // If we clicked on an empty cell but with possible moves and there isn't any piece at where we clicked
        else if (focusedPiece != null && possibleMoves.contains(position) && !hasPieceAt(position)) {
            focusedPiece.setPosition(position);
            nextState();
            focusedPiece = null;
            possibleMoves = null;
        }
        // If we clicked on an enemy piece and that it is in possible moves
        else if (focusedPiece != null && focusedPiece.hasEnemyPieceAt(position) && possibleMoves.contains(position)) {
            removePiece(piece);
            nextState();
            focusedPiece.setPosition(position);
            focusedPiece = null;
            possibleMoves = null;
        } else {
            focusedPiece = null;
            possibleMoves = null;
        }
        return false;
    }

    /**
     * Generate a chess board with pieces
     * The white are at the top and the black are at the bottom
     */
    private void initializePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color = null;
                if (j == 0 || j == 1)
                    color = Board.BLACK_COLOR;
                else if (j == 6 || j == 7)
                    color = Board.WHITE_COLOR;
                if (j == 1 || j == 6) {
                    pieces.add(new Pawn(window, this, i, j, color, j == 1 ? BasePosition.TOP : BasePosition.BOTTOM));
                } else if (j == 0 || j == 7) {
                    BasePosition position = j == 0 ? BasePosition.TOP : BasePosition.BOTTOM;
                    if (i == 0 || i == 7) {
                        pieces.add(new Rook(window, this, i, j, color, position));
                    } else if (i == 1 || i == 6) {
                        pieces.add(new Knight(window, this, i, j, color, position));
                    } else if (i == 2 || i == 5) {
                        pieces.add(new Bishop(window, this, i, j, color, position));
                    } else if (i == 3) {
                        pieces.add(new Queen(window, this, i, j, color, position));
                    } else {
                        pieces.add(new King(window, this, i, j, color, position));
                    }
                }
            }
        }
    }


    private void nextState() {
        currentPlayingColor = currentPlayingColor == Color.WHITE ? Color.BLACK : Color.WHITE;
        onBoardStateUpdate.accept(currentPlayingColor == Color.WHITE);
    }


    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public Piece getPieceAt(Vector2i position) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
    }

    public Piece getPieceAt(int x, int y) {
        return getPieceAt(new Vector2i(x, y));
    }

    public boolean hasPieceAt(Vector2i position) {
        return pieces.stream().anyMatch(piece -> piece.getPosition().equals(position));
    }

    public boolean hasPieceAt(int x, int y) {
        return hasPieceAt(new Vector2i(x, y));
    }

    public String getPlayerName() {
        return currentPlayingColor == Color.WHITE ? "Blancs" : "Noirs";
    }
}
