package com.littleBeasts.screens;

import java.awt.Color;
import java.awt.Graphics2D;


import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;

public class MenuScreen extends Screen implements IUpdateable {

    private static final String COPYRIGHT = "2020 littleBeasts";

    public long lastPlayed;

    private KeyboardMenu mainMenu;
    private boolean renderInstructions;

    public MenuScreen(){
        super("MAINMENU");
    }

    private void exit(){
        System.exit(0);
    }

    @Override
    protected void initializeComponents() {
        final double centerX = Game.window().getResolution().getWidth() / 2.0;
        final double centerY = Game.window().getResolution().getHeight() * 1 / 2;
        final double buttonWidth = 450;

        this.mainMenu = new KeyboardMenu(centerX - buttonWidth / 2, centerY * 1.3, buttonWidth, centerY / 2, "Play", "Instructions", "Exit");

        this.getComponents().add(this.mainMenu);

        this.mainMenu.onChange(c -> {
            this.renderInstructions = c == 1;
        });

        this.mainMenu.onConfirm(c -> {
            switch (c.intValue()) {
                case 0:
                    this.startGame();
                    break;
                case 2:
                    this.exit();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void prepare() {
        this.mainMenu.setEnabled(true);
        super.prepare();
        Game.loop().attach(this);
        Game.window().getRenderComponent().setBackground(Color.BLACK);
        Game.graphics().setBaseRenderScale(6f * Game.window().getResolutionScale());
        this.mainMenu.incFocus();
        Game.world().loadEnvironment("Arkham");
        Game.world().camera().setFocus(Game.world().environment().getCenter());
    }

    @Override
    public void render(final Graphics2D g) {
        Game.world().environment().render(g);
       // this.renderScrollingStuff(g);
        final double centerX = Game.window().getResolution().getWidth() / 2.0;
        //final double logoX = centerX - LOGO_COIN.getWidth() / 2;
        //final double logoY = Game.window().getResolution().getHeight() * 1 / 12;
        //ImageRenderer.render(g, LOGO_COIN, logoX, logoY);
        // ImageRenderer.render(g, LOGO_TEXT, logoX, logoY);
        //g.setFont(GameManager.GUI_FONT);
        final double stringWidth = g.getFontMetrics().stringWidth(COPYRIGHT);
        g.setColor(Color.WHITE);
        TextRenderer.renderWithOutline(g, COPYRIGHT, centerX - stringWidth / 2, Game.window().getResolution().getHeight() * 19 / 20, Color.BLACK,true);

      //  if (this.renderInstructions) {
     //       final double controlsY = Game.window().getResolution().getHeight() - MenuScreen.CONTROLS.getHeight() - 20;
      //      ImageRenderer.render(g, MenuScreen.CONTROLS, 20, controlsY);
      //  }
        super.render(g);
    }

    private void startGame() {
        this.mainMenu.setEnabled(false);
        //Game.audio().playSound("confirm.ogg");
        Game.window().getRenderComponent().fadeOut(2500);

        Game.loop().perform(3500, () -> {
            //Game.screens().display("INGAME-SCREEN");
            //Game.world().loadEnvironment(GameLogic.START_LEVEL);
            GameLogic.setState(GameState.INGAME);
            Game.screens().display("INGAME-SCREEN");
            Game.world().loadEnvironment("Arkham");
        });
    }

    @Override
    public void suspend() {
        super.suspend();
        Game.loop().detach(this);
        Game.audio().stopMusic();
    }

    @Override
    public void update() {
        if (this.lastPlayed == 0) {
           // Game.audio().playMusic(Resources.sounds().get("menumusic.ogg"));
            this.lastPlayed = Game.loop().getTicks();
        }
    }

}
