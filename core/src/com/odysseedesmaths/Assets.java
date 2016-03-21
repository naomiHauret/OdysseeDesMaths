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

    // Raccourcis pratiques
    private static final String
            ICONS_PATH = "textures/common/char_icons/";

    @Asset(Texture.class)
    public static final String
            HERO = "textures/common/heros.png",
            HEART = "textures/common/heart.png",
            HEART_EMPTY = "textures/common/heart_empty.png",
            VANNEBUTTON = "textures/CoffeePlumbing/vanneButton.png",
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
            UI_ORANGE = "ui/orange.atlas",
            UI_SCROLL = "ui/scroll.atlas";


    @Asset(Music.class)
    public static final String
            ARCADE = "music/Arcade_Machine.ogg",
            MENU_MUSIC = "music/Opening.ogg",
            GAME_OVER_MUSIC = "music/Game Over.ogg",
            // GAGNER_MUSIC = "music/Game Over.ogg"; // A ajouter après avoir trouvé la musique


    public static final FileHandle
            PRESS_START_2P = Gdx.files.internal("fonts/PressStart2P.ttf"),
            KENPIXEL_BLOCKS = Gdx.files.internal("fonts/kenpixel_blocks.ttf");


    /**********************
     * ASSETS SPECIFIQUES *
     **********************/

    // Arrivée remarquable
    @Asset(TextureAtlas.class)
    public static final String
            ARRIVEE = "arrivee_remarquable/arrivee.atlas";

    // Plongée au coeur du problème
    // Accrobranche
    // Coffee Plumbing
    // Mauvais tournant
    // Tower's destruction
}
