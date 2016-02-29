package com.odysseedesmaths.minigames.coffeePlumbing.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by trilunaire on 20/02/16.
 */
/*TODO: - Faire une fonction permettant de prendre tous les tuyaux (voir les layers de la carte, et les propriétés des objets) - Faire une fonction permettant de trouver les vannes
*/
public class CoffeeLevel {
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private TiledMapTileLayer vannes;
    private TiledMapTileLayer tuyaux;
    private TiledMapTileLayer koffeeMeters;
    /**
     * Représente la largeur en pixel de la map
     */
    private int mapWidthPixel;
    /**
     * Représente la hauteur en pixel de la map
     */
    private int mapHeightPixel;
    private int mapWidthTiled;
    private int mapHeightTiled;
    private int tileWidth;
    private int tileHeight;
    private Case[][] cases;
    private Sprite[][] sprites;


    public CoffeeLevel(String mapPath) {
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);

        //configuration des variables de tailles
        this.tileWidth = ((Integer) this.map.getProperties().get("tilewidth"));
        this.tileHeight = ((Integer) this.map.getProperties().get("tileheight"));

        this.mapWidthTiled = (Integer) this.map.getProperties().get("width");
        this.mapHeightTiled = (Integer) this.map.getProperties().get("height");

        this.mapWidthPixel = mapWidthTiled * tileWidth;
        this.mapHeightPixel = mapHeightTiled * tileHeight;

        //configuration des layers
        this.vannes = (TiledMapTileLayer) map.getLayers().get("vannes");
        this.tuyaux = (TiledMapTileLayer) map.getLayers().get("tuyaux");
        this.koffeeMeters = (TiledMapTileLayer) map.getLayers().get("koffeeMeters");

