package com.odysseedesmaths.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Elite extends Enemy {

    public Elite(Case c) {
        super(c);
    }

    public Elite() {
        this(null);
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Enemy) {
            ArriveeGame.get().destroy((Enemy) e);
            ((Enemy) e).setAlive(false);
            if (e instanceof Elite) {
                ArriveeGame.get().destroy(this);
                setAlive(false);
            }
        }

        return continuer && isAlive();
    }
}
