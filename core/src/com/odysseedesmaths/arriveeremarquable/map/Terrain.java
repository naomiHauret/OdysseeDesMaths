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

    private final TiledMap map;
    public TiledMapRenderer renderer;

    private Case[][] cases;
    private Case depart;
    private Case fin;

    public Terrain() {
        this.map = new TmxMapLoader().load("map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);

        TiledMapTileLayer baseLayer = (TiledMapTileLayer)map.getLayers().get("base");
        TiledMapTileLayer obstaclesLayer = (TiledMapTileLayer)map.getLayers().get("obstacles");
        TiledMapTileLayer pointsLayer = (TiledMapTileLayer)map.getLayers().get("points");

        cases = new Case[baseLayer.getWidth()][baseLayer.getHeight()];
        for (int i=0; i < baseLayer.getWidth(); i++) {
            for (int j=0; j < baseLayer.getHeight(); j++) {
                cases[i][j] = new Case(i, j, obstaclesLayer.getCell(i,j) != null);
                if (pointsLayer.getCell(i,j) != null) {
                    if (pointsLayer.getCell(i,j).getTile().getProperties().get("depart") != null) depart = cases[i][j];
                    if (pointsLayer.getCell(i,j).getTile().getProperties().get("fin") != null) fin = cases[i][j];
                }
            }
        }
    }

    public Case[][] getCases() {
        return cases;
    }

    public Case getDepart() {
        return depart;
    }
    public Case getFin() {
        return fin;
    }

    public int getWidth() {
        return cases.length;
    }

    public int getHeight() {
        return cases[0].length;
    }

    // Méthodes permettant le Pathfinding
    @Override
    public Set<Case> getVoisins(Case c) {
        Set<Case> voisins = new HashSet<Case>();

        if (c.j+1 < cases[0].length && !cases[c.i][c.j+1].isObstacle()) voisins.add(cases[c.i][c.j+1]);
        if (!cases[c.i][c.j-1].isObstacle()) voisins.add(cases[c.i][c.j-1]);
        if (c.i+1 < cases.length && !cases[c.i+1][c.j].isObstacle()) voisins.add(cases[c.i+1][c.j]);
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