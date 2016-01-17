package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entite;

public class Div extends Signe {

    @Override
    public void act() {

    }

    @Override
    public boolean meet(Entite e) {
        boolean alive = super.meet(e);

        if (e instanceof Mult) {
            ArriveeGame.get().destroy(this);
            ArriveeGame.get().destroy((Signe) e);
            alive = false;
        }

        return alive;
    }
}
