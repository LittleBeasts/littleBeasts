package com.littlebeasts.gamelogic;

import com.littlebeasts.Program;
import com.littlebeasts.entities.Interactable;
import com.littlebeasts.entities.LitiAreaSign;
import com.littlebeasts.entities.LitiNPC;
import com.littlebeasts.entities.LitiPet;
import com.littlebeasts.entities.LitiPlayer;
import com.littlebeasts.entities.LitiPropChest;
import com.littlebeasts.entities.LitiPropDoor;
import com.littlebeasts.entities.LitiPropSign;
import com.littlebeasts.scenemanager.SceneNotPossibleError;
import com.littlebeasts.scenemanager.ScenePlayerHelper;
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

import static config.GlobalConstants.DEBUG_CONSOLE_OUT;

public class LitiMap {

    private List<ITileLayer> tileMapLayers;
    private Collection<MapArea> mapAreas;
    private Collection<Spawnpoint> spawnpoints;
    private static ArrayList<Interactable> Interactables;
    private boolean freshlySpawned = true;
    private long freshlySpawnedTime = System.currentTimeMillis();
    private List<Integer> changedTileLayers = new ArrayList<>();
    private boolean deactivateOverlays = false;

    public void loadNewArea() {
        int spawnDelay = 1000; //delay in milliseconds
        if (freshlySpawned || (freshlySpawnedTime + spawnDelay) >= System.currentTimeMillis())
            return;
        if (spawnpoints == null)
            loadCurrentSpawnPoints();
        Point2D playerPosition = LitiPlayer.instance().getCenter();
        playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
        Rectangle2D mapArea;
        for (Spawnpoint spawnpoint : spawnpoints) {
            mapArea = spawnpoint.getBoundingBox();
            if (isFacingCorrectDirection(spawnpoint) && mapArea.contains(playerPosition) && checkMapAreaForSpawnPoint(spawnpoint)) {
                return;
            }
        }
    }

    public boolean isFacingCorrectDirection(Spawnpoint spawnpoint) {
        return spawnpoint.getDirection() == LitiPlayer.instance().getFacingDirection().getOpposite();
    }

    public void checkAreas() throws SceneNotPossibleError {
        String layerName = "";
        for (MapArea area : mapAreas) {
            if (area.getBoundingBox().contains(LitiPlayer.instance().getCenter()))
                if (area.getName().contains("OPACITY-")) {
                    layerName = area.getName().replace("OPACITY-", "");
                } else if (area.getName().contains("SCENE-")) {
                    checkSceneAreas(area.getName().replace("SCENE-", ""));
                }
        }
        setOpacityForMapLayer(layerName);
    }

    public void checkSceneAreas(String scene) throws SceneNotPossibleError {
        int dayInt = Integer.parseInt(scene.substring(0, scene.indexOf("-")));
        int sceneInt = Integer.parseInt(scene.substring(scene.indexOf("-") + 1));
        ScenePlayerHelper.startScene(dayInt, sceneInt);
    }

    private boolean checkMapAreaForSpawnPoint(Spawnpoint spawnpoint) {
        if (spawnpoint.getSpawnInfo() != null && spawnpoint.getSpawnInfo().equals("culdesac"))
            return true;
        if (spawnpoint.getName() != null) {
            this.freshlySpawned = true;
            String spawnpointName = Game.world().environment().getMap().getName();
            String targetMapName = spawnpoint.getName();

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
    }

    public void newMapLoadUp() {
        Program.getGameLogic().setState(GameState.LOADING);
        resetOldMap();
        loadCurrentMapAreas();
        createTileMapLayerList();
        createInteractableList();
        loadCurrentSpawnPoints();
        this.freshlySpawned = true;
        this.freshlySpawnedTime = System.currentTimeMillis();
        Program.getGameLogic().setState(GameState.INGAME);
    }

    private void resetOldMap() {
        for (Integer layerNumber : changedTileLayers) {
            tileMapLayers.get(layerNumber).setRenderType(RenderType.OVERLAY);
        }
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
                else if (entity.getName().contains("CHEST-"))
                    Interactables.add(new LitiPropChest(entity));
                else if (entity.getName().contains("DOOR-"))
                    Interactables.add((new LitiPropDoor(entity)));
                else if (entity.getName().contains("SIGN-"))
                    Interactables.add((new LitiPropSign(entity)));
            }
        }
        for (MapArea area : mapAreas) {
            if (area.getName().contains("INTERACTION-"))
                Interactables.add(new LitiAreaSign(area));
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
                    this.freshlySpawnedTime = System.currentTimeMillis(); // reset timer for spawn check.
                    break;
                }
            }
        }
    }

    private void setOpacityForMapLayer(String layerName) {
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
