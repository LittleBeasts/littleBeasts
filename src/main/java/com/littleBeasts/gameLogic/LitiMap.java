package com.littleBeasts.gameLogic;

import com.littleBeasts.Program;
import com.littleBeasts.entities.*;
import com.littleBeasts.sceneManager.SceneNotPossibleError;
import com.littleBeasts.sceneManager.ScenePlayer;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.tilemap.ITileLayer;
import de.gurkenlabs.litiengine.graphics.RenderType;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiMap {

    private List<ITileLayer> tileMapLayers;
    private Collection<MapArea> mapAreas;
    private Collection<Spawnpoint> spawnpoints;
    private static ArrayList<Interactable> Interactables;
    private boolean freshlySpawned = true;
    private long start = System.currentTimeMillis();
    private List<Integer> changedTileLayers = new ArrayList<>();
    private boolean deactivateOverlays = false;

    public void loadNewArea() {
        int spawnDelay = 2000; //delay in milliseconds
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
                if (checkMapAreaForSpawnPoint(area)) return;
            }
        }
    }

    public void checkAreas() throws SceneNotPossibleError {
        for (MapArea area : mapAreas) {
            if (area.getName().contains("OPACITY-") && area.getBoundingBox().contains(LitiPlayer.instance().getCenter())) {
                checkOpacity(area.getName().replace("OPACITY-", ""));
            } else if (area.getName().contains("SCENE-") && area.getBoundingBox().contains(LitiPlayer.instance().getCenter())) {
                checkSceneAreas(area.getName().replace("SCENE-", ""));
            }
        }
    }

    public void checkSceneAreas(String scene) throws SceneNotPossibleError {
        int dayInt = Integer.parseInt(scene.substring(0, scene.indexOf("-")));
        int sceneInt = Integer.parseInt(scene.substring(scene.indexOf("-") + 1));
        ScenePlayer.startScene(dayInt, sceneInt);
    }

    private boolean checkMapAreaForSpawnPoint(Spawnpoint area) {
        if (area.getSpawnInfo() != null && area.getSpawnInfo().equals("culdesac"))
            return true;
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

            spawnPlayer(spawnpointName, targetMapName);
            newMapLoadUp();
        }
        return false;
    }

    private void spawnPlayer(String spawnpointName, String targetMapName) {
        Game.world().loadEnvironment(targetMapName);
        Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint(spawnpointName);
        if (spawnpoint != null) {
            spawnpoint.spawn(LitiPlayer.instance());
            spawnpoint.spawn(LitiPet.instance());
        }
        assert spawnpoint != null;
        LitiPlayer.instance().setFacingDirection(spawnpoint.getDirection());
        LitiPet.instance().setFacingDirection(spawnpoint.getDirection());

        switch (spawnpoint.getDirection()) {
            case LEFT:
                LitiPet.instance().setX(LitiPet.instance().getX() - 16);
                break;
            case RIGHT:
                LitiPet.instance().setX(LitiPet.instance().getX() + 16);
                break;
            case DOWN:
                LitiPet.instance().setY(LitiPet.instance().getY() + 16);
                break;
            case UP:
                LitiPet.instance().setY(LitiPet.instance().getY() - 16);
                break;
        }
        LitiPlayer.instance().setRenderWithLayer(true);
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
        Interactables = new ArrayList<>();
        Collection<IEntity> collectionNpc = Game.world().environment().getEntities();
        for (IEntity entity : collectionNpc) {
            if (entity.getName() != null) {
                if (entity.getName().contains("NPC-"))
                    Interactables.add(new LitiNPC(entity));
                if (entity.getName().contains("CHEST-"))
                    Interactables.add(new LitiProp(entity));
            }
        }
    }

    private void createTileMapLayerList() {
        this.tileMapLayers = Game.world().environment().getMap().getTileLayers();
    }

    public void checkFreshlySpawned() {
        Point2D playerPosition;
        Rectangle2D mapArea;

        if (this.freshlySpawned) {
            this.freshlySpawned = false;
            for (Spawnpoint spawnpoint : spawnpoints) {
                mapArea = spawnpoint.getBoundingBox();
                playerPosition = LitiPlayer.instance().getCenter();
                playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
                if (mapArea.contains(playerPosition.getX(), playerPosition.getY())) {
                    this.freshlySpawned = true;
                    this.start = System.currentTimeMillis(); // reset timer for spawn check.
                    break;
                }
            }
        }
    }

    public void checkOpacity(String layerName) {
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
        boolean isInOverlayArea = false;
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

    public void update() throws SceneNotPossibleError {
        if (Program.getGameLogic().getState() != GameState.INGAME) return;
        loadNewArea();
        checkFreshlySpawned();
        checkAreas();
        checkOverlays();
    }

    public ArrayList<Interactable> getInteractables() {
        return Interactables;
    }
}
