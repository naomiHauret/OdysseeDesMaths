package com.odysseedesmaths.scenes;


import com.badlogic.gdx.graphics.Texture;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.dialogs.EndButtonsListener;
import com.odysseedesmaths.dialogs.SimpleDialog;
import com.odysseedesmaths.minigames.accrobranche.Accrobranche;

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
                final OdysseeDesMaths game = getMss().getJeu();
                game.setScreen(new SimpleDialog(game, Assets.DLG_ARRIVEE_2a, new EndButtonsListener() {
                    @Override
                    public void buttonPressed(String buttonName) {
                        game.setScreen(new SimpleDialog(game, Assets.EXP_ARRIVEE_1, new EndButtonsListener() {
                            @Override
                            public void buttonPressed(String buttonName) {
                                if (buttonName.equals("continue")) {
                                    //TODO: Lancenement questionnaire 1
                                    game.setScreen(new SimpleDialog(game, Assets.DLG_ARRIVEE_2b, new EndButtonsListener() {
                                        @Override
                                        public void buttonPressed(String buttonName) {
                                            game.setScreen(new Accrobranche(game));
                                        }
                                    }));
                                } else if (buttonName.equals("next_level")) {
                                    //TODO: Lancement explications 2
                                }
                            }
                        }));
                    }
                }));
        }
        state = (++state)%3;
    }
}
