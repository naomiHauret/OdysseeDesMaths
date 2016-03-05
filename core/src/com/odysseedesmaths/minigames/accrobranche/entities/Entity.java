package com.odysseedesmaths.minigames.accrobranche.entities;

import com.odysseedesmaths.minigames.accrobranche.Accrobranche;

public abstract class Entity {
    private Accrobranche minigame;

    public Entity(Accrobranche minigame) {
        this.minigame = minigame;
    }

    public Accrobranche getMinigame() {
        return minigame;
    }
}