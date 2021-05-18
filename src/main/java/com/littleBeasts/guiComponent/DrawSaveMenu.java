package com.littleBeasts.guiComponent;

import com.littleBeasts.Program;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.screens.InventoryState;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

import static config.HudConstants.HEIGHT;
import static config.HudConstants.WIDTH;

public class DrawSaveMenu extends GuiComponent {

    protected DrawSaveMenu(double x, double y, double width, double height) {
        super(x, y, width, height);

        Input.keyboard().onKeyTyped(KeyEvent.VK_W, e -> {
            if (Program.getGameLogic().getState() == GameState.SAVE_MENU) {
                InventoryState.nextState();
            }
        });

        Input.keyboard().onKeyTyped(KeyEvent.VK_S, e -> {
            if (Program.getGameLogic().getState() == GameState.SAVE_MENU) {
                InventoryState.previousState();
            }
        });

    }

    @Override
    public void render(Graphics2D g) {
        if (Program.getGameLogic().getState().equals(GameState.INVENTORY)) {
            g.setColor(Color.gray);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            //rectangle
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawRect((WIDTH / 16), HEIGHT / 16, (WIDTH - WIDTH / 8), HEIGHT - HEIGHT / 4);


            //Right Side
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine((WIDTH / 2) + 20, HEIGHT / 3 * 2, WIDTH - 20, HEIGHT / 3 * 2);


        }


    }

}
