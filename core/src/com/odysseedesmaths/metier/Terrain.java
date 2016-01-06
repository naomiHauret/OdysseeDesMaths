package com.odysseedesmaths.metier;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Terrain {

    private static Terrain terrain = null;

    public static final int HAUTEUR = 20;
    public static final int LARGEUR = HAUTEUR * 2;

    private TiledMap map;
    public TiledMapRenderer renderer;

    private Terrain() {
        this.map = new TmxMapLoader().load("map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    private static void create() {
        terrain = new Terrain();
    }

    public static Terrain get() {
        if (terrain == null) {
            create();
        }
        return terrain;
    }

    public void getDepart() {}
}
