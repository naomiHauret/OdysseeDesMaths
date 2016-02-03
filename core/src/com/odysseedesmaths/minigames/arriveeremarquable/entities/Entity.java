package com.odysseedesmaths.minigames.arriveeremarquable.entities;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Entity {
    private ArriveeRemarquable minigame;
    private Case maCase;

    public Entity(ArriveeRemarquable minigame, Case c) {
        this.minigame = minigame;
        this.maCase = c;
    }

    public Entity(ArriveeRemarquable minigame) {
        this(minigame, null);
    }

    public Case getCase() {
        return maCase;
    }

    public void setCase(Case c) {
        if (getCase() != null) {
            getCase().free();
        }
        maCase = c;
    }

    public ArriveeRemarquable getMinigame() {
        return minigame;
    }
}