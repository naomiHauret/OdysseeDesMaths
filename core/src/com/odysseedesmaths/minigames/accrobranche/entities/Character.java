package com.odysseedesmaths.minigames.accrobranche.entities;

import com.odysseedesmaths.minigames.accrobranche.Accrobranche;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Character extends Entity {

    public Character(Accrobranche minigame, Case c) {
        super(minigame);
    }

    public Character(Accrobranche minigame) {
        super(minigame);
    }

    public void moveTo() {
        boolean continuer = true;
    }

    public abstract boolean meet(Entity e);
}
