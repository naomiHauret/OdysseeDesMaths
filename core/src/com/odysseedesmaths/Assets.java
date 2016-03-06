package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import net.dermetfan.gdx.assets.AnnotationAssetManager;
import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public class Assets {

    private Assets() {}

    private static final AnnotationAssetManager manager = new AnnotationAssetManager();

    public static AnnotationAssetManager getManager() {
        return manager;
    }

    @Asset(Texture.class)
    public static final String
            HERO = "heros.png",
            HEART = "coeur.png",
            MAIN_MENU_BACKGROUND = "tower.png";


    @Asset(TextureAtlas.class)
    public static final String
            UI_MAIN = "ui/main.atlas",
            UI_ORANGE = "ui/orange.atlas";


    @Asset(Music.class)
    public static final String
            ARCADE = "music/Arcade_Machine.ogg",
            MENU_MUSIC = "music/Opening.ogg",
            GAME_OVER_MUSIC = "music/Game Over.ogg";


    public static final FileHandle
            PRESS_START_2P = Gdx.files.internal("fonts/PressStart2P.ttf"),
            KENPIXEL_BLOCKS = Gdx.files.internal("fonts/kenpixel_blocks.ttf");


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