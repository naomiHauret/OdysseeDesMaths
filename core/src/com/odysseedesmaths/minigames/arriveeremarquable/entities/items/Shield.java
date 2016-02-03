package com.odysseedesmaths.minigames.arriveeremarquable.entities.items;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public class Shield extends Item {

    private static final int DUREE = 1 + 7; // 1+ car le tour où le héros prend l'objet sa durée est décrémentée

    public Shield(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Shield(ArriveeRemarquable minigame) {
        super(minigame);
    }

    @Override
    public void trigger() {
        getMinigame().activeItems.put(this.getClass(), DUREE);
        super.trigger();
    }
}
