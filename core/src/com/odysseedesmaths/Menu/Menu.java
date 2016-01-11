package com.odysseedesmaths.Menu;

public class Menu extends Screen {
    private ImageButton musique;
    private ImageButton son;

    public Menu() {
        //TextureAtlas atlasMusique = new TextureAtlas(Gdx.files.internal(""));
        Skin skinMusique = new Skin();
        skinMusique.add("image", Gdx.files.internal("music64.png"));
        ImageButtonStyle bsMusique = new ImageButtonStyle();
        bsMusique.imageChecked = skinMusique.getDrawable("image");
        musique = new Button();
        son = new Button();
    }
}