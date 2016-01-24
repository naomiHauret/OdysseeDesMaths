package com.odysseedesmaths.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entity;

public class SuperSmart extends Enemy {

    @Override
    public void act() {

    }

    @Override
    public boolean meet(Entity e) {
        boolean alive = super.meet(e);

        if (e instanceof Smart) {
            ArriveeGame.get().destroy(this);
            ArriveeGame.get().destroy((Enemy) e);
            alive = false;
        }

        return alive;
    }
}
