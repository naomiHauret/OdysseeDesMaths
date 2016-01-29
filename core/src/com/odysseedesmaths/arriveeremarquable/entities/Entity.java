package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Entity {
    private Case maCase;

    public Entity(Case c) {
        this.maCase = c;
    }

    public Entity() {
        this(null);
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
}