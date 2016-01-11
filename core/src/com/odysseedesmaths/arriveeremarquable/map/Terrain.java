package com.odysseedesmaths.arriveeremarquable.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.odysseedesmaths.pathfinding.Pathfindable;

import java.util.HashSet;
import java.util.Set;

public class Terrain implements Pathfindable<Case> {

    private TiledMap map;
    private TiledMapRenderer renderer;
    private Case[][] cases;

    private static Terrain terrain = null;

    private Terrain() {
        this.map = new TmxMapLoader().load("map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);

        TiledMapTileLayer baseLayer = (TiledMapTileLayer)map.getLayers().get("base");
        TiledMapTileLayer obstaclesLayer = (TiledMapTileLayer)map.getLayers().get("obstacles");

        cases = new Case[baseLayer.getHeight()][baseLayer.getWidth()];
        for (int i=0; i < baseLayer.getHeight(); i++) {
            for (int j=0; j < baseLayer.getWidth(); j++) {
                cases[i][j] = new Case(i, j, obstaclesLayer.getCell(i,j) != null);
            }
        }
    }

    public static Terrain create() {
        terrain = new Terrain();
        return terrain;
    }

    public static Terrain get() {
        if (terrain == null) {
            create();
        }
        return terrain;
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public Case getCase(int i, int j) {
        return cases[i][j];
    }

    public Case getDepart() {
        return cases[3][4];
    }

    @Override
    public Set<Case> getVoisins(Case c) {
        Set<Case> voisins = new HashSet<Case>();

        if (!cases[c.i][c.j+1].isObstacle()) voisins.add(cases[c.i][c.j+1]);
        if (!cases[c.i][c.j-1].isObstacle()) voisins.add(cases[c.i][c.j-1]);
        if (!cases[c.i+1][c.j].isObstacle()) voisins.add(cases[c.i+1][c.j]);
        if (!cases[c.i-1][c.j].isObstacle()) voisins.add(cases[c.i-1][c.j]);

        return voisins;
    }

    @Override
    public int heuristic(Case e1, Case e2) {
        // Distance de Manhattan
        return Math.abs(e1.i - e2.i) + Math.abs(e1.j - e2.j);
    }

    @Override
    public int cost(Case e1, Case e2) {
        // Tous les déplacements ont le même coût
        return 1;
    }
}