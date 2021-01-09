package com.littleBeasts.screens;

import com.littleBeasts.entities.LitiBeast;
import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
import static config.HudConstants.*;

// |--------------------------------------------------------------------------|
// |    This class is the parent class draws the UI to display the stats      |
// |    of the littleBeasts when in battle.                                   |
// |    2020.11.12. D.B.                                                      |
// |--------------------------------------------------------------------------|

public class DrawBeastStats {

    private final LitiBeast beast;
    private final boolean playerBeast;
    private final List<DrawDamageAnimation> drawDamageAnimationList = new ArrayList<>();

    public DrawBeastStats(LitiBeast beast, boolean isPlayerBeast) {
        this.playerBeast = isPlayerBeast;
        this.beast = beast;
    }

    public void draw(Graphics2D g, int teamPosition) {
        if (this.beast == null) return;
        if (playerBeast) {
            drawPlayerBeastStats(g, teamPosition);
            for (Integer dmg : this.beast.getCeEntity().getDamages()) {
                drawDamageAnimationList.add(new DrawDamageAnimation((LitiPlayer.instance().getCenter()), dmg));
            }
        } else {
            for (Integer dmg : this.beast.getCeEntity().getDamages()) {
                drawDamageAnimationList.add(new DrawDamageAnimation(this.beast.getCenter(), dmg));
            }
        }
        damageAnimationOutOfBounds(g);
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
        DrawHud.drawString(g, beastInfo, TEAM_START_POINT + offset + BEAST_PORTRAIT_WIDTH, HUD_BOTTOM_START);
    }

    private void damageAnimationOutOfBounds(Graphics2D g) {
        for (int i = 0; i < drawDamageAnimationList.size(); i++) {
            drawDamageAnimationList.get(i).draw(g);
            if (drawDamageAnimationList.get(i).outOfBounds()) {
                drawDamageAnimationList.remove(i);
                if (DEBUG_CONSOLE_OUT) System.out.println("Removed");
            }
        }
    }

    public void drawDamageRolls(Graphics2D g) {
        damageAnimationOutOfBounds(g);
    }

    public boolean isReadyToBeRemoved() {
        return this.drawDamageAnimationList.isEmpty();
    }
}