        this.cases=new Case[mapWidthTiled][mapHeightTiled];
        this.sprites=new Sprite[mapWidthTiled][mapHeightTiled];
    }

    public void createGUI() {
    /*
    * TODO: prendre les tuyaux, leurs capacités, et les vannes
    */
        boolean[][] posTuyaux = getItemsPosition(this.tuyaux);
        boolean[][] posVannes = getItemsPosition(this.vannes);
        boolean[][] posKoffeeMeter = getItemsPosition(this.koffeeMeters);

        /* On prends les obstacles & sprites*/
        for(int i=0; i<mapWidthTiled;i++){
            for(int j=0; j<mapHeightTiled; j++){
                if(!posTuyaux[i][j]){ //si on a un obstacle
                    cases[i][j] = new Case(i,j,!posTuyaux[i][j]);
                    //La ou on a un obstacles (pas tuyau), on n'as pas de sprite
                    sprites[i][j] = null;
                }
                else if(posVannes[i][j] || posKoffeeMeter[i][j]){ //si on a un sprite
                   // sprites[i][j] = new Sprite(i,j);
                    cases[i][j] = null;
                }
            }
        }


        
    }

    /**
     * Return a boolean tab that symbolise the presence of items in the layer
     * @param tiMaTileLayer
     * @return a two dimention boolean tab
     */
    public boolean[][] getItemsPosition(TiledMapTileLayer tiMaTileLayer) {
        boolean[][] pos = new boolean[this.mapWidthTiled][this.mapHeightTiled];
        for (int i = 0; i < this.mapWidthTiled; i++) {
            for (int j = 0; j < this.mapHeightTiled; j++) {
                pos[i][j] = (tiMaTileLayer.getCell(i, j) != null);
            }
        }
        return pos;
    }

    /**
     * Getter of mapHeightPixel
     *
     * @return the value of mapHeightPixel
     */
    public int get_mapHeightPixel() {
        return this.mapHeightPixel;
    }

    /**
     * Setter of mapHeightPixel
     *
     * @param new_mapHeightPixel: new value of mapHeightPixel
     */
    public void set_mapHeightPixel(int new_mapHeightPixel) {
        this.mapHeightPixel = new_mapHeightPixel;
    }

    /**
     * Getter of mapWidthPixel
     *
     * @return the value of mapWidthPixel
     */
    public int get_mapWidthPixel() {
        return this.mapWidthPixel;
    }

    /**
     * Setter of mapWidthPixel
     *
     * @param new_mapWidthPixel: new value of mapWidthPixel
     */
    public void set_mapWidthPixel(int new_mapWidthPixel) {
        this.mapWidthPixel = new_mapWidthPixel;
    }

    /**
     * Getter of mapRenderer
     *
     * @return the value of mapRenderer
     */
    public TiledMapRenderer get_mapRenderer() {
        return this.mapRenderer;
    }

    /**
     * Setter of mapRenderer
     *
     * @param new_mapRenderer: new value of mapRenderer
     */
    public void set_mapRenderer(TiledMapRenderer new_mapRenderer) {
        this.mapRenderer = new_mapRenderer;
    }

    /**
     * Getter of map
     *
     * @return the value of map
     */
    public TiledMap get_map() {
        return this.map;
    }

    /**
     * Setter of map
     *
     * @param new_map: new value of map
     */
    public void set_map(TiledMap new_map) {
        this.map = new_map;
    }

    /**
     * Getter of tileHeight
     *
     * @return the value of tileHeight
     */
    public int get_tileHeight() {
        return this.tileHeight;
    }

    /**
     * Setter of tileHeight
     *
     * @param new_tileHeight: new value of tileHeight
     */
    public void set_tileHeight(int new_tileHeight) {
        this.tileHeight = new_tileHeight;
    }

    /**
     * Getter of tileWeight
     *
     * @return the value of tileWeight
     */
    public int get_tileWidth() {
        return this.tileWidth;
    }

    /**
     * Setter of tileWeight
     *
     * @param new_tileWidth: new value of tileWeight
     */
    public void set_tileWidth(int new_tileWidth) {
        this.tileWidth = new_tileWidth;
    }

    /**
     * Getter of vannes
     *
     * @return the value of vannes
     */
    public TiledMapTileLayer get_vannes() {
        return this.vannes;
    }

    /**
     * Setter of vannes
     *
     * @param new_vannes: the new value of vannes
     */
    public void set_vannes(TiledMapTileLayer new_vannes) {
        this.vannes = new_vannes;
    }

    /**
     * Getter of tuyaux
     *
     * @return the value of tuyaux
     */
    public TiledMapTileLayer get_tuyaux() {
        return this.tuyaux;
    }

    /**
     * Setter of tuyaux
     *
     * @param new_tuyaux: the new value of tuyaux
     */
    public void set_tuyaux(TiledMapTileLayer new_tuyaux) {
        this.tuyaux = new_tuyaux;
    }

    /**
     * Getter of koffeeMeters
     *
     * @return the value of koffeeMeters
     */
    public TiledMapTileLayer get_koffeeMeters() {
        return this.koffeeMeters;
    }

    /**
     * Setter of koffeeMeters
     *
     * @param new_koffeeMeters: the new value of koffeeMeters
     */
    public void set_koffeeMeters(TiledMapTileLayer new_koffeeMeters) {
        this.koffeeMeters = new_koffeeMeters;
    }

    /**
     * Getter of mapWidthTiled
     *
     * @return the value of mapWidthTiled
     */
    public int get_mapWidthTiled() {
        return this.mapWidthTiled;
    }

    /**
     * Setter of mapWidthTiled
     *
     * @param new_mapWidthTiled: the new value of mapWidthTiled
     */
    public void set_mapWidthTiled(int new_mapWidthTiled) {
        this.mapWidthTiled = new_mapWidthTiled;
    }

    /**
     * Getter ofmapHeightTiled
     *
     * @return the value ofmapHeightTiled
     */
    public int get_mapHeightTiled() {
        return this.mapHeightTiled;
    }

    /**
     * Setter ofmapHeightTiled
     *
     * @param new_mapHeightTiled: the new value ofmapHeightTiled
     */
    public void set_mapHeightTiled(int new_mapHeightTiled) {
        this.mapHeightTiled = new_mapHeightTiled;
    }

    /**
    * Getter of sprite
    * @return the value of sprite
    */
    public Sprite[][] get_sprites(){
      return this.sprites;
    }

    /**
    * Setter of sprite
    * @param new_sprite: the new value of sprite
    */
    public void set_sprites(Sprite[][] new_sprites){
      this.sprites = new_sprites;
    }

    public void dispose() {
        map.dispose();
    }
}
