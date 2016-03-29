package com.odysseedesmaths;

import com.badlogic.gdx.Preferences;
/*
        Classe référence de sauvegarde
 */

public class Save {

    private Preferences save;

    private String name;
    private boolean prologueFinished;   // Classe
    private boolean level1Finished;     // Arrivée remarquable
    private boolean level2Finished;     // Accrobranche
    private boolean level3Finished;     // CoffeePlumbing

    public Save(Preferences save) {
        this.save = save;
        if (!save.contains("name")) init();
        load();
    }

    /*
      récupère la sauvegarde avec le nom du joueur

      @return le nom de la sauvegarde (nom du joueur)
    */
    public String getName() {
        return name;
    }

<<<<<<< HEAD
    /*
      mise en place du nom de la sauvegarde avec le nom du joueur
    */
=======
    public boolean isPrologueFinished() {
        return prologueFinished;
    }

    public boolean isLevel1Finished() {
        return level1Finished;
    }

    public boolean isLevel2Finished() {
        return level2Finished;
    }

    public boolean isLevel3Finished() {
        return level3Finished;
    }

>>>>>>> 45fb5196725771ea23c7f546bea79d9ee5310b01
    public void setName(String name) {
        this.name = name;
        save.putString("name", name);
        save.flush();
    }

<<<<<<< HEAD
    /*
      mise en place de sauvegardes automatiques
    */
    public void setLevel1Finished(boolean level1Finished) {
        this.level1Finished = level1Finished;
        save.putBoolean("level1Finished", level1Finished);
=======
    public void setPrologueFinished(boolean finished) {
        this.prologueFinished = finished;
        save.putBoolean("prologueFinished", finished);
>>>>>>> 45fb5196725771ea23c7f546bea79d9ee5310b01
        save.flush();
    }

    public void setLevel1Finished(boolean finished) {
        this.level1Finished = finished;
        save.putBoolean("level1Finished", finished);
        save.flush();
    }

    public void setLevel2Finished(boolean finished) {
        this.level2Finished = finished;
        save.putBoolean("level2Finished", finished);
        save.flush();
    }

    public void setLevel3Finished(boolean finished) {
        this.level3Finished = finished;
        save.putBoolean("level3Finished", finished);
        save.flush();
    }

    public boolean isEmpty() {
        return name.equals("");
    }

    public int getCompletion() {
        double completion = 0.0;

        if (level1Finished) completion++;
        if (level2Finished) completion++;
        if (level3Finished) completion++;
        completion = (completion / 3) * 100;

        return (int)completion;
    }

    public void reset() {
        init();
        load();
    }

    private void init() {
        save.putString("name", "");
        save.putBoolean("prologueFinished", false);
        save.putBoolean("level1Finished", false);
        save.putBoolean("level2Finished", false);
        save.putBoolean("level3Finished", false);
        save.flush();
    }

    /*
      lance la sauvegarde sélectionnée selon l'avancement enregistré
    */
    private void load() {
        name = save.getString("name");
        prologueFinished = save.getBoolean("prologueFinished");
        level1Finished = save.getBoolean("level1Finished");
        level2Finished = save.getBoolean("level2Finished");
        level3Finished = save.getBoolean("level3Finished");
    }
}
