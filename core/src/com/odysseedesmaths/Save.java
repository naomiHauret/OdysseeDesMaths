package com.odysseedesmaths;

import com.badlogic.gdx.Preferences;
/*
        Classe référence de sauvegarde
 */

public class Save {

    private Preferences save;

    private String name;
    private boolean level1Finished;     // Arrivée remarquable
    private boolean level2Finished;     // Accrobranche
    private boolean level3Finished;     // CoffeePlumbing

    public Save(Preferences save) {
        this.save = save;
        if (!save.contains("name")) init();
        load();
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
        save.putString("name", name);
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
        save.putBoolean("level1Finished", false);
        save.putBoolean("level2Finished", false);
        save.putBoolean("level3Finished", false);
        save.flush();
    }

    private void load() {
        name = save.getString("name");
        level1Finished = save.getBoolean("level1Finished");
        level2Finished = save.getBoolean("level2Finished");
        level3Finished = save.getBoolean("level3Finished");
    }
}
