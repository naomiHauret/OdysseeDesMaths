package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import net.dermetfan.gdx.assets.AnnotationAssetManager;
import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public class Assets {

    private Assets() {}

    private static final AnnotationAssetManager manager = new AnnotationAssetManager();

    public static AnnotationAssetManager getManager() {
        return manager;
    }

    public static final TextureAtlas UI_GRAPHISM;
    public static final TextureRegion menuPause;

    @Asset(Texture.class)
    public static final String
            HERO = "heros.png",
            HEART = "coeur.png",
            MAINMENUBACKGROUND = "tower.png";

    @Asset(TextureAtlas.class)
    public static final String
            UI = "ui/ui.atlas",
            UI_TEST = "test/uiskin.atlas";

    @Asset(Music.class)
    public static final String
            ARCADE = "Arcade_Machine.ogg",
            MENUMUSIC = "music/Opening.ogg";

    public static final BitmapFont PIXEL;
    public static final BitmapFont GAME_OVER;
    public static final BitmapFont TIMER;
    public static final BitmapFont ITEM_COUNTER;

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel-life.TTF"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = Gdx.graphics.getHeight() / 10;
        PIXEL = generator.generateFont(parameter);
        TIMER = generator.generateFont(parameter);
        ITEM_COUNTER = generator.generateFont(parameter);

        parameter.size = Gdx.graphics.getHeight() / 5;
        GAME_OVER = generator.generateFont(parameter);
        generator.dispose();

        UI_GRAPHISM = new TextureAtlas(Gdx.files.internal("test/uiskin.atlas"));
        menuPause = UI_GRAPHISM.findRegion("default");
    }


    /**********************
     * ASSETS SPECIFIQUES *
     **********************/

    // Arrivée remarquable
    @Asset(Texture.class)
    public static final String
            ARR_HORDE = "horde.png",
            ARR_S_EGAL = "signeEgal.png",
            ARR_S_ADD = "signeAdd.png",
            ARR_S_SOUST = "signeSoust.png",
            ARR_S_MULT = "signeMult.png",
            ARR_S_DIV = "signeDiv.png",
            ARR_BUFF_SHIELD = "bouclier.png",
            ARR_SHIELD = "itemBouclier.png";

    // Plongée au coeur du problème
    // Accrobranche
    // Coffee Plumbing
    // Mauvais tournant
    // Tower's destruction
}