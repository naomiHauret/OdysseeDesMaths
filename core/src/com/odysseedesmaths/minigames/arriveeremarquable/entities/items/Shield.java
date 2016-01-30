package com.odysseedesmaths.minigames.arriveeremarquable.entities.items;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class Shield extends Item {

    private static final int DUREE = 1 + 7; // 1+ car le tour où le héros prend l'objet sa durée est décrémentée

    @Override
    public void trigger() {
        ArriveeRemarquable.get().activeItems.put(this.getClass(), DUREE);
        super.trigger();
    }
}
