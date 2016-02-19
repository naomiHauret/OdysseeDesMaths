package com.odysseedesmaths.minigames.coffeePlumbing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by trilunaire on 16/02/16.
 */
public class HardcoreCoffeeTest extends ApplicationAdapter{
    //TODO: Faire en sorte que la fucking canalisation affiché en plus grand
    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    Viewport viewport;
    int mapWidth,mapHeight,width,height;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(width/2,height/2,camera);
        map = new TmxMapLoader().load("maps/CoffeePlumbing/firstMapCoffeePlumbing.tmx");
        mapRenderer=new OrthogonalTiledMapRenderer(map);
        // Récupération de ses dimensions
        mapWidth=((Integer)map.getProperties().get("width")).intValue();
        mapHeight=((Integer)map.getProperties().get("height")).intValue();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        reglerCamera();
        camera.update();
    }

    public void reglerCamera(){
        float posX, posY, minX, minY, maxX, maxY;
        minX=width/2; //la caméra restera toujours, au minimum, au milieu de l'écran (pas d'affichage en dehors de l'écran)
        minY=height/2;
        maxX = mapWidth * 64 - minX; //et au maximum, elle ne dépassera pas l'affichage de la fin de la map
        maxY = mapHeight * 64 - minY;
        camera.position.set(Math.max(minX,maxX),Math.max(minY,maxY),0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
