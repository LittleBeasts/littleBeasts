package config;

import de.gurkenlabs.litiengine.Game;

import java.awt.*;

public class HudConstants {


    public static final int BOTTOM_PAD = 140; //Pad from Bottom of Screen
    public static final int HUD_BOTTOM_START = (int) Game.window().getResolution().getHeight() - BOTTOM_PAD;
    public static final int HUD_ROW_HEIGHT = 100; // Height of HUD elements
    public static final int HUD_TILE_WIDTH = 150; // Width of HUD elements
    public static final int BATTLE_MENU_WIDTH = 150;
    public static final int BATTLE_MENU_START = 300;
    public static final int BATTLE_MENU_OFFSET = 50;
    public static final int TILE_GAP = 40; // Gap in-between HUD elements
    public static final int TEAM_START_POINT = 600; // Start of the littleBeastTiles
    public static final int HUD_START_POINT = 40; // Start of battle HUD
    public static final int BEAST_PORTRAIT_HEIGHT = 100;
    public static final int BEAST_PORTRAIT_WIDTH = 100;
    public static final int HEIGHT = (int) Game.window().getResolution().getHeight();
    public static final int WIDTH = (int) Game.window().getResolution().getWidth();

    public static final int BATTLEBARHEIGHT = 180;

    public static final Font HUD_FONT = new Font("Serif", Font.PLAIN, 15);


    // BattleMenu and SubMenus
    public static final int ITEMLISTLENGTH = 4;
    public static final int SUBMENUSHIFT = 25;
    public static final Color BACKGROUND = Color.WHITE;
    public static final Color SELECTCOLOR = Color.RED;
    public static final Color BUTTONCOLOR = Color.GRAY;
    public static final Color TEXTCOLOR = Color.BLACK;
    public static final Color HPBARCOLOR = new Color(255, 0, 0, 100);
    public static final String NO_ITEMS_PLACEHOLDER = "No items";

    public static final Font ChatWindowFont = new Font("Serif", Font.PLAIN, 55);

    // Menu and InGameMenu

    public static final Color MENU_BACKGROUND = new Color(100, 100, 100, 150);
    public static final Color BUTTON_RED = new Color(140, 16, 16, 200);
    public static final Color BUTTON_BLACK = new Color(0, 0, 0, 200);
    public static final Font MENU_FONT = new Font("Serif", Font.BOLD, 13);
    public static final Color MENU_FONT_COLOR = Color.WHITE;
    public static final int MENU_DELAY = 180;
    public static final String[] INGAME_MENU_ITEMS = new String[]{"Continue", "To Main Menu", "Exit"};
    public static final String[] MAIN_MENU_ITEMS = new String[]{"Single Player Game", "Play Online", "Instructions", "Exit"};
    public static final double MENU_BUTTON_WIDTH = 450;
    public static final double MENU_CENTER_X = Game.window().getResolution().getWidth() / 2.0;
    public static final double MENU_CENTER_Y = Game.window().getResolution().getHeight() * 1 / 2;
}
