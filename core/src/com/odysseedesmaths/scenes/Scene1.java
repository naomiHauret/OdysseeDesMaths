package com.odysseedesmaths.scenes;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.dialogs.EndButtonsListener;
import com.odysseedesmaths.dialogs.SimpleDialog;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class Scene1 extends Scene {

    Texture background;

    public Scene1 () {
        background = Assets.getManager().get(Assets.S01_PAYSAGE, Texture.class);
    }

    @Override
    public Texture getBackground() {
        return background;
    }

    @Override
    public void aventure() {
        final OdysseeDesMaths gameReference = getMss().getJeu();
        getMss().getJeu().setScreen(new SimpleDialog(getMss().getJeu(), Assets.DLG_ARRIVEE_1, new EndButtonsListener() {
            @Override
            public void buttonPressed(String buttonName) {
                switch (buttonName) {
                    case "continue":
                        background = Assets.getManager().get(Assets.S01_FUITE, Texture.class);
                        getMss().updateBackground();
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                background = Assets.getManager().get(Assets.S01_PAYSAGE, Texture.class);
                                gameReference.setScreen(new ArriveeRemarquable(gameReference));
                            }
                        }, 3f);
                        break;
                }
            }
        }));
    }
}