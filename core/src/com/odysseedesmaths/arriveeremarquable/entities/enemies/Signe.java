package com.odysseedesmaths.arriveeremarquable.entities.enemies;

import com.odysseedesmaths.arriveeremarquable.map.Case;

/**
 * Created by Allan on 03/01/2016.
 */
public abstract class Signe extends com.odysseedesmaths.arriveeremarquable.entities.Personnage {

    public Signe(Case c) {
        super(c);
    }

    @Override
    public void meet(com.odysseedesmaths.arriveeremarquable.entities.Entite e) {

    }

    public abstract void move();
}
