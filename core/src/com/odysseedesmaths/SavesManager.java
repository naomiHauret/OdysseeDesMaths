package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
/*
        Classe d'interface système des espaces sauvegardes
 */

public class SavesManager {

    private Save save1;
    private Save save2;
    private Save save3;
    private Save currentSave;

    public SavesManager() {
        save1 = new Save(Gdx.app.getPreferences("save1"));
        save2 = new Save(Gdx.app.getPreferences("save2"));
        save3 = new Save(Gdx.app.getPreferences("save3"));
    }

    public Save getSave1() {
        return save1;
    }

    public Save getSave2() {
        return save2;
    }

    public Save getSave3() {
        return save3;
    }

    public Save getCurrentSave() {
        return currentSave;
    }

    public void setCurrentSave(Save save) {
        currentSave = save;
    }
}
