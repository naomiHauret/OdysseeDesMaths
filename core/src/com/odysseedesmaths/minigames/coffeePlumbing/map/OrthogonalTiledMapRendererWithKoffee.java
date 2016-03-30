package com.odysseedesmaths.minigames.coffeePlumbing.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class OrthogonalTiledMapRendererWithKoffee extends OrthogonalTiledMapRenderer {
    private static List<Sprite> superStrongKoffee;
    private int numberOfSpriteLayer = 4;

    public OrthogonalTiledMapRendererWithKoffee(TiledMap map){
        super(map);
        superStrongKoffee = new ArrayList<Sprite>();
    }

    /**
    * Getter of superStrongKoffee
    * @return the value of superStrongKoffee
    */
    public List<Sprite> get_superStrongKoffee(){
      return this.superStrongKoffee;
    }

    /**
    * Setter of superStrongKoffee
    * @param new_superStrongKoffee: the new value of superStrongKoffee
    */
    public void set_superStrongKoffee(List<Sprite> new_superStrongKoffee){
      this.superStrongKoffee = new_superStrongKoffee;
    }

    /**
    * Getter of numberOfSpriteLayer
    * @return the value of numberOfSpriteLayer
    */
    public int get_numberOfSpriteLayer(){
      return this.numberOfSpriteLayer;
    }

    /**
    * Setter of numberOfSpriteLayer
    * @param new_numberOfSpriteLayer: the new value of numberOfSpriteLayer
    */
    public void set_numberOfSpriteLayer(int new_numberOfSpriteLayer){
      this.numberOfSpriteLayer = new_numberOfSpriteLayer;
    }

    public void render(){
        beginRender();
        int layerCourant = 0;
        for(MapLayer layer : map.getLayers()){
            if(layer instanceof TiledMapTileLayer){
                renderTileLayer((TiledMapTileLayer)layer);
                layerCourant++;
                if(layerCourant==numberOfSpriteLayer){//if we are in the layer for the Sprite
                    //just draw it up!!!!

                    for(Sprite dropOfKoffee : superStrongKoffee){
                        System.out.println("Fuck OFFF!!!!");
                        dropOfKoffee.draw(this.batch);
                    }
                }
            }else{
                for(MapObject wtfIsThisObject : layer.getObjects()){
                    renderObject(wtfIsThisObject);
                }
            }
        }
        endRender();
    }

    public static void addSomeKoffee(Sprite moreKoffeePlz){
        superStrongKoffee.add(moreKoffeePlz);
    }

    public static void removeSomeKoffee(Sprite pleaseDontDoThat){
        superStrongKoffee.remove(pleaseDontDoThat); //NOOOOOOOOOO
    }
}
