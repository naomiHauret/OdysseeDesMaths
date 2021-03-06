package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import net.dermetfan.gdx.assets.AnnotationAssetManager;
import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

/*
    Classe récupérant tous les assets nécessaires durant le jeu
*/

public class Assets {

    private Assets() {}

    private static final AnnotationAssetManager manager = new AnnotationAssetManager();

    public static AnnotationAssetManager getManager() {
        return manager;
    }

    // Raccourcis pratiques
    private static final String
            ICONS_PATH = "textures/common/char_icons/",
            DLG_PATH = "texts/dialogs/",
            EXP_PATH = "texts/explanations/",
            QST_PATH = "texts/questionnaires/";

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
            ICON_VIKTOR = ICONS_PATH+"viktor.png",
            ARRIVEE_REMARAQUABLE_ARCADE_MACHINE = "textures/common/arcadeMachines/arriveRemarquableArcadeMachine.png",
            ACCROBRANCHE_ARCADE_MACHINE = "textures/common/arcadeMachines/accrobrancheArcadeMachine.png",
            COFFEE_PLUMBING_ARCADE_MACHINE = "textures/common/arcadeMachines/CoffeePlumbingArcadeMachine.png",
            ARCADE_ROOM_BACKGROUND = "textures/common/arcadeMachines/background.png";


    @Asset(TextureAtlas.class)
    public static final String
            UI_MAIN = "ui/main.atlas",
            UI_ORANGE = "ui/orange.atlas",
            UI_SCROLL = "ui/scroll.atlas",
	        KOFFEE = "textures/CoffeePlumbing/koffee.atlas";


    @Asset(Music.class)
    public static final String
            ARCADE = "music/Arcade_Machine.ogg",
            MENU_MUSIC = "music/Opening.ogg",
            GAME_OVER_MUSIC = "music/Game Over.ogg",
            GAGNER_MUSIC = "music/Victory.ogg";


    public static final FileHandle
            PRESS_START_2P = Gdx.files.internal("fonts/PressStart2P.ttf"),
            KENPIXEL_BLOCKS = Gdx.files.internal("fonts/kenpixel_blocks.ttf");

    // Dialogues
    public static final String
            DLG_ARRIVEE_1 = DLG_PATH + "dialogue01.xml",
            DLG_ARRIVEE_2a = DLG_PATH + "dialogue02a.xml",
            DLG_ARRIVEE_2b = DLG_PATH + "dialogue02b.xml";

    // Explications
    public static final String
            EXP_ARRIVEE_1 = EXP_PATH + "arrivee1.xml";

    // Questionnaires
    public static final String
            QST_ARRIVEE1 = QST_PATH + "arrivee1.xml";

    // Scènes
    @Asset(Texture.class)
    public static final String
            S00_CLASSE = "scenes/classe.png",
            S00_TABLEAU = "scenes/tableau.png",
            S00_TABLEAU_PROF = "scenes/tableauProf.png",
            S00_ELEVE = "scenes/eleveDormir.png",
            S01_PAYSAGE = "scenes/paysage.png",
            S01_FUITE = "scenes/fuite.png",
            S02_CHUTE = "scenes/trou2.png",
            S02_CAVERNE = "scenes/caverne.png";


    /**********************
     * ASSETS SPECIFIQUES *
     **********************/

    // Arrivée remarquable
    private static final String ARR_PATH = "arrivee_remarquable/";

    @Asset(TextureAtlas.class)
    public static final String
            ARR_ATLAS = ARR_PATH + "arrivee.atlas";

    @Asset(Texture.class)
    public static final String
            ARR_DLGIMG_G1 = ARR_PATH + "dialogs_images/g1.png",
            ARR_DLGIMG_G2 = ARR_PATH + "dialogs_images/g2.png",
            ARR_DLGIMG_G3 = ARR_PATH + "dialogs_images/g3.png",
            ARR_DLGIMG_G4 = ARR_PATH + "dialogs_images/g4.png",
            ARR_DLGIMG_G5 = ARR_PATH + "dialogs_images/g5.png",
            ARR_DLGIMG_G6 = ARR_PATH + "dialogs_images/g6.png",
            ARR_DLGIMG_G7 = ARR_PATH + "dialogs_images/g7.png";

    // Plongée au coeur du problème
    // Accrobranche
    // Coffee Plumbing
    // Mauvais tournant
    // Tower's destruction
}
