package com.odysseedesmaths.arriveeremarquable.entities.enemies;

import com.odysseedesmaths.arriveeremarquable.entities.Entite;
import com.odysseedesmaths.arriveeremarquable.entities.Personnage;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Signe extends Personnage {

    public Signe(Case c) {
        super(c);
    }

    @Override
    public void meet(Entite e) {
    }

    public abstract void move();
}
