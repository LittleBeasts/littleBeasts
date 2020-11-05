package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.PlayerState;
import com.littleBeasts.entities.Player;
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

public class Hud extends GuiComponent {

    private static final int PADDING = 40;
    boolean debug = true;
    private BattleMenu bm;
    private BattleMenu attackMenu;
    boolean drawAttackMenu = false;

    protected Hud() {
        super(0, 0, Game.window().getResolution().getWidth(), Game.window().getResolution().getHeight());

        String[] menuItems = new String[]{"Attack", "Catch", "Item", "Flee"};
        int height = (int) Game.window().getResolution().getHeight();
        int width = (int) Game.window().getResolution().getWidth();
        bm = new BattleMenu(300, height - 140, 200, 100, menuItems);
        bm.onConfirm(c -> {
            switch (c.intValue()) {
                case 0:
                    drawAttackMenu = drawAttackMenu ? false : true;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        });
        String[] attacks = new String[]{"Attack1", "Attack2", "Attack3", "Attack4"};
        attackMenu = new BattleMenu(350, height - 140, 200, 100, attacks);
        attackMenu.onConfirm(c->{
            switch (c.intValue()) {
                case 0:
                    drawAttackMenu = drawAttackMenu ? false : true;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        });

    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        g.setColor(Color.RED);

        if (Game.world().environment() == null || Player.instance().isDead() || Player.instance().getState() != PlayerState.CONTROLLABLE) {
            return;
        }

        //this.renderEnemyUI(g);
        this.renderPlayerUI(g);
        this.renderHP(g);
        this.renderBeasts(g);
        if (GameLogic.getState() == GameState.BATTLE) {
            this.drawIngameHud(g);
            try {
                this.drawBattleHud(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bm.setFocus(false);
        }
        // if (GameLogic.getState() == GameState.BATTLE) {

        //  }

    }

    private void drawBattleHud(Graphics2D g) throws IOException {
        int height = (int) Game.window().getResolution().getHeight();
        int width = (int) Game.window().getResolution().getWidth();
        g.setColor(Color.WHITE);
        //Player portrait and stats.
        drawPlayPortrait(g, width, height);
        //Action menu
        drawActionMenu(g);
        bm.setFocus(drawAttackMenu ? false : true);
        bm.drawMenu(g);
        if (drawAttackMenu) {
            attackMenu.drawMenu(g);
            attackMenu.setFocus(true);
        } else {
            attackMenu.setFocus(false);
        }

        //draw beast portraits and stats
        drawBeastPortraits(g);

    }

    int cint = 0;

    private void drawPlayPortrait(Graphics2D g, int width, int height) throws IOException {
        // if (cint % 60 == 0)
        //     System.out.println("Width: " + width + " | Height: " + height);
        // cint++;
        g.setColor(Color.WHITE);
        Image originalImage = ImageIO.read(new File("sprites/char.png"));
        int padding = 40;
        int elementHeight = 100;
        int elementWidth = 200;
        g.fillRect(padding, height - padding - elementHeight, elementWidth, elementHeight);
        g.drawImage(originalImage, padding, height - padding - elementHeight, 80, 100, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 15));
        String playerStats = "Player Name\n";
        playerStats += "Player Life\n";
        playerStats += "Player Whatever\n";
        drawString(g, playerStats, padding + 100, height - padding - elementHeight);
    }

    private static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private void drawBeastPortraits(Graphics2D g) {

    }

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
        if (debug) {
            System.out.println(x + " | " + y + " || " + Game.window().getResolution().getWidth() + " | " + Game.window().getResolution().getHeight());
            debug = false;
        }
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
        bm.setFocus(focus);
    }
}
