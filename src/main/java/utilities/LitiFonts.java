package utilities;

import com.littleBeasts.screens.DrawChatWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LitiFonts {
    private static final ArrayList<Font> gameFonts = new ArrayList<>();

    public static void loadFonts() throws IOException, FontFormatException {
        String pathName = "./Fonts";
        File path = new File(pathName);
        String[] fontFilesNames = path.list();
        assert fontFilesNames != null;
        for (String fontFileName : fontFilesNames) {
            File fontFile = new File(pathName + "/" + fontFileName);
            Font gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            gameFont = gameFont.deriveFont(10.f);
            gameFonts.add(gameFont);
        }
    }

    public static ArrayList<Font> getGameFonts() {
        return gameFonts;
    }
}
