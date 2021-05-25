package com.littlebeasts.battleanimation;

import com.littlebeasts.Program;
import com.littlebeasts.entities.LitiBeast;
import config.HudConstants;

import java.awt.*;

import static config.HudConstants.*;

public class BeastStats {

    private final LitiBeast beast;
    private final boolean playerBeast;

    public BeastStats(LitiBeast beast, boolean isPlayerBeast) {
        this.playerBeast = isPlayerBeast;
        this.beast = beast;
    }

    public void draw(Graphics2D g, int teamPosition) {
        if (this.beast == null) return;
        if (playerBeast)
            drawPlayerBeastStats(g, teamPosition);
    }

    private void drawPlayerBeastStats(Graphics2D g, int teamPosition) {
        int offset = teamPosition * (HudConstants.TILE_GAP + HudConstants.HUD_TILE_WIDTH);

        // background of tile
        g.setColor(HudConstants.BACKGROUND);
        g.fillRect(TEAM_START_POINT + offset, HUD_BOTTOM_START, HUD_TILE_WIDTH, HUD_ROW_HEIGHT);

        // draw portrait
        g.drawImage(beast.getPortrait(), TEAM_START_POINT + offset, HUD_BOTTOM_START, BEAST_PORTRAIT_WIDTH, BEAST_PORTRAIT_HEIGHT, null);

        // draw health bar overlay
        g.setColor(HudConstants.HPBARCOLOR);
        int hpRatio = beast.getCeEntity().getCeStats().getCurrentHitPoints() * 100 / beast.getCeEntity().getCeStats().getMaxHitPoints();
        g.fillRect(TEAM_START_POINT + offset, HUD_BOTTOM_START + HUD_ROW_HEIGHT, BEAST_PORTRAIT_WIDTH, -(BEAST_PORTRAIT_HEIGHT * hpRatio) / 100);

        // get Beast info
        String beastInfo = "";
        beastInfo += beast.getMonsterName() + "\n";
        beastInfo += beast.getCeEntity().getCeStats().getCurrentHitPoints() + "/" + beast.getCeEntity().getCeStats().getMaxHitPoints() + "\n";

        // draw beast info
        g.setFont(HudConstants.HUD_FONT);
        g.setColor(HudConstants.TEXTCOLOR);
        Program.getIngameScreen().getHud().drawString(g, beastInfo, TEAM_START_POINT + offset + BEAST_PORTRAIT_WIDTH, HUD_BOTTOM_START);
    }
}
