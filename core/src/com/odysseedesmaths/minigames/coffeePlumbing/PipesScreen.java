package com.odysseedesmaths.minigames.coffeePlumbing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.KoffeeMeter;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.Vanne;
import com.odysseedesmaths.minigames.coffeePlumbing.map.CoffeeLevel;
import com.odysseedesmaths.minigames.coffeePlumbing.map.OrthogonalTiledMapRendererWithKoffee;

import java.util.Iterator;

public class PipesScreen implements Screen {
    private CoffeePlumbing minigame;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int width, height;
    private CoffeeLevel level;
    private Stage stage;
    private OrthogonalTiledMapRendererWithKoffee mapRenderer; //TODO: mettre cette classe en static

    public PipesScreen(final CoffeePlumbing game, String map){
        minigame = game;
        width = Gdx.graphics.getWidth()/2;
        height = Gdx.graphics.getHeight()/2;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(width,height,camera);
        stage = new Stage(viewport);
        level = new CoffeeLevel(map);
        mapRenderer = new OrthogonalTiledMapRendererWithKoffee(level.get_map());

        init();
    }

    public void init(){
        level.buildLevel();
        Iterator<Vanne> itVanne = level.get_vannes().iterator();

        while(itVanne.hasNext()){
            stage.addActor(itVanne.next().get_table());
            System.out.println("Vannes ajoutées"); //debug
        }

        Iterator<KoffeeMeter> itKFM = level.get_indicateurs().iterator();

        while(itKFM.hasNext()){
            stage.addActor(itKFM.next().get_table());
            System.out.println("Koffee Meter ajouté"); //debug
        }
        
        Musique.setCurrent("music/CoffeePlumbing/ambiant.ogg");
        Musique.play();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        stage.act();
        stage.draw();

        reglerCamera();
        camera.update();
    }

    public void reglerCamera(){ //TODO: devra gérer le déplacement de la caméra
        float minX, minY, maxX, maxY;
        minX=width/2; //la caméra restera toujours, au minimum, au milieu de l'écran (pas d'affichage en dehors de l'écran)
        minY=height/2;
        maxX = level.get_mapWidthPixel() - minX; //et au maximum, elle ne dépassera pas l'affichage de la fin de la map
        maxY = level.get_mapHeightPixel() - minY;
        camera.position.set(Math.max(minX,maxX),Math.max(minY,maxY),0);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        mapRenderer.dispose();
    }
}
