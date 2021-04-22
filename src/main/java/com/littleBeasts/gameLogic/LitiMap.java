package com.littleBeasts.gameLogic;

import com.littleBeasts.entities.LitiInteractable;
import com.littleBeasts.entities.LitiPlayer;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.tilemap.ITileLayer;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiMap {

    List<ITileLayer> tileMapLayers;
    Collection<MapArea> mapAreas;
    private static ArrayList<LitiInteractable> litiInteractables;
    private static final ArrayList<Font> gameFonts = new ArrayList<>();

    public void loadNewArea() {
        if (mapAreas == null)
            loadCurrentMapAreas();
        Point2D playerPosition;
        Rectangle2D mapArea;
        for (MapArea area : mapAreas) {
            mapArea = area.getBoundingBox();
            playerPosition = LitiPlayer.instance().getCenter();
            playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
            if (mapArea.contains(playerPosition)) {
                if (area.getName() != null && area.getName().contains("AREA-")) {
                    String originName = Game.world().environment().getMap().getName();
                    if (DEBUG_CONSOLE_OUT) System.out.println(area.getName().replace("AREA-", ""));
                    Game.world().loadEnvironment(area.getName().replace("AREA-", ""));
                    Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint(originName);
                    if (spawnpoint != null) {
                        spawnpoint.spawn(LitiPlayer.instance());
                    }
                    LitiPlayer.instance().setFacingDirection(Direction.DOWN);
                    LitiPlayer.instance().setRenderWithLayer(true);
                    newMapLoadUp();
                }
            }
        }
    }

    public void newMapLoadUp() {
        createInteractableList();
        createTileMapLayerList();
        loadCurrentMapAreas();
    }

    private void loadCurrentMapAreas() {
        mapAreas = Game.world().environment().getAreas();
    }

    public void createInteractableList() {
        litiInteractables = new ArrayList<>();
        Collection<IEntity> collectionNpc = Game.world().environment().getEntities();
        for (IEntity entity : collectionNpc) {
            if (entity.getName() != null) {
                if (entity.getName().contains("NPC-")) {
                    litiInteractables.add(new LitiInteractable(entity, true));
                } else if (entity.getName().contains("CHEST-")) {
                    litiInteractables.add(new LitiInteractable(entity, false));
                }
            }
        }
    }

    private void createTileMapLayerList() {
        this.tileMapLayers = Game.world().environment().getMap().getTileLayers();
    }

    public static ArrayList<LitiInteractable> getInteractables() {
        return litiInteractables;
    }

    public void checkOpacity() {
        String layerName = "";
        for (MapArea area : mapAreas) {
            if (area.getName().contains("OVERLAY-") && area.getBoundingBox().contains(LitiPlayer.instance().getCenter())) {
                layerName = area.getName().replace("OVERLAY-", "");
            }
        }

        for (ITileLayer layer : tileMapLayers) {
            if (layer.getName().equals(layerName)) {
                layer.setOpacity(0.5f);
            } else
                layer.setOpacity(1.f);
        }
    }

    public static void loadFonts() throws IOException, FontFormatException {
        String pathName = "./Fonts";
        File path = new File(pathName);
        String[] fontFilesNames = path.list();
        assert fontFilesNames != null;
        for (String fontFileName : fontFilesNames) {
            File fontFile = new File(pathName + "/" + fontFileName);
            Font gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            gameFont = gameFont.deriveFont(10.f);
            gameFonts.add(gameFont);
        }
    }

    public static ArrayList<Font> getGameFonts() {
        return gameFonts;
    }

}
