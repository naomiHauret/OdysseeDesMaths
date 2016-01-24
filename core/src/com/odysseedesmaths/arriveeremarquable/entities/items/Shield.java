package com.odysseedesmaths.arriveeremarquable.entities.items;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public class Shield extends Item {

    private static final int DUREE = 7;

    @Override
    public void trigger() {
        super.trigger();
        ArriveeGame.get().activeItems.put(this.getClass(), DUREE);
    }
}
