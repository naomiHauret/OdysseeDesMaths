package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odysseedesmaths.menus.MenuPrincipal;

public class OdysseeDesMaths extends Game {
    public SpriteBatch batcher;
    @Override
    public void create() {
        Assets.getManager().load(Assets.class);
        Assets.getManager().finishLoading();

        batcher = new SpriteBatch();
        setScreen(new MenuPrincipal(this));
    }

}
