package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Board extends Entity {

    public static final int CELL_SIZE = 685 / 8;
    private final static Class<? extends Piece>[] PIECES = new Class[]{
            Rook.class, Knight.class, Bishop.class, Queen.class, King.class,
            Bishop.class, Knight.class, Rook.class, Pawn.class
    };
    private final List<Piece> pieces = new ArrayList<>(32);
    private Piece focusedPiece;
    private List<Vector2i> possibleMoves;
    private int historyIterator = 0;
    private int halfMoves = 0;
    private final List<String> history = new ArrayList<>();

    @Setter
    private Consumer<Boolean> onBoardStateUpdate;

    @Getter
    private PieceColor currentPlayingColor = PieceColor.WHITE;

    public Board(Window window) {
        super(window);
    }

    @SuppressWarnings("unused")
    public static void load() {
        Logger.getGlobal().info("Loading chess board");
        for (Class<? extends Piece> piece : PIECES) {
            Piece.load(piece);
        }
    }

    @Override
    public void init() {
        setPosition(174, 50);
        setWidth(685);
        setHeight(685);
        initializePieces();
    }

    @Override
    public void draw(Graphics2D g) {
        logger.info("Drawing board");
        int radius = 15;
        // Drawing the cells with border radius for the corners
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor((i + j) % 2 == 0 ? PieceColor.WHITE.color : PieceColor.BLACK.color);
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
        // Drawing each piece
        for (Piece piece : pieces)
            piece.draw(g);
        // Drawing the possible moves
        if (possibleMoves != null) {
            for (Vector2i move : possibleMoves) {
                g.setColor(new Color(0x7EFFF153, true));
                Vector2i pos = getPosition().translate(move.scale(CELL_SIZE));   // Board positions to pixel position
//                if (!hasPieceAt(move)) {
                pos = pos.translate(CELL_SIZE / 2).translate(-CELL_SIZE / 4);    // Center the square
                g.fillRoundRect(pos.x, pos.y, CELL_SIZE / 2, CELL_SIZE / 2, CELL_SIZE, CELL_SIZE);
//                } else {
                // Clip the round rectangle that we wanna keep and draw the square
//                    g.fillRect(pos.x, pos.y, CELL_SIZE, CELL_SIZE);
//                    g.fillArc(pos.x, pos.y, CELL_SIZE, CELL_SIZE, 300, 340);
//            }
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
        // If we clicked on a piece and that it is a friendly piece
        if (piece != null && piece.color == getCurrentPlayingColor() && (focusedPiece == null || focusedPiece.hasFriendPieceAt(position))) {
            possibleMoves = piece.getPossibleMoves();
            focusedPiece = piece;
            setDirty(true);
        }
        // If we clicked on an empty cell but with possible moves and there isn't any piece at where we clicked
        else if (focusedPiece != null && possibleMoves.contains(position) && !hasPieceAt(position)) {
            setDirty(true);
            focusedPiece.setPosition(position);
            nextState();
            focusedPiece = null;
            possibleMoves = null;
        }
        // If we clicked on an enemy piece and that it is in possible moves
        else if (focusedPiece != null && focusedPiece.hasEnemyPieceAt(position) && possibleMoves.contains(position)) {
            setDirty(true);
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
     * The black are at the top and the white are at the bottom
     */
    private void initializePieces() {
        loadFenString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public void historyPrevious() {
        historyIterator--;
        if (historyIterator < 0) historyIterator = 0;
        loadFenString(history.get(historyIterator));
    }

    public void historyNext() {
        historyIterator++;
        if (historyIterator >= history.size()) {
            historyIterator = history.size() - 1;
        }
        loadFenString(history.get(historyIterator));
    }

    public void loadFenString(@NotNull String fen) {
        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");
        pieces.clear();
        // For each row
        for (int i = 0; i < 8; i++) {
            char[] charPieces = rows[i].toCharArray();
            int j = 0;
            // For each character if it's a piece we add it to the board
            // Otherwise we increment j to know how many empty cells we have
            for (Character c : charPieces) {
                if (Character.isLetter(c)) {
                    PieceColor color = Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
                    BasePosition position = color == PieceColor.BLACK ? BasePosition.TOP : BasePosition.BOTTOM;
                    switch (Character.toLowerCase(c)) {
                        case 'p' -> pieces.add(new Pawn(window, this, j, i, color, position));
                        case 'r' -> pieces.add(new Rook(window, this, j, i, color, position));
                        case 'n' -> pieces.add(new Knight(window, this, j, i, color, position));
                        case 'b' -> pieces.add(new Bishop(window, this, j, i, color, position));
                        case 'q' -> pieces.add(new Queen(window, this, j, i, color, position));
                        case 'k' -> pieces.add(new King(window, this, j, i, color, position));
                    }
                    j++;
                } else
                    j += Character.getNumericValue(c);
            }
        }
        currentPlayingColor = parts[1].equals("w") ? PieceColor.WHITE : PieceColor.BLACK;
        halfMoves = Integer.parseInt(parts[4]);
        setDirty(true);
    }


    /**
     * Generate a FEN string from the current board
     */
    private String generateFenString() {
        StringBuilder fen = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int emptyCount = 0;
            for (int j = 0; j < 8; j++) {
                Piece piece = getPieceAt(j, i);
                if (piece == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    fen.append(piece.getPieceId());
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            if (i < 7) fen.append("/");
        }
        fen.append(" ").append(currentPlayingColor == PieceColor.WHITE ? "w" : "b");
        fen.append(" ");
        fen.append("-");
        fen.append(" ");
        fen.append(" ").append(halfMoves);
        return fen.toString();
    }


    private void nextState() {
        currentPlayingColor = currentPlayingColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
        halfMoves++;
        history.add(generateFenString());
        onBoardStateUpdate.accept(currentPlayingColor == PieceColor.WHITE);
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
        return currentPlayingColor == PieceColor.WHITE ? "Blancs" : "Noirs";
    }
}
