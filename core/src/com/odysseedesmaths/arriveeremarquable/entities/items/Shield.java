package com.odysseedesmaths.arriveeremarquable.entities.items;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public class Shield extends Item {

    private static final int DUREE = 1 + 7; // 1+ car le tour où le héros prend l'objet sa durée est décrémentée

    @Override
    public void trigger() {
        ArriveeGame.get().activeItems.put(this.getClass(), DUREE);
        super.trigger();
    }
}
