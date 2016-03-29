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

    /*
    mise en place du premier espace de sauvegarde

      @return sauvegarge
    */
    public Save getSave1() {
        return save1;
    }

    /*
    mise en place du deuxième espace de sauvegarde

      @return sauvegarge
    */
    public Save getSave2() {
        return save2;
    }

    /*
      mise en place du troisième espace de sauvegarde

      @return sauvegarge
    */
    public Save getSave3() {
        return save3;
    }

    /*
      récupère la sauvegarde sélectionnée

      @return sauvegarde
    */
    public Save getCurrentSave() {
        return currentSave;
    }

    /*
      met en place la sauvegarde dans l'espace prédéfinit à la sélection
    */
    public void setCurrentSave(Save save) {
        currentSave = save;
    }
}
