package utilities;

import com.littleBeasts.Program;
import config.FontConstants;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LitiFonts {
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