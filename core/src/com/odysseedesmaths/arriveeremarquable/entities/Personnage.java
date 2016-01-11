package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Personnage extends Entite {

    public Personnage(Case c) {
        super(c);
    }

    public void moveTo(Case c) {
        if (c.getEntite() != null) {
            meet(c.getEntite());
        }
        getCase().setEntite(null);
        c.setEntite(this);
    }

    public abstract void meet(Entite e);
}
