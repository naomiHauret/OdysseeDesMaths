package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Personnage extends Entite {

    public Personnage(Case c) {
        super(c);
    }

    public void moveTo(Case c) {
        boolean continuer = true;
        if (c.isTaken()) {
            continuer = meet(c.getEntite());
        }
        if (continuer) {
            getCase().free();
            setCase(c);
            c.setEntite(this);
        }
    }

    public abstract boolean meet(Entite e);
}
