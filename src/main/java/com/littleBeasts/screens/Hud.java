package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.PlayerState;
import com.littleBeasts.entities.Beast;
import com.littleBeasts.entities.Player;
import config.GlobalConfig;
import config.HudConstants;
import config.PlayerConfig;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.Resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Hud extends GuiComponent {

    private static final int PADDING = 40;
    boolean debug = GlobalConfig.DEBUG_CONSOLE_OUT;
    private final BattleMenu battleMenu;
    private final BattleMenu attackMenu;

    private boolean drawAttackMenu = false;
    private int rollIn = 0;

    public Hud() {
        super(0, 0, Game.window().getResolution().getWidth(), Game.window().getResolution().getHeight());

        // ToDo: Will be refactored with BattleMenu and AttackMenu
        battleMenu = new BattleMenu(300, PlayerConfig.PLAYER_ACTIONS);
        // ToDo: test if we can remove this
        battleMenu.onConfirm(c -> {
            switch (c.intValue()) {
                case 0:
                case 1:
                    drawAttackMenu = !drawAttackMenu;
                    break;
            }
        });
        // ToDo: Similar to above ToDoS
        CeAttack[] ceAttacks = Player.instance().getPlayerAttacks();
        attackMenu = new BattleMenu(350, ceAttacks);
        attackMenu.onConfirm(c -> {
            switch (c.intValue()) {
                case 0:
                case 1:
                    drawAttackMenu = !drawAttackMenu;
                    break;
            }
        });

    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

//        g.setColor(Color.RED);

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
        if (rollIn < HudConstants.BATTLEBARHEIGHT)
            rollIn += 5;
        g.fillRect(0, 0, HudConstants.WIDTH, rollIn);
        g.fillRect(0, HudConstants.HEIGHT, HudConstants.WIDTH, -rollIn);
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
        int height = (int) Game.window().getResolution().getHeight();
        int width = (int) Game.window().getResolution().getWidth();
        g.setColor(Color.WHITE);

        //Player portrait and stats.
        drawPlayerPortrait(g, width, height);

        //Action menu
        drawActionMenu(g);

        battleMenu.setFocus(!drawAttackMenu);
        battleMenu.draw(g);

        if (drawAttackMenu) {
            attackMenu.draw(g);
            attackMenu.setFocus(true);
        } else {
            attackMenu.setFocus(false);
        }

        //draw beast portraits and stats
        drawBeastPortraits(g);

    }

    // ToDo: refactor width and height to global Hud constants, e.g.: width, height, color
    private void drawPlayerPortrait(Graphics2D g, int width, int height) throws IOException {
        g.setColor(Color.WHITE);

        // ToDo: get from Character
        Image originalImage = ImageIO.read(new File("sprites/char.png"));
        int padding = HudConstants.HUD_START_POINT;
        int elementHeight = HudConstants.HUD_ROW_HEIGHT;
        int elementWidth = HudConstants.HUD_TILE_WIDTH + 50;
        g.fillRect(padding, HudConstants.HEIGHT - HudConstants.BOTTOM_PAD, elementWidth, elementHeight);
        g.drawImage(originalImage, padding, HudConstants.HEIGHT - HudConstants.BOTTOM_PAD, 80, 100, null);
        g.setColor(Color.BLACK);

        // ToDo: extract Font in new Constants
        g.setFont(new Font("Serif", Font.PLAIN, 15));
        String playerStats = Player.instance().getPlayerName() + "\n";
        playerStats += Player.instance().getCePlayer().getCeEntity().getHitPoints() + "/" + Player.instance().getMaxHP() + "\n";
        playerStats += "Player Whatever\n";
        drawString(g, playerStats, padding + 100, height - padding - elementHeight);
    }

    private static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private void drawBeastPortraits(Graphics2D g) {
        for (Beast beast : Player.instance().getLittleBeastTeam()) {
            beast.getBeastStats().draw(g);
        }
        if (GameLogic.getBeastList() != null) {
            for (Beast beast : GameLogic.getBeastList()) {
                if (beast.getBeastStats() != null)
                    beast.getBeastStats().draw(g);
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
        double y = Game.window().getResolution().getHeight() - PADDING * 2;
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

    public BattleMenu getAttackMenu() {
        return attackMenu;
    }

    public BattleMenu getBattleMenu() {
        return battleMenu;
    }

}
