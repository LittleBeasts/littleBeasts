package com.littlebeasts.guicomponent;

import com.littlebeasts.Program;
import com.littlebeasts.gamelogic.GameState;
import com.littlebeasts.gamelogic.LitiClient;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.GuiComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static client.Message.encodeOutgoingMessageForClient;
import static config.GlobalConstants.DEBUG_CONSOLE_OUT;
import static config.HudConstants.ChatWindowFont;

public class DrawChatWindow extends GuiComponent {
    private static final String CURSOR = "|";
    private static StringBuffer buffer = new StringBuffer();
    private static String showableText = "";
    private static final int maxLength = 38;
    private static final String validCharacters = "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM1234567890ß!?., ";
    private static final List<String> chatHistory = new ArrayList<>();
    private static int index;
    private static int topElement = 0;
    private static final int amountOfDrawnElements = 6;
    private static int bottomElement = amountOfDrawnElements;
    private final Font font = ChatWindowFont;
    private final Point textPoint;
    private final int padding = 2;

    private int countDelay;
    private boolean cursor;
    private final int x = 0;
    private final int y = 0;

    public DrawChatWindow() {
        super(0, 0, Game.window().getWidth(), Game.window().getHeight());
        int x = this.x + this.padding;
        int y = this.y - this.padding;
        this.textPoint = new Point(x, y);
        clearIndex();
        this.countDelay = 0;
        this.cursor = false;
    }

    private static void clearTextField() {
        buffer = new StringBuffer();
        showableText = "";
        clearIndex();
    }

    public static synchronized void returnKey() {
        if (buffer.length() > 0) {
            String value = buffer.toString();
            try {
                LitiClient.sendMessageToServer(encodeOutgoingMessageForClient(LitiClient.getClient().getName(), value));
            } catch (IOException e) {
                e.printStackTrace();
            }
            chatHistory.add(value);
            if (chatHistory.size() > amountOfDrawnElements) {
                if (bottomElement != chatHistory.size()) {
                    bottomElement = chatHistory.size();
                    topElement = chatHistory.size() - amountOfDrawnElements;
                    if (DEBUG_CONSOLE_OUT) System.out.println("Top: " + topElement + " | Bottom: " + bottomElement);
                } else {
                    topElement++;
                    bottomElement++;
                }
            }
        }
        clearTextField();
    }

    public static synchronized void escapeKey() {
        clearTextField();
    }

    private static void clearIndex() {
        index = 0;
    }

    private static int getIndex() {
        return index;
    }

    public static synchronized void delete() {
        if (buffer.length() > 0) {
            clearIndex();
            buffer.deleteCharAt(buffer.length() - 1);
            showableText = buffer.substring(getIndex(), buffer.length());
        }
    }

    public static void add(KeyEvent e) {
        if (Program.getGameLogic().getState() != GameState.INGAME_CHAT) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                delete();
                break;
            case KeyEvent.VK_ENTER:
                returnKey();
                break;
            case KeyEvent.VK_ESCAPE:
                escapeKey();
                break;
            case KeyEvent.VK_UP:
                decIncrement();
                break;
            case KeyEvent.VK_DOWN:
                incIncrement();
                break;
            default:
                break;
        }

        if (buffer.length() >= maxLength) {
            return;
        }

        if (validCharacters.indexOf(e.getKeyChar()) == -1) {
            return;
        }

        buffer.append(e.getKeyChar());

        showableText = buffer.substring(getIndex(), buffer.length());
    }

    private static void incIncrement() {
        if (chatHistory.size() < amountOfDrawnElements)
            return;
        if (bottomElement != chatHistory.size()) {
            bottomElement++;
            topElement++;
        }
        if (DEBUG_CONSOLE_OUT) System.out.println("Down: Bottom: " + bottomElement + " | Top: " + topElement);
    }

    private static void decIncrement() {
        if (chatHistory.size() < amountOfDrawnElements)
            return;
        if (topElement > 0) {
            bottomElement--;
            topElement--;
        }
        if (DEBUG_CONSOLE_OUT) System.out.println("Up: Bottom: " + bottomElement + " | Top: " + topElement);
    }

    @Override
    public synchronized void render(Graphics2D g) {
        List<String> bufferedMessages;
        bufferedMessages = LitiClient.getBufferedMessages();
        if (bufferedMessages != null) {
            chatHistory.addAll(bufferedMessages);
        }
        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();
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


        int scrollbarHeight = -height + vPadding * 2;
        int scrollPointHeight = Math.min((scrollbarHeight / (Math.max(chatHistory.size() - amountOfDrawnElements + 1, 1))), -30);
        int scrollPointPosition = chatHistory.size() > amountOfDrawnElements ? scrollPointHeight * (chatHistory.size() - bottomElement) : 0;

        g.setColor(Color.BLUE);
        g.fillRect(this.x + hPadding + (width - 2 * hPadding), height - vPadding, 30, scrollbarHeight);
        g.setColor(Color.GREEN);
        g.fillRect(this.x + hPadding + (width - 2 * hPadding), height - vPadding + scrollPointPosition, 30, scrollPointHeight);
    }

    private static void drawString(Graphics g, List<String> text, int xIn, int yIn) {
        int x = xIn;
        int y = yIn;
        for (int i = topElement; i < bottomElement; i++) {
            if (i < text.size() && text.get(i) != null)
                g.drawString(text.get(i), x, y += g.getFontMetrics().getHeight());
        }
    }
}
