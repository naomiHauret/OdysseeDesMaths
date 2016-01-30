package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class OdysseeDesMaths extends Game {

    @Override
    public void create() {
        Assets.getManager().load(Assets.class);
        Assets.getManager().finishLoading();

        setScreen(new ArriveeRemarquable(this));
    }

}
