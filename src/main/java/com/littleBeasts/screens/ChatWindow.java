package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.PlayerState;
import com.littleBeasts.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.input.IKeyboard;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class ChatWindow extends GuiComponent implements IUpdateable, KeyListener {
    private static final String CURSOR = "|";
    private static StringBuffer buffer;
    private static String showableText;
    private static int maxLength;
    private static String validCharacters;
    private static String chatHistory;
    private static int index;
    private Font font;
    private float alpha;

    private Rectangle rectangle;
    private Point textPoint;
    ;
    private int padding;

    private int cursorDelay = 30;
    private int countDelay;
    private boolean cursor;
    private int cursorLength;
    private int x = 0;
    private int y = 0;
    private int width = Game.window().getWidth();
    private int height = Game.window().getHeight();
    private int vPadding = 200;
    private int hPadding = 400;

    private static boolean focus;

    public static void init() {
    }


    public ChatWindow() {
        super(0, 0, Game.window().getWidth(), Game.window().getHeight());
        /* Set attributes with constructor parameters */
        this.font = new Font("Serif", Font.PLAIN, 55);
        chatHistory = "";
        validCharacters = "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM1234567890ß!?., ";
        /* Set text point */
        int x = this.x + this.padding;
        int y = this.y - this.padding;
        this.textPoint = new Point(x, y);

        /* Create variables to control buffer */
        this.buffer = new StringBuffer();
        this.showableText = "";
        this.maxLength = 50;
        this.clearIndex();

        /* Create variables to control cursor */
        this.countDelay = 0;
        this.cursor = false;
        this.cursorLength = this.font.getSize();

        /* Starts without focus */
        this.focus = false;
    }

    private static void clearTextField() {
        focus = false;
        buffer = new StringBuffer();
        showableText = "";
        clearIndex();

    }

    private static void clearHistory() {
        chatHistory = "";
    }

    public synchronized void onFocus() {
        focus = true;
    }

    /**
     * ENTER
     */
    public static synchronized void returnKey() {
        if (buffer.length() > 0) {
            String value = buffer.toString();
            chatHistory += value + "\n";
            //this.returnKeyCommand.setParameters(value);
            //this.returnKeyCommand.execute();
        } else {
            //this.escapeKeyCommand.execute();
        }
        clearTextField();
    }

    /**
     * ESC
     */
    public static synchronized void escapeKey() {
        clearTextField();
        clearHistory();
        //this.escapeKeyCommand.execute();
    }

    private static void clearIndex() {
        index = 0;
    }

    private static int getIndex() {
        return index;
    }

    private void increaseIndex() {
        index++;
    }

    public static synchronized void delete() {
        if (buffer.length() > 0) {
            clearIndex();
            buffer.deleteCharAt(buffer.length() - 1);

            /* Get showable text with index */
            showableText = buffer.substring(getIndex(), buffer.length());

            /* Adjust showable text if necessary */
            // while (getAdjustedTextWidth(showableText) > rectangle.width) {
            //     increaseIndex();
            //     showableText = buffer.substring(getIndex(), buffer.length());
            // }
        }
    }

    public static void add(char character) {
        if (character == KeyEvent.VK_BACK_SPACE) {
            delete();
            return;
        }
        if (character == KeyEvent.VK_ENTER) {
            returnKey();
            return;
        }
        if (character == KeyEvent.VK_ESCAPE) {
            escapeKey();
            return;
        }

        /* Validate size */
        if (buffer.length() >= maxLength) {
            return;
        }

        /* Validate character */
        if (validCharacters.indexOf(character) == -1) {
            return;
        }

        /* Add char in buffer */
        buffer.append(character);

        /* Get showable text with index */
        showableText = buffer.substring(getIndex(), buffer.length());

        /* Adjust showable text if necessary */
        //  while (getAdjustedTextWidth(showableText) > rectangle.width) {
        //      increaseIndex();
        //      showableText = buffer.substring(getIndex(), buffer.length());
        //  }
    }

    @Override
    public synchronized void render(Graphics2D g) {
        g.setColor(new Color(150, 150, 150, 150));
        g.fillRect(0, 0, Game.window().getWidth(), Game.window().getHeight());
        /* Draw component */
        int textHeight = 70;
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.fillRoundRect(this.x + hPadding, this.y + vPadding, this.width - (hPadding * 2), this.height - (vPadding * 2), 20, 20);
        g.setColor(Color.gray);
        g.fillRoundRect(this.x + hPadding, this.y + vPadding, this.width - (hPadding * 2), this.height - (vPadding * 2) - textHeight, 20, 20);
        g.fillRect(this.x + hPadding, this.height - vPadding - textHeight, this.width - (hPadding * 2), -textHeight);

        /* Verifier if can draw cursor */
        countDelay++;
        countDelay = countDelay % cursorDelay;
        if (countDelay == 0) {
            cursor = !cursor;
        }

        /* Set text to draw */
        String text = showableText + ((cursor) ? CURSOR : "");

        /* Draw */
        g.setColor(Color.BLACK);
        int fineTuning = 20;
        drawString(g, chatHistory, textPoint.x + hPadding + fineTuning, this.y + vPadding + fineTuning);
        g.drawString(text, textPoint.x + hPadding + fineTuning, Game.window().getHeight() - vPadding - fineTuning);
    }


    public void setFocus(boolean focus) {
        this.focus = focus;
    }


    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
}
