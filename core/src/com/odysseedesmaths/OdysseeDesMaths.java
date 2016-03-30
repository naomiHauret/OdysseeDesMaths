package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odysseedesmaths.menus.GameChoiceMenu;
import com.odysseedesmaths.menus.NewSave;

/*
        Classe du jeu principal
 */

public class OdysseeDesMaths extends Game {
    private static Settings settings;

    public SpriteBatch batcher;

    private ModeSceneScreen modeScene = null;
    private SavesManager savesManager;

    public static Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    @Override
    public void create() {
        Assets.getManager().load(Assets.class);
        Assets.getManager().finishLoading();

        Musique.setVolume(getSettings().isMusicMuted() ? 0 : 100);

        batcher = new SpriteBatch();
        setScreen(new GameChoiceMenu(this));
    }

    public ModeSceneScreen getModeScene() {
        if (modeScene == null) {
            this.modeScene = new ModeSceneScreen(this);
        }
        modeScene.returnToScene(); // Pour ne pas retourner sur le menu
        return modeScene;
    }

    public SavesManager getSavesManager() {
        if (savesManager == null) {
            this.savesManager = new SavesManager();
        }
        return savesManager;
    }

    /* Ces méthodes permettent d'éviter d'instancier plusieurs fois des écrans
    toujours identiques.
    On peut faire de même pour chaque jeu, où vérifier qu'ils sont détruits lorsqu'on les
    quitte.
     */

    public void startGame() {
        if (savesManager.getCurrentSave().isEmpty()) {
            setScreen(new NewSave(this));
        } else {
            setScreen(getModeScene());
        }
    }
}