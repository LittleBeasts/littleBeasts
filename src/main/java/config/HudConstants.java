package config;

import de.gurkenlabs.litiengine.Game;

import java.awt.*;

public class HudConstants {

    public static final int BOTTOM_PAD = 140; //Pad from Bottom of Screen
    public static final int HUD_ROW_HEIGHT = 100; // Height of HUD elements
    public static final int HUD_TILE_WIDTH = 150; // Width of HUD elements
    public static final int BATTLE_MENU_WIDTH = 150;
    public static final int TILE_GAP = 40; // Gap in-between HUD elements
    public static final int TEAM_START_POINT = 600; // Start of the littleBeastTiles
    public static final int HUD_START_POINT = 40; // Start of battle HUD
    public static final int HEIGHT = (int) Game.window().getResolution().getHeight();
    public static final int ITEMLISTLENGTH = 4;

    public static final Color BACKGROUND = Color.WHITE;
    public static final Color SELECTCOLOR = Color.RED;
    public static final Color BUTTONCOLOR = Color.GRAY;
    public static final Color TEXTCOLOR = Color.BLACK;
    public static final Color HPBARCOLOR = new Color(255, 0, 0, 100);

}
