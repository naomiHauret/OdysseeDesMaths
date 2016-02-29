package com.odysseedesmaths;

import com.badlogic.gdx.Preferences;

public class Save {

    private Preferences save;

    private String name;
    private boolean level1Finished;     // Arrivée remarquable
    private boolean level2Finished;     // Plongée au coeur du problème
    private boolean level3Finished;     // Accrobranche
    private boolean level4Finished;     // CoffeePlumbing

    public Save(Preferences save) {
        this.save = save;
        if (!save.contains("name")) init();
        load();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        save.putString("name", name);
        save.flush();
    }

    public void setLevel1Finished(boolean level1Finished) {
        this.level1Finished = level1Finished;
        save.putBoolean("level1Finished", level1Finished);
        save.flush();
    }

    public void setLevel2Finished(boolean level2Finished) {
        this.level2Finished = level2Finished;
        save.putBoolean("level2Finished", level2Finished);
        save.flush();
    }

    public void setLevel3Finished(boolean level3Finished) {
        this.level3Finished = level3Finished;
        save.putBoolean("level3Finished", level3Finished);
        save.flush();
    }

    public void setLevel4Finished(boolean level4Finished) {
        this.level4Finished = level4Finished;
        save.putBoolean("level4Finished", level4Finished);
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
        if (level4Finished) completion++;
        completion = (completion / 4) * 100;

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
        save.putBoolean("level4Finished", false);
        save.flush();
    }

    private void load() {
        name = save.getString("name");
        level1Finished = save.getBoolean("level1Finished");
        level2Finished = save.getBoolean("level2Finished");
        level3Finished = save.getBoolean("level3Finished");
        level4Finished = save.getBoolean("level4Finished");
    }
}
