package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odysseedesmaths.menus.MenuPrincipal;
import com.odysseedesmaths.menus.NewSave;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class OdysseeDesMaths extends Game {
    public SpriteBatch batcher;

    private MenuPrincipal menuPrincipal = null;
    private ModeSceneScreen modeScene = null;

    private SavesManager savesManager;

    @Override
    public void create() {
        Assets.getManager().load(Assets.class);
        Assets.getManager().finishLoading();

        batcher = new SpriteBatch();
        setScreen(getMenuPrincipal());
    }

    public MenuPrincipal getMenuPrincipal() {
        if (menuPrincipal == null) {
            this.menuPrincipal = new MenuPrincipal(this);
        }
        return menuPrincipal;
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
            setScreen(new ArriveeRemarquable(this));
        }
    }
}