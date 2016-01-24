package com.odysseedesmaths.arriveeremarquable.entities.items;


import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public class Heart extends Item {

    @Override
    public void trigger() {
        ArriveeGame.get().hero.increasePDV();
    }
}
