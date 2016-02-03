package com.odysseedesmaths.minigames.arriveeremarquable.entities;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Character extends Entity {

    public Character(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Character(ArriveeRemarquable minigame) {
        super(minigame);
    }

    public void moveTo(Case c) {
        boolean continuer = true;
        if (c.isTaken()) {
            continuer = meet(c.getEntity());
        }
        if (continuer) {
            setCase(c);
            c.setEntity(this);
        }
    }

    public abstract boolean meet(Entity e);
}
