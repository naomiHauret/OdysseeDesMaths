package com.odysseedesmaths.scenes;


import com.badlogic.gdx.graphics.Texture;
import com.odysseedesmaths.ModeSceneScreen;

/*
    Classe générale des scènes
 */

public abstract class Scene {
    private static ModeSceneScreen mss;

    public abstract Texture getBackground(); // return le fond de la scene
    public abstract void aventure(); // permet à l'aventure de se dérouler

    public static void updateMss (ModeSceneScreen mode) {
        mss = mode;
    }

    public static ModeSceneScreen getMss() {
        return mss;
    }
}
