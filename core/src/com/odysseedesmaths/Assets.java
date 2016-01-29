package com.odysseedesmaths;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import net.dermetfan.gdx.assets.AnnotationAssetManager;
import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public class Assets {

    private static final AnnotationAssetManager manager = new AnnotationAssetManager();

    public static AnnotationAssetManager getManager() {
        return manager;
    }

    @Asset(Texture.class)
    public static final String
            HERO = "heros.png",
            HEART = "coeur.png",
            ARR_HORDE = "horde.png",
            ARR_S_EGAL = "signeEgal.png",
            ARR_S_ADD = "signeAdd.png",
            ARR_S_SOUST = "signeSoust.png",
            ARR_S_MULT = "signeMult.png",
            ARR_S_DIV = "signeDiv.png",
            ARR_BUFF_SHIELD = "bouclier.png",
            ARR_SHIELD = "itemBouclier.png";


    @Asset(TextureAtlas.class)
    public static final String
            UI_ATLAS = "ui/ui.atlas";


    //TODO: utiliser Assets pour les BitmapFonts
    //@Asset(BitmapFont.class)
    //public static final String UI_TIMER_FNT;


    @Asset(Music.class)
    public static final String
            ARCADE = "Arcade_Machine.ogg";


    static {
        manager.load(Assets.class);
        manager.finishLoading();
    }
}