package com.odysseedesmaths.minigames.coffeePlumbing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.minigames.coffeePlumbing.map.CoffeeLevel;
import com.odysseedesmaths.minigames.coffeePlumbing.map.Tuyau;

import java.util.Iterator;

//Classe pour les test ...en bazard
public class HardcoreCoffeeTest extends ApplicationAdapter {
    CoffeeLevel level;
    OrthographicCamera camera;
    Viewport viewport;
    int width;
    int height;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth()/2;
        height = Gdx.graphics.getHeight()/2;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(width,height,camera);
        level = new CoffeeLevel("maps/CoffeePlumbing/mapTestNewTextures.tmx");
        Iterator<Tuyau> test = level.get_canalisation().iterator();
        Tuyau tuyauDebug = test.next();
        System.out.print(tuyauDebug.toString());
        Iterator<Tuyau> successor = tuyauDebug.getAllSuccessor().iterator() ;

        while(successor.hasNext()){
            System.out.println(((Tuyau)successor.next()).toString());
        }

        Musique.setCurrent("music/CoffeePlumbing/ambiant.ogg");
        Musique.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level.get_mapRenderer().setView(camera);
        level.get_mapRenderer().render();

        reglerCamera();
        camera.update();
    }

    public void reglerCamera(){
        float minX, minY, maxX, maxY;
        minX=width/2; //la caméra restera toujours, au minimum, au milieu de l'écran (pas d'affichage en dehors de l'écran)
        minY=height/2;
        maxX = level.get_mapWidthPixel() - minX; //et au maximum, elle ne dépassera pas l'affichage de la fin de la map
        maxY = level.get_mapHeightPixel() - minY;
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
