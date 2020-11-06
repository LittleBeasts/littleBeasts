package com.littleBeasts.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.GuiComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.ChatWindowFont;

public class ChatWindow extends GuiComponent implements IUpdateable {
    private static final String CURSOR = "|";
    private static StringBuffer buffer;
    private static String showableText;
    private static int maxLength;
    private static String validCharacters;
    private static List<String> chatHistory;
    private static int index;
    private static int topElement, bottomElement;
    private static int scrollbarHeight, scrollPointHeight;
    private static int scrollPointPosition;
    private static final int amountOfDrawnElements = 6;
    /* Set attributes with constructor parameters */
    private final Font font = ChatWindowFont;
    private float alpha;

    private Rectangle rectangle;
    private final Point textPoint;
    ;
    private int padding;

    private int countDelay;
    private boolean cursor;
    private final int x = 0;
    private final int y = 0;

    private static boolean focus;


    public static void init() {
    }


    public ChatWindow() {
        super(0, 0, Game.window().getWidth(), Game.window().getHeight());
        chatHistory = new ArrayList<>();
        validCharacters = "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM1234567890ß!?., ";
        /* Set text point */
        int x = this.x + this.padding;
        int y = this.y - this.padding;
        this.textPoint = new Point(x, y);


        /* Create variables to control buffer */
        buffer = new StringBuffer();
        showableText = "";
        maxLength = 38;
        clearIndex();

        /* Create variables to control cursor */
        this.countDelay = 0;
        this.cursor = false;
        int cursorLength = this.font.getSize();
        this.topElement = 0;
        this.bottomElement = amountOfDrawnElements;

        /* Starts without focus */
        focus = false;
    }

    private static void clearTextField() {
        focus = false;
        buffer = new StringBuffer();
        showableText = "";
        clearIndex();

    }

    private static void clearHistory() {
        chatHistory.clear();
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
            chatHistory.add(value);
            if (chatHistory.size() > amountOfDrawnElements) {
                if (bottomElement != chatHistory.size()) {
                    bottomElement = chatHistory.size();
                    topElement = chatHistory.size() - amountOfDrawnElements;
                    System.out.println("Top: " + topElement + " | Bottom: " + bottomElement);
                } else {
                    topElement++;
                    bottomElement++;
                }
            }

        }
        clearTextField();
    }

    /**
     * ESC
     */
    public static synchronized void escapeKey() {
        clearTextField();
        clearHistory();
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
            showableText = buffer.substring(getIndex(), buffer.length());
        }
    }

    public static void add(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                delete();
                break;
            case KeyEvent.VK_ENTER:
                returnKey();
                break;
            case KeyEvent.VK_ESCAPE:
                escapeKey();
            case KeyEvent.VK_UP:
                decIncrement();
                break;
            case KeyEvent.VK_DOWN:
                incIncrement();
                break;
        }


        /* Validate size */
        if (buffer.length() >= maxLength) {
            return;
        }

        /* Validate character */
        if (validCharacters.indexOf(e.getKeyChar()) == -1) {
            return;
        }

        /* Add char in buffer */
        buffer.append(e.getKeyChar());

        /* Get showable text with index */
        showableText = buffer.substring(getIndex(), buffer.length());
    }

    private static void incIncrement() {
        if (chatHistory.size() < amountOfDrawnElements)
            return;
        if (bottomElement != chatHistory.size()) {
            bottomElement++;
            topElement++;
        }
        System.out.println("Down: Bottom: " + bottomElement + " | Top: " + topElement);
    }

    private static void decIncrement() {
        if (chatHistory.size() < amountOfDrawnElements)
            return;
        if (topElement > 0) {
            bottomElement--;
            topElement--;
        }
        System.out.println("Up: Bottom: " + bottomElement + " | Top: " + topElement);
    }

    int tick = 0;

    @Override
    public synchronized void render(Graphics2D g) {

        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();


        tick++;
        if (tick % 60 == 0) {
            System.out.println("width: " + Game.window().getResolution().getWidth() + " | height: " + (int) Game.window().getResolution().getHeight());
        }

        g.setColor(new Color(150, 150, 150, 150));
        g.fillRect(0, 0, (int) Game.window().getResolution().getWidth(), (int) Game.window().getResolution().getHeight());

        int textHeight = 70;
        g.setFont(font);
        g.setColor(Color.WHITE);
        int vPadding = 200;
        int hPadding = 400;
        g.fillRoundRect(this.x + hPadding, this.y + vPadding, width - (hPadding * 2), height - (vPadding * 2), 20, 20);
        g.setColor(Color.gray);
        g.fillRoundRect(this.x + hPadding, this.y + vPadding, width - (hPadding * 2), height - (vPadding * 2) - textHeight, 20, 20);
        g.fillRect(this.x + hPadding, height - vPadding - textHeight, width - (hPadding * 2), -textHeight);

        /* Verifier if can draw cursor */
        countDelay++;
        int cursorDelay = 30;
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
        g.drawString(text, textPoint.x + hPadding + fineTuning, height - vPadding - fineTuning);


        scrollbarHeight = -height + vPadding * 2;
        scrollPointHeight = Math.min((scrollbarHeight / (Math.max(chatHistory.size() - amountOfDrawnElements + 1, 1))), -30);
        scrollPointPosition = chatHistory.size() > amountOfDrawnElements ? scrollPointHeight * (chatHistory.size() - bottomElement) : 0;

        g.setColor(Color.BLUE);
        g.fillRect(this.x + hPadding + (width - 2 * hPadding), height - vPadding, 30, scrollbarHeight);
        g.setColor(Color.GREEN);
        g.fillRect(this.x + hPadding + (width - 2 * hPadding), height - vPadding + scrollPointPosition, 30, scrollPointHeight);
    }

    public void setFocus(boolean focus) {
        ChatWindow.focus = focus;
    }

    @Override
    public void update() {

    }

    private static void drawString(Graphics g, List<String> text, int x, int y) {
        for (int i = topElement; i < bottomElement; i++) {
            if (i < text.size())
                g.drawString(text.get(i), x, y += g.getFontMetrics().getHeight());
        }
    }
}
