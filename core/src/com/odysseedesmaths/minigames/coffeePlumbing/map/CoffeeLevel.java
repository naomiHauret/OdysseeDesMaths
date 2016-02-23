package com.odysseedesmaths.minigames.coffeePlumbing.map;

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
  /**
  * Représente la largeur en pixel de la map
  */
  private int mapWidth;
  /**
  * Représente la hauteur en pixel de la map
  */
  private int mapHeight;
  private int tileWidth;
  private int tileHeight;

  public CoffeeLevel(String mapPath){
    this.map = new TmxMapLoader().load(mapPath);
    this.mapRenderer = new OrthogonalTiledMapRenderer(map);

    //configuration des variables de tailles
    this.tileWidth = ((Integer)this.map.getProperties().get("tilewidth"));
    this.tileHeight = ((Integer)this.map.getProperties().get("tileheight"));

    this.mapWidth =  (Integer)this.map.getProperties().get("width") * tileWidth;
    this.mapHeight = ((Integer)this.map.getProperties().get("height"))*tileHeight;

    System.out.println(tileWidth+"+"+tileHeight+"+"+mapWidth+"+"+mapHeight);

    //configuration des layers
    this.vannes = (TiledMapTileLayer)map.getLayers().get("vannes");
    this.tuyaux = (TiledMapTileLayer)map.getLayers().get("tuyaux");
    System.out.println("Sparttaaaaaaaa");
  }

  /**
  * Getter of mapHeight
  * @return the value of mapHeight
  */
  public int get_mapHeight(){
    return this.mapHeight;
  }

  /**
  * Setter of mapHeight
  * @param new_mapHeight: new value of mapHeight
  */
  public void set_mapHeight(int new_mapHeight){
    this.mapHeight = new_mapHeight;
  }

  /**
  * Getter of mapWidth
  * @return the value of mapWidth
  */
  public int get_mapWidth(){
    return this.mapWidth;
  }

  /**
  * Setter of mapWidth
  * @param new_mapWidth: new value of mapWidth
  */
  public void set_mapWidth(int new_mapWidth){
    this.mapWidth = new_mapWidth;
  }

  /**
  * Getter of mapRenderer
  * @return the value of mapRenderer
  */
  public TiledMapRenderer get_mapRenderer(){
    return this.mapRenderer;
  }

  /**
  * Setter of mapRenderer
  * @param new_mapRenderer: new value of mapRenderer
  */
  public void set_mapRenderer(TiledMapRenderer new_mapRenderer){
    this.mapRenderer = new_mapRenderer;
  }

  /**
  * Getter of map
  * @return the value of map
  */
  public TiledMap get_map(){
    return this.map;
  }

  /**
  * Setter of map
  * @param new_map: new value of map
  */
  public void set_map(TiledMap new_map){
    this.map = new_map;
  }

  /**
  * Getter of tileHeight
  * @return the value of tileHeight
  */
  public int get_tileHeight(){
    return this.tileHeight;
  }

  /**
  * Setter of tileHeight
  * @param new_tileHeight: new value of tileHeight
  */
  public void set_tileHeight(int new_tileHeight){
    this.tileHeight = new_tileHeight;
  }

  /**
  * Getter of tileWeight
  * @return the value of tileWeight
  */
  public int get_tileWidth(){
    return this.tileWidth;
  }

  /**
  * Setter of tileWeight
  * @param new_tileWidth: new value of tileWeight
  */
  public void set_tileWidth(int new_tileWidth){
    this.tileWidth = new_tileWidth;
  }

  /**
  * Getter of vannes
  * @return the value of vannes
  */
  public TiledMapTileLayer get_vannes(){
    return this.vannes;
  }

  /**
  * Setter of vannes
  * @param new_vannes: the new value of vannes
  */
  public void set_vannes(TiledMapTileLayer new_vannes){
    this.vannes = new_vannes;
  }

  /**
  * Getter of tuyaux
  * @return the value of tuyaux
  */
  public TiledMapTileLayer get_tuyaux(){
    return this.tuyaux;
  }

  /**
  * Setter of tuyaux
  * @param new_tuyaux: the new value of tuyaux
  */
  public void set_tuyaux(TiledMapTileLayer new_tuyaux){
    this.tuyaux = new_tuyaux;
  }

  public void dispose(){
    map.dispose();
  }
}
