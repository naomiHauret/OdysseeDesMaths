package com.odysseedesmaths.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.odysseedesmaths.Assets;

public class Scene0 extends Scene {

    private int state;
    private Texture background;

    public Scene0 () {
        state = 0;
        background = Assets.getManager().get(Assets.S00_CLASSE, Texture.class);
        // Je ne me sers pas de la classe Assets tant qu'elle n'est pas rangée ;)
    }

    @Override
    public Texture getBackground() {
        return background;
    }

    @Override
    public void aventure() {
        switch (state) {
            case 0:
                background = Assets.getManager().get(Assets.S00_TABLEAU, Texture.class);
                getMss().updateBackground();
                break;
            case 1:
                background = Assets.getManager().get(Assets.S00_TABLEAU_PROF, Texture.class);
                getMss().updateBackground();
                break;
            case 2:
                background = Assets.getManager().get(Assets.S00_ELEVE, Texture.class);
                getMss().updateBackground();
                break;
            case 3:
                background = Assets.getManager().get(Assets.S00_CLASSE, Texture.class);
                getMss().getJeu().getSavesManager().getCurrentSave().setPrologueFinished(true);
                getMss().switchScene(getMss().getScene1());
                break;
        }
        state = (++state)%4;
    }
}
