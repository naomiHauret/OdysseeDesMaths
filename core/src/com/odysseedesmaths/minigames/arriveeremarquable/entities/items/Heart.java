package com.odysseedesmaths.minigames.arriveeremarquable.entities.items;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public class Heart extends Item {

    public Heart(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Heart(ArriveeRemarquable minigame) {
        super(minigame);
    }

    @Override
    public void trigger() {
        getMinigame().hero.increasePDV();
        super.trigger();
    }
}
