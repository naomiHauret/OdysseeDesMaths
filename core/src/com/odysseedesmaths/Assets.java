package com.odysseedesmaths;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    public static final AssetManager manager = new AssetManager();

    public static final AssetDescriptor<Texture> HERO = new AssetDescriptor<Texture>("heros.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> UI = new AssetDescriptor<TextureAtlas>("ui/ui.atlas", TextureAtlas.class);

    public static void loadMain() {
        manager.load(HERO);
        manager.load(UI);
        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }

    public static final AssetDescriptor<Texture> ARR_BUFF_SHIELD = new AssetDescriptor<Texture>("bouclier.png", Texture.class);
    public static final AssetDescriptor<Texture> ARR_SHIELD = new AssetDescriptor<Texture>("itemBouclier.png", Texture.class);

    public static void loadArriveeRemarquable() {
        manager.load(ARR_BUFF_SHIELD);
        manager.load(ARR_SHIELD);
        manager.finishLoading();
    }

    public static void disposeArriveeRemarquable() {
        manager.unload(ARR_BUFF_SHIELD.fileName);
        manager.unload(ARR_SHIELD.fileName);
    }

    static {
        loadMain();
    }
}