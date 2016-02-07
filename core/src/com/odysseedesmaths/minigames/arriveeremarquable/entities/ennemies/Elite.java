package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Elite extends Enemy {

    public Elite(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Elite(ArriveeRemarquable minigame) {
        super(minigame);
    }

    @Override
    public boolean meet(com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Enemy) {
            getMinigame().destroy((Enemy) e);
            ((Enemy) e).setAlive(false);
            if (e instanceof Elite) {
                getMinigame().destroy(this);
                setAlive(false);
            }
        }

        return continuer && isAlive();
    }
}
