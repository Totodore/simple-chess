package fr.scriptis.simplechess.windows;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.entities.chess.Board;
import fr.scriptis.simplechess.entities.ui.Background;
import fr.scriptis.simplechess.entities.ui.FpsCounter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class ChessWindow extends Window {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Entity>[] ENTITIES = new Class[]{
            Board.class,
    };

    private Font poppins;

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
        Entity fps = FpsCounter.create(this);
        entityManager.add(fps);
        fps.init();
        windowTimer.start();
        logger.info("ChessWindow initialized");
    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);
        paintComponent((Graphics2D) g);
        Toolkit.getDefaultToolkit().sync();
    }

    protected void paintComponent(@NotNull Graphics2D g2d) {
        g2d.setFont(poppins.deriveFont(Font.BOLD, 30));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB));
        for (Entity entity : entityManager.getAllEntities()) {
            entity.draw(g2d);
        }
        entityManager.find(FpsCounter.class).ifPresent(e -> e.tick());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        logger.info("Clicked: " + e.getPoint());
    }
}
