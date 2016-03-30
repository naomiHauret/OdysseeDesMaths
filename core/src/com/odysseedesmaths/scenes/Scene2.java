package com.odysseedesmaths.scenes;


import com.badlogic.gdx.graphics.Texture;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.dialogs.EndButtonsListener;
import com.odysseedesmaths.dialogs.SimpleDialog;
import com.odysseedesmaths.minigames.accrobranche.Accrobranche;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class Scene2 extends Scene {

    private int state;
    Texture background;

    public Scene2 () {
        state = 0;
        background = Assets.getManager().get(Assets.S01_FUITE, Texture.class);
    }

    @Override
    public Texture getBackground() {
        return background;
    }

    @Override
    public void aventure() {
        switch (state) {
            case 0:
                background = Assets.getManager().get(Assets.S02_CHUTE, Texture.class);
                getMss().updateBackground();
                break;
            case 1:
                background = Assets.getManager().get(Assets.S02_CAVERNE, Texture.class);
                getMss().updateBackground();
                break;
            case 2:
                background = Assets.getManager().get(Assets.S01_FUITE, Texture.class);
                final OdysseeDesMaths gameReference = getMss().getJeu();
                getMss().getJeu().setScreen(new SimpleDialog(getMss().getJeu(), Assets.DLG_ARRIVEE_2, new EndButtonsListener() {
                    @Override
                    public void buttonPressed(String buttonName) {
                        switch (buttonName) {
                            case "continue":
                                gameReference.setScreen(new Accrobranche(gameReference));
                                break;
                        }
                    }
                }));
                break;
        }
        state = (++state)%3;
    }
}
