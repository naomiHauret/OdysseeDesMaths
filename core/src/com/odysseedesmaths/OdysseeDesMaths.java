package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odysseedesmaths.dialogs.EndButtonsListener;
import com.odysseedesmaths.dialogs.SimpleDialog;
import com.odysseedesmaths.menus.MenuPrincipal;
import com.odysseedesmaths.menus.NewSave;
import com.odysseedesmaths.minigames.accrobranche.Accrobranche;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

/*
        Classe du jeu principal
 */

public class OdysseeDesMaths extends Game {
    public SpriteBatch batcher;

    private ModeSceneScreen modeScene = null;
    private SavesManager savesManager;

    @Override
    public void create() {
        Assets.getManager().load(Assets.class);
        Assets.getManager().finishLoading();

        batcher = new SpriteBatch();
        setScreen(new MenuPrincipal(this));
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
        } else if (savesManager.getCurrentSave().isLevel3Finished()) {
            setScreen(new ArriveeRemarquable(this));
        } else if (savesManager.getCurrentSave().isLevel2Finished()) {
            setScreen(new ArriveeRemarquable(this));
        } else if (savesManager.getCurrentSave().isLevel1Finished()) {
            setScreen(new Accrobranche(this));
        } else {
            final OdysseeDesMaths gameReference = this; // patchwork un peu degueu pour le passage de référence ci-dessous
            setScreen(new SimpleDialog(this, Assets.DLG_ARRIVEE1, new EndButtonsListener() {
                @Override
                public void buttonPressed(String buttonName) {
                    switch (buttonName) {
                        case "continue":
                            gameReference.setScreen(new ArriveeRemarquable(gameReference));
                            break;
                    }
                }
            }));
        }
    }
}