package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.entities.Player;
import com.littleBeasts.PlayerState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.RenderEngine;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.gui.TextFieldComponent;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Hud extends GuiComponent {

    private static final int PADDING = 40;

    protected Hud() {
        super(0, 0, Game.window().getResolution().getWidth(), Game.window().getResolution().getHeight());

    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        g.setColor(Color.RED);

        if (Game.world().environment() == null || Player.instance().isDead() || Player.instance().getState() != PlayerState.CONTROLLABLE || GameLogic.getState() != GameState.INGAME) {
            return;
        }

        //this.renderEnemyUI(g);
        this.renderPlayerUI(g);
        this.renderHP(g);
        this.renderBeasts(g);

    }

    private void renderBeasts(Graphics2D g) {

    }

    private void renderPlayerUI(Graphics2D g) {
    }

    boolean debug = true;

    private void renderHP(Graphics2D g) {


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
        Font font = new Font(g.getFont().getName(),0,30);
        g.setFont(font);
        TextRenderer.render(g, "50/50", x - 100.0, y,true);

        ImageRenderer.render(g, Resources.images().get("sprites/icon.png"), x, y);
    }

}
