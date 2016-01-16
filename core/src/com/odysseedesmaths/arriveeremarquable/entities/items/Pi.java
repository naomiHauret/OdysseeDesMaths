package com.odysseedesmaths.arriveeremarquable.entities.items;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public class Pi extends Item {

    private static final int DUREE = 7;

    public Pi(Case c) {
        super(c);
    }

    @Override
    public void trigger() {
        super.trigger();
        ArriveeGame.get().activeItems.put("pi", DUREE);
    }
}
