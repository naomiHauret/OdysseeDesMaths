package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Personnage extends Entite {

    public Personnage(Case c) {
        super(c);
    }

    public void moveTo(Case c) {
        boolean alive = true;
        if (c.isTaken()) {
            alive = meet(c.getEntite());
        }
        if (alive) {
            getCase().free();
            setCase(c);
            c.setEntite(this);
        }
    }

    public abstract boolean meet(Entite e);
}
