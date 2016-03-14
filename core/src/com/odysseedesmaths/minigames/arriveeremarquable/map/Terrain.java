package com.odysseedesmaths.minigames.arriveeremarquable.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.odysseedesmaths.pathfinding.Pathfindable;

import java.util.HashSet;
import java.util.Set;

public class Terrain implements Pathfindable<Case> {

    private final TiledMap map;
    public BatchTiledMapRenderer renderer;

    private final Case[][] cases;
    private final Case depart;
    private final Case fin;

    public Terrain() {
        int height, width;

        // Récupération d'une map
        this.map = new TmxMapLoader().load("map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);

        // Récupération de ses dimensions
        width = Integer.valueOf((String)map.getProperties().get("width"));
        height = Integer.valueOf((String)map.getProperties().get("height"));
        this.cases = new Case[width][height];

        // Récupération des layers nécessaires
        TiledMapTileLayer obstaclesLayer = (TiledMapTileLayer)map.getLayers().get("obstacles");

        // Création des cases
        for (int i=0; i < width; i++) {
            for (int j=0; j < height; j++) {
                cases[i][j] = new Case(i, j, obstaclesLayer.getCell(i,j) != null);
            }
        }

        // Récupération des points de départ et d'arrivée
        String[] mapDepart = ((String)map.getProperties().get("start")).split(",");
        String[] mapFin = ((String)map.getProperties().get("end")).split(",");
        this.depart = cases[Integer.valueOf(mapDepart[0])][Integer.valueOf(mapDepart[1])];
        this.fin = cases[Integer.valueOf(mapFin[0])][Integer.valueOf(mapFin[1])];
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

        if ((c.j+1 < getHeight()) && !cases[c.i][c.j+1].isObstacle()) {
            voisins.add(cases[c.i][c.j+1]);
        }
        if ((c.j-1 > 0) && !cases[c.i][c.j-1].isObstacle()) {
            voisins.add(cases[c.i][c.j-1]);
        }
        if ((c.i+1 < getWidth()) && !cases[c.i+1][c.j].isObstacle()) {
            voisins.add(cases[c.i+1][c.j]);
        }
        if ((c.i-1 > 0) && !cases[c.i-1][c.j].isObstacle()) {
            voisins.add(cases[c.i-1][c.j]);
        }

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

    // Vision en ligne
    public boolean seeEachOther(Case c1, Case c2) {
        if (c2.i == c1.i) {
            int i = c1.i;
            for (int j = Math.min(c2.j, c1.j); j < Math.max(c2.j, c1.j); j++) {
                if (cases[i][j].isObstacle()) return false;
            }
        } else if (c2.j == c1.j) {
            int j = c1.j;
            for (int i = Math.min(c2.i, c1.i); i < Math.max(c2.i, c1.i); i++) {
                if (cases[i][j].isObstacle()) return false;
            }
        } else {
            return false;
        }

        return true;
    }
}
