package fr.scriptis.simplechess;

import fr.scriptis.simplechess.windows.ChessWindow;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.logging.Logger;

public class Application extends JFrame {

    public static final int REFRESH_RATE = 60;

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        Application app = new Application();
        app.setTitle("SimpleChess");
        app.setBounds(0, 0, 1500, 800);
        app.setVisible(true);
        app.setOpacity(1);
        app.setResizable(false);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setBackground(Color.WHITE);
        logger.info("Starting game");
        ChessWindow chessWindow = new ChessWindow();
        chessWindow.setSize(app.getWidth(), app.getHeight());
        chessWindow.setPreferredSize(chessWindow.getSize());
        app.add(chessWindow);
        chessWindow.load();
        chessWindow.init();
        chessWindow.setVisible(true);
        logger.info("Game started");

    }
}
