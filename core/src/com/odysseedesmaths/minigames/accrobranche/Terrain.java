package com.odysseedesmaths.minigames.accrobranche;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Terrain {
    private final TiledMap map;
    public TiledMapRenderer renderer;

    public Terrain() {
        int height, width;

        // Récupération d'une map
        this.map = new TmxMapLoader().load("accrobranche/test.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);

        // Récupération de ses dimensions
        width = (Integer)map.getProperties().get("width");
        height = (Integer)map.getProperties().get("height");

        // Récupération des layers nécessaires
        TiledMapTileLayer platformesLayer = (TiledMapTileLayer)map.getLayers().get("platformes");

        // Récupération des points de départ et d'arrivée
        //String[] mapDepart = ((String)map.getProperties().get("start")).split(",");
        //String[] mapFin = ((String)map.getProperties().get("end")).split(",");
        //this.depart = cases[Integer.valueOf(mapDepart[0])][Integer.valueOf(mapDepart[1])];
        //this.fin = cases[Integer.valueOf(mapFin[0])][Integer.valueOf(mapFin[1])];
    }
}
