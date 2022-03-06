package fr.scriptis.simplechess.windows;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.entities.chess.Board;
import fr.scriptis.simplechess.entities.chess.PieceColor;
import fr.scriptis.simplechess.entities.ui.Background;
import fr.scriptis.simplechess.entities.ui.Button;
import fr.scriptis.simplechess.entities.ui.CenteredText;
import fr.scriptis.simplechess.entities.ui.FpsCounter;
import fr.scriptis.simplechess.entities.ui.IconButton;
import fr.scriptis.simplechess.utils.Vector2i;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Objects;

public class ChessWindow extends Window {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Entity>[] ENTITIES = new Class[]{
            Background.class,
            Board.class,
    };

    private static final Color FONT_COLOR = new Color(0xB6B6DA);
    private static final int FONT_SIZE = 45;
    private CenteredText title;

    public ChessWindow() {
        super(ENTITIES);
    }

    public void init() {
        super.init();
        Font mainFont = null;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mainFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ChessWindow.class.getResourceAsStream("/fonts/BalloBhai.ttf"))));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) getGraphics();
        if (mainFont != null)
            g2d.setFont(mainFont);
//        Entity fps = FpsCounter.create(this);
        IconButton previousButton;
        IconButton nextButton;
        Button giveUpBtn;
        entityManager.add(
                title = new CenteredText(this, "", new Vector2i(1150, 100), FONT_SIZE, FONT_COLOR, g2d),
                giveUpBtn = Button.builder().text("Abandonner")
                        .center(new Vector2i(1150, 550))
                        .padding(new Vector2i(10, -5))
                        .autoSize(true)
                        .fontColor(FONT_COLOR)
                        .fontSize(FONT_SIZE)
                        .onClick(this::onGiveUp).build(),
                previousButton = IconButton.builder().iconName("arrow_back.svg")
                        .center(new Vector2i(1042, 640))
                        .autoSize(false)
                        .height(40)
                        .width(40)
                        .iconSize(18)
                        .offset(new Vector2i(5, 0))
                        .onClick(this::onPreviousBtn)
                        .build(),
                nextButton = IconButton.builder().iconName("arrow_forward.svg")
                        .center(new Vector2i(1260, 640))
                        .autoSize(false)
                        .height(40)
                        .width(40)
                        .iconSize(18)
                        .offset(new Vector2i(2, 0))
                        .onClick(this::onNextBtn)
                        .build()
//                fps
        );
//        fps.init();
        title.init(g2d);
        giveUpBtn.init(g2d);
        nextButton.init();
        previousButton.init();
        entityManager.find(Board.class).ifPresent(
                board -> board.setOnBoardStateUpdate(this::onBoardStateUpdate));
        windowTimer.start();
        onBoardStateUpdate(true);
        logger.info("ChessWindow initialized");
    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);
        paintComponent((Graphics2D) g);
        Toolkit.getDefaultToolkit().sync();
    }

    protected void paintComponent(@NotNull Graphics2D g2d) {
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB));
        // Max quality
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Entity entity : entityManager.getAllEntities()) {
            entity.draw(g2d);
        }
        entityManager.find(FpsCounter.class).ifPresent(FpsCounter::tick);
    }

    private void onBoardStateUpdate(boolean isWhiteTurn) {
        title.setText("C'est au tour des " + (isWhiteTurn ? "Blancs" : "Noirs"));
    }

    private void onGiveUp(Button button) {
        System.out.println("Victoire des " + (entityManager.find(Board.class).orElseThrow().getCurrentPlayingColor() != PieceColor.BLACK ? "Noirs" : "Blancs"));
    }

    private void onNextBtn(IconButton button) {
        entityManager.find(Board.class).ifPresent(Board::historyNext);
    }

    private void onPreviousBtn(IconButton button) {
        entityManager.find(Board.class).ifPresent(Board::historyPrevious);
    }
}
