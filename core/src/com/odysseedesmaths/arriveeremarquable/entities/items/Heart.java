package com.odysseedesmaths.arriveeremarquable.entities.items;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;

public class Heart extends Item {

    @Override
    public void trigger() {
        ArriveeGame.get().hero.increasePDV();
        super.trigger();
    }
}
