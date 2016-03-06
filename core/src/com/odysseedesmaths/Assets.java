package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    // Raccourcis pratiques
    private static final String
            ICONS_PATH = "textures/common/char_icons/";

    @Asset(Texture.class)
    public static final String
            HERO = "heros.png",
            HEART = "coeur.png",
            MAIN_MENU_BACKGROUND = "tower.png",
            // Icones des personnages
            ICON_HERO = ICONS_PATH+"hero.png",
            ICON_PYLES = ICONS_PATH+"pyles.png",
            ICON_AUDIB = ICONS_PATH+"audib.png",
            ICON_GWENDOUILLE = ICONS_PATH+"gwendouille.png",
            ICON_DIJKSTRA = ICONS_PATH+"dijkstra.png",
            ICON_EDDYMALOU = ICONS_PATH+"eddymalou.png",
            ICON_MARKOV = ICONS_PATH+"markov.png",
            ICON_NICHOLAS = ICONS_PATH+"nicholasSaunderson.png",
            ICON_PYTHAGORE = ICONS_PATH+"pythagore.png",
            ICON_ROBERT = ICONS_PATH+"robertSmithn.png",
            ICON_TARTAGLIA = ICONS_PATH+"tartaglia.png",
            ICON_THALES = ICONS_PATH+"thales.png",
            ICON_TIFOUILLE = ICONS_PATH+"tifouille.png",
            ICON_VIKTOR = ICONS_PATH+"viktor.png";

    @Asset(TextureAtlas.class)
    public static final String
            UI_MAIN = "ui/main.atlas",
            UI_RED = "ui/red.atlas",
            UI_GREY = "ui/grey.atlas",
            UI_SCROLL = "ui/scroll.atlas";


    @Asset(Music.class)
    public static final String
            ARCADE = "music/Arcade_Machine.ogg",
            MENU_MUSIC = "music/Opening.ogg",
            GAME_OVER_MUSIC = "music/Game Over.ogg";


    // Ces polices sont chargées à l'aide d'un FreeTypeFontGenerator
    public static final BitmapFont
            BUTTON,
            GAME_OVER,
            TIMER,
            ITEM_COUNTER;

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        FreeTypeFontParameter parameter;

        parameter = new FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight() / 20;
        parameter.color = Color.WHITE;
        TIMER = generator.generateFont(parameter);
        ITEM_COUNTER = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight() / 9;
        parameter.color = Color.RED;
        parameter.borderWidth = 5;
        parameter.borderColor = Color.WHITE;
        GAME_OVER = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight() / 22;
        parameter.color = Color.WHITE;
        BUTTON = generator.generateFont(parameter);

        generator.dispose();
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