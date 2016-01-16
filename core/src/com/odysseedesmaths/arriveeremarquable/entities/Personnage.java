package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Personnage extends Entite {

    public Personnage(Case c) {
        super(c);
    }

    public void moveTo(Case c) {
        if (c.isTaken()) {
            meet(c.getEntite());
        }
        getCase().free();
        setCase(c);
        c.setEntite(this);
    }

    public abstract void meet(Entite e);
}
