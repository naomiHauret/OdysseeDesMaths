package com.odysseedesmaths.minigames.coffeePlumbing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(width/2,height/2,camera);
        map = new TmxMapLoader().load("maps/CoffeePlumbing/firstMapCoffeePlumbing.tmx");
        mapRenderer=new OrthogonalTiledMapRenderer(map);
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
        camera.position.set(0,0,0);
        camera.update();
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
