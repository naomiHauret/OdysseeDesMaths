package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Character extends Entity {

    public Character(Case c) {
        super(c);
    }

    public void moveTo(Case c) {
        boolean continuer = true;
        if (c.isTaken()) {
            continuer = meet(c.getEntity());
        }
        if (continuer) {
            setCase(c);
        }
    }

    public abstract boolean meet(Entity e);
}
