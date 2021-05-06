package com.littleBeasts.screens;

import com.littleBeasts.Program;
import com.littleBeasts.entities.LitiBeast;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameLogic;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.gameLogic.LitiBattle;
import com.littleBeasts.gameLogic.PlayerState;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import static config.HudConstants.*;

/*--------------------------------------------
In the draw method of this class all element which need to be displayed are drawn

--------------------------------------------*/

public class DrawHud extends GuiComponent {
    private final BattleMenu battleMenu;
    private int rollIn = 0;

    public DrawHud() {
        super(0, 0, WIDTH, HEIGHT);

        battleMenu = new BattleMenu(true);
        //ToDo: get PlayerItems from Player Inventory
        battleMenu.onConfirm(c -> {
            battleMenu.setFocus(false);
            if (c == 0) {
                battleMenu.getAttackMenu().setFocus(true);
            } else if (c == 1) {
                battleMenu.getCatchMenu().setFocus(true);
            }
        });
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        if (Game.world().environment() == null || LitiPlayer.instance().isDead() || LitiPlayer.instance().getState() != PlayerState.CONTROLLABLE) {
            return;
        }

        this.drawDamageRolls(g);
        this.renderBeasts(g);
        if (Program.getGameLogic().getState() == GameState.BATTLE) {
            try {
                this.rollInBars(g);
                this.drawBattleHud(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //this.drawIngameHud(g);
            rollIn = 0;
        }
    }

    private void rollInBars(Graphics2D g) {
        g.setColor(Color.BLACK);
        if (rollIn < HudConstants.BATTLEBARHEIGHT) rollIn += 5;
        g.fillRect(0, 0, HudConstants.WIDTH, rollIn);
        g.fillRect(0, HEIGHT, HudConstants.WIDTH, -rollIn);
    }

    private void drawDamageRolls(Graphics2D g) {
        for (LitiBeast litiBeast : LitiPlayer.instance().getLittleBeastTeam()) {
            litiBeast.getBeastStats().drawDamageRolls(g);
        }
        for (LitiBeast litiBeast : LitiBattle.getBeastList()) {
            if (litiBeast.getBeastStats() != null)
                litiBeast.getBeastStats().drawDamageRolls(g);
        }
    }

    private void drawBattleHud(Graphics2D g) throws IOException {

        g.setColor(Color.WHITE);

        //Player portrait and stats.
        drawPlayerPortrait(g);

        this.battleMenu.draw(g);
        this.battleMenu.setFocus(!(this.battleMenu.getAttackMenu().isFocused() || this.battleMenu.getCatchMenu().isFocused()));
        if (this.battleMenu.getAttackMenu().isFocused()) {
            this.battleMenu.getAttackMenu().draw(g, battleMenu.firstDrawnItem);
        }
        if (this.battleMenu.getCatchMenu().isFocused()) {
            this.battleMenu.getCatchMenu().draw(g, 1 - battleMenu.firstDrawnItem);
        }

        //draw beast portraits and stats
        drawBeastPortraits(g);

    }

    private void drawPlayerPortrait(Graphics2D g) throws IOException {
        g.setColor(BACKGROUND);
        int padding = HUD_START_POINT;
        int elementHeight = HUD_ROW_HEIGHT;
        int elementWidth = HUD_TILE_WIDTH + 50;
        g.fillRect(padding, HEIGHT - BOTTOM_PAD, elementWidth, elementHeight);
        g.drawImage(LitiPlayer.instance().getPlayerPortrait(), padding, HEIGHT - BOTTOM_PAD, 80, 100, null);
        g.setColor(Color.BLACK);
        g.setFont(HUD_FONT);
        String playerStats = LitiPlayer.instance().getPlayerName() + "\n";
        playerStats += LitiPlayer.instance().getCePlayer().getCeStats().getCurrentHitPoints() + "/" + LitiPlayer.instance().getMaxHP() + "\n";
        playerStats += "Player Whatever\n";
        drawString(g, playerStats, padding + 100, HEIGHT - padding - elementHeight);
    }

    public static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private void drawBeastPortraits(Graphics2D g) {
        for (LitiBeast beast : LitiPlayer.instance().getLittleBeastTeam()) {
            beast.getBeastStats().draw(g, LitiPlayer.instance().getLittleBeastTeam().indexOf(beast));
        }
        if (LitiBattle.getBeastList() != null) {
            for (LitiBeast beast : LitiBattle.getBeastList()) {
                if (beast.getBeastStats() != null)
                    beast.getBeastStats().draw(g, 0);
            }
        }
    }

    // TODO: Methods tbd
    private void renderBeasts(Graphics2D g) {

    }

    private void drawIngameHud(Graphics2D g) {
        if (Program.getGameLogic().getState().equals(GameState.INGAME)) {
            double y = Game.window().getResolution().getHeight() - TILE_GAP * 2;
            double x = Game.window().getResolution().getWidth() / 2.0;
            double currentWidth = 50.0;
            double height = 10.0;
            RoundRectangle2D actualRect = new RoundRectangle2D.Double(x, y, currentWidth, height, 1.5, 1.5);

            // RenderEngine.renderShape(g,actualRect);
            ShapeRenderer.render(g, actualRect);
            Font font = new Font(g.getFont().getName(), 0, 30);
            g.setFont(font);
            TextRenderer.render(g, "50/50", x - 100.0, y, true);

            ImageRenderer.render(g, Resources.images().get("sprites/icon.png"), x, y);
        }
    }

    public BattleMenu getBattleMenu() {
        return battleMenu;
    }

}
