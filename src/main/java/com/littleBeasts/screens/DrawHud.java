package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.PlayerState;
import com.littleBeasts.entities.Beast;
import com.littleBeasts.entities.Player;
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

    private final DrawBattleMenu battleMenu;
    private final DrawBattleMenu attackMenu;

    private boolean drawAttackMenu = false;
    private int rollIn = 0;

    public DrawHud() {
        super(0, 0, WIDTH, HEIGHT);

        battleMenu = new DrawBattleMenu();

        battleMenu.onConfirm(c -> {
            drawAttackMenu = !drawAttackMenu;
        });

        attackMenu = new DrawAttackMenu(Player.instance().getPlayerAttacks());
        attackMenu.onConfirm(c -> {
            drawAttackMenu = !drawAttackMenu;
        });

    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        if (Game.world().environment() == null || Player.instance().isDead() || Player.instance().getState() != PlayerState.CONTROLLABLE) {
            return;
        }

        this.drawDamageRolls(g);
        this.renderPlayerUI(g);
        this.renderHP(g);
        this.renderBeasts(g);
        if (GameLogic.getState() == GameState.BATTLE) {
            try {
                this.rollInBars(g);
                this.drawBattleHud(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.drawIngameHud(g);
            rollIn = 0;
            battleMenu.setFocus(false);
        }
    }

    private void rollInBars(Graphics2D g) {
        g.setColor(Color.BLACK);
        if (rollIn < HudConstants.BATTLEBARHEIGHT) rollIn += 5;
        g.fillRect(0, 0, HudConstants.WIDTH, rollIn);
        g.fillRect(0, HEIGHT, HudConstants.WIDTH, -rollIn);
    }

    private void drawDamageRolls(Graphics2D g) {
        for (Beast beast : Player.instance().getLittleBeastTeam()) {
            beast.getBeastStats().drawDamageRolls(g);
        }
        for (Beast beast : GameLogic.getBeastList()) {
            if (beast.getBeastStats() != null)
                beast.getBeastStats().drawDamageRolls(g);
        }
    }

    private void drawBattleHud(Graphics2D g) throws IOException {

        g.setColor(Color.WHITE);

        //Player portrait and stats.
        drawPlayerPortrait(g);

        //Action menu
        drawActionMenu(g);


        battleMenu.draw(g);
        attackMenu.setFocus(drawAttackMenu);
        battleMenu.setFocus(!drawAttackMenu);

        if (attackMenu.isFocused()) {
            this.attackMenu.draw(g);
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
        g.drawImage(Player.instance().getPlayerPortrait(), padding, HEIGHT - BOTTOM_PAD, 80, 100, null);
        g.setColor(Color.BLACK);
        g.setFont(HUD_FONT);

        String playerStats = Player.instance().getPlayerName() + "\n";
        playerStats += Player.instance().getCePlayer().getCeEntity().getHitPoints() + "/" + Player.instance().getMaxHP() + "\n";
        playerStats += "Player Whatever\n";
        drawString(g, playerStats, padding + 100, HEIGHT - padding - elementHeight);
    }

    public static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private void drawBeastPortraits(Graphics2D g) {
        for (Beast beast : Player.instance().getLittleBeastTeam()) {
            beast.getBeastStats().draw(g, Player.instance().getLittleBeastTeam().indexOf(beast));
        }
        if (GameLogic.getBeastList() != null) {
            for (Beast beast : GameLogic.getBeastList()) {
                if (beast.getBeastStats() != null)
                    beast.getBeastStats().draw(g, 0);
            }
        }
    }

    // Methods tbd
    private void drawActionMenu(Graphics2D g) {

    }

    private void drawContextMenu(Graphics2D g) {

    }

    private void renderBeasts(Graphics2D g) {

    }

    private void renderPlayerUI(Graphics2D g) {
    }

    private void renderHP(Graphics2D g) {

    }

    private void drawIngameHud(Graphics2D g) {
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

    public void setBattleMenuFocus(boolean focus) {
        battleMenu.setFocus(focus);
    }

    public DrawBattleMenu getAttackMenu() {
        return attackMenu;
    }

    public DrawBattleMenu getBattleMenu() {
        return battleMenu;
    }

}
