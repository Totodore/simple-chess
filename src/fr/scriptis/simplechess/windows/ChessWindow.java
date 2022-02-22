package fr.scriptis.simplechess.windows;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.entities.chess.Board;
import fr.scriptis.simplechess.entities.ui.Background;
import fr.scriptis.simplechess.entities.ui.Button;
import fr.scriptis.simplechess.entities.ui.FpsCounter;
import fr.scriptis.simplechess.entities.ui.CenteredText;
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

    private Font poppins;
    private static final Color FONT_COLOR = new Color(0xB6B6DA);
    private Button giveUpBtn;
    private CenteredText title;

    public ChessWindow() {
        super(ENTITIES);
    }

    public void init() {
        super.init();
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppins = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ChessWindow.class.getResourceAsStream("/fonts/Poppins-Regular.ttf"))));
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.setFont(poppins.deriveFont(Font.BOLD, 30));
        Entity fps = FpsCounter.create(this);
        entityManager.add(
                title = new CenteredText(this, "", new Vector2i(1150, 140), 30, FONT_COLOR, g2d),
                giveUpBtn = Button.builder().text("Abandonner")
                        .position(new Vector2i(1150, 200))
                        .width(200).height(50)
                        .fontColor(FONT_COLOR)
                        .bgColor(new Color(0xB6B6DA))
                        .fontSize(30)
                        .onClick(this::onGiveUp).build(),
                fps
        );
        fps.init();
        title.init(g2d);
        giveUpBtn.init(g2d);
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

    }
}
