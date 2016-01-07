package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Personnage extends Entite {

    public Personnage(Case c) {
        super(c);
    }

    public void move(Case c) {

    }

    public abstract void meet(Entite e);
}
