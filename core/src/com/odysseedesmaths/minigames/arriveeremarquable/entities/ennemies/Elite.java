package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Elite extends Enemy {

    public Elite(Case c) {
        super(c);
    }

    public Elite() {
        this(null);
    }

    @Override
    public boolean meet(com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Enemy) {
            ArriveeRemarquable.get().destroy((Enemy) e);
            ((Enemy) e).setAlive(false);
            if (e instanceof Elite) {
                ArriveeRemarquable.get().destroy(this);
                setAlive(false);
            }
        }

        return continuer && isAlive();
    }
}
