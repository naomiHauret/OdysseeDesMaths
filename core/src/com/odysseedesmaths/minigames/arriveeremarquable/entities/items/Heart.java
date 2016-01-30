package com.odysseedesmaths.minigames.arriveeremarquable.entities.items;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class Heart extends Item {

    @Override
    public void trigger() {
        ArriveeRemarquable.get().hero.increasePDV();
        super.trigger();
    }
}
