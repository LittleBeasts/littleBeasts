package utilities;

import com.littlebeasts.Program;
import config.FontConstants;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LitiFontsUtils {
    private static final ArrayList<Font> gameFonts = new ArrayList<>();

    public static void loadFonts() throws IOException, FontFormatException {
        for (String fontFileName : FontConstants.FONT_LIST) {
            Font gameFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Program.class.getResourceAsStream("/Fonts/" + fontFileName)));
            gameFont = gameFont.deriveFont(10.f);
            gameFonts.add(gameFont);
        }
    }

    public static ArrayList<Font> getGameFonts() {
        return gameFonts;
    }
}