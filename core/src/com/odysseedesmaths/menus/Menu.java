package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Menu extends Stage {
    public ImageButton musique;
    public ImageButton son;
    public BitmapFont font = null;
    public FreeTypeFontGenerator ftfg = null;
    public FreeTypeFontGenerator.FreeTypeFontParameter ftfp = null;

    public Menu(int fontRatio, Color c, String fontPath) {
        ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = new BitmapFont();
        ftfp.size= Gdx.graphics.getHeight()/ fontRatio; //the size can be change later
        ftfp.color=c;
        ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        font = ftfg.generateFont(ftfp);
        //Skin skinMusique = new Skin();
        //skinMusique.add("image", Gdx.files.internal("music64.png"));
        //ImageButtonStyle bsMusique = new ImageButton.ImageButtonStyle();
        //bsMusique.imageChecked = skinMusique.getDrawable("image");
    }
}