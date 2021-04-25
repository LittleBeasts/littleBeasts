package com.littleBeasts.gameLogic;

import com.littleBeasts.entities.LitiInteractable;
import com.littleBeasts.entities.LitiPet;
import com.littleBeasts.entities.LitiPlayer;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.tilemap.ITileLayer;
import de.gurkenlabs.litiengine.graphics.RenderType;

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

    private List<ITileLayer> tileMapLayers;
    private Collection<MapArea> mapAreas;
    private Collection<Spawnpoint> spawnpoints;
    private static ArrayList<LitiInteractable> litiInteractables;
    private static final ArrayList<Font> gameFonts = new ArrayList<>();
    private boolean freshlySpawned = true;
    private long start = System.currentTimeMillis();
    private int spawnDelay = 1000; //delay in milliseconds
    private List<Integer> changedTileLayers = new ArrayList<>();
    private boolean deactivateOverlays = false;
    private boolean isInOverlayArea = false;

    public void loadNewArea() {
        if (freshlySpawned || (start + spawnDelay) >= System.currentTimeMillis())
            return;
        if (spawnpoints == null)
            loadCurrentSpawnPoints();
        Point2D playerPosition;
        Rectangle2D mapArea;
        for (Spawnpoint area : spawnpoints) {
            mapArea = area.getBoundingBox();
            playerPosition = LitiPlayer.instance().getCenter();
            playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
            if (mapArea.contains(playerPosition)) {
                if (area.getSpawnInfo() != null && area.getSpawnInfo().equals("culdesac"))
                    return;
                if (area.getName() != null) {
                    this.freshlySpawned = true;
                    String spawnpointName = Game.world().environment().getMap().getName();
                    String targetMapName = area.getName();

                    if (targetMapName.contains("#")) {
                        spawnpointName += targetMapName.substring(targetMapName.indexOf("#"));
                        targetMapName = targetMapName.substring(0, targetMapName.indexOf("#"));
                    }
                    if (DEBUG_CONSOLE_OUT) {
                        System.out.println(spawnpointName);
                        System.out.println(targetMapName);
                    }

                    Game.world().loadEnvironment(targetMapName);
                    Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint(spawnpointName);
                    if (spawnpoint != null) {
                        spawnpoint.spawn(LitiPlayer.instance());
                        spawnpoint.spawn(LitiPet.instance());
                    }
                    LitiPlayer.instance().setFacingDirection(spawnpoint.getDirection());
                    LitiPet.instance().setFacingDirection(spawnpoint.getDirection());

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
        loadCurrentSpawnPoints();
        this.freshlySpawned = true;
        this.start = System.currentTimeMillis();
    }

    private void loadCurrentSpawnPoints() {
        spawnpoints = Game.world().environment().getSpawnPoints();
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


    public void checkFreshlySpawned() {
        Point2D playerPosition;
        Rectangle2D mapArea;

        if (this.freshlySpawned) {
            this.freshlySpawned = false;
            for (Spawnpoint spawnpoint : spawnpoints) {
                mapArea = spawnpoint.getBoundingBox();
                playerPosition = LitiPlayer.instance().getCenter();
                playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 6);
                if (mapArea.contains(playerPosition.getX(), playerPosition.getY())) {
                    this.freshlySpawned = true;
                    break;
                }
            }
        }
    }

    public void checkOpacity() {
        String layerName = "";
        for (MapArea area : mapAreas) {
            if (area.getName().contains("OPACITY-") && area.getBoundingBox().contains(LitiPlayer.instance().getCenter())) {
                layerName = area.getName().replace("OPACITY-", "");
            }
        }

        for (ITileLayer layer : tileMapLayers) {
            if (layer.getName().equals(layerName)) {
                layer.setOpacity(0.5f);
            } else
                layer.setOpacity(1.f);
        }
    }

    public void checkOverlays() {
        Point2D playerPosition = LitiPlayer.instance().getCenter();
        playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
        isInOverlayArea = false;
        for (MapArea area : mapAreas) {
            if (area.getName().contains("OVERLAY-") && area.getBoundingBox().contains(playerPosition)) {
                isInOverlayArea = true;
                for (int i = 0; i < tileMapLayers.size(); i++) {
                    ITileLayer layer = tileMapLayers.get(i);
                    if (layer.getRenderType().equals(RenderType.OVERLAY)) {
                        changedTileLayers.add(i);
                        layer.setRenderType(RenderType.NORMAL);
                        if (layer.getName().equals(area.getName().replace("OVERLAY-", "")))
                            deactivateOverlays = true;
                    }
                    if (deactivateOverlays)
                        break;
                }
                if (deactivateOverlays)
                    break;
            }
        }

        if (!isInOverlayArea && changedTileLayers != null && changedTileLayers.size() > 0) {
            for (int position : changedTileLayers) {
                tileMapLayers.get(position).setRenderType(RenderType.OVERLAY);
            }
            changedTileLayers = new ArrayList<>();
            deactivateOverlays = false;
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
