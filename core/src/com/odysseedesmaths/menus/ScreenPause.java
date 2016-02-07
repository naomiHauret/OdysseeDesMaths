package com.odysseedesmaths.menus;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * Created by trilunaire on 11/01/16.
 */
public class ScreenPause implements ApplicationListener{
    BitmapFont font = null;
    FreeTypeFontGenerator ftfg = null;
    FreeTypeFontParameter ftfp = null;

    Button retourJeu, recommencer,quitter;
    TextButtonStyle txtButtonStyle=null;
    Stage stage;

    public void create(){
        ftfp = new FreeTypeFontParameter();
        font = new BitmapFont();
        txtButtonStyle = new TextButton.TextButtonStyle();
        ftfp.size= Gdx.graphics.getHeight()/ 10;
        ftfp.color=new Color(255,255,255,1);
        ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel-life.TTF"));
        font = ftfg.generateFont(ftfp);

        txtButtonStyle.font = font;

        retourJeu = new TextButton("Retour",txtButtonStyle);
        recommencer = new TextButton("Recommencer",txtButtonStyle);
        quitter = new TextButton("Quitter",txtButtonStyle);

        // configuration du tableau
        Table tableau = new Table();
        tableau.setFillParent(true);
        tableau.add(retourJeu).width(Gdx.graphics.getWidth() / 3).pad(10);
        tableau.getCell(retourJeu).expand();
        tableau.row();
        tableau.add(recommencer).width(Gdx.graphics.getWidth() / 3).pad(10);;
        tableau.getCell(recommencer).expand();
        tableau.row();
        tableau.add(quitter).width(Gdx.graphics.getWidth() / 3).pad(10);;
        tableau.getCell(quitter).expand();

        stage = new Stage();

        stage.addActor(tableau);
    }

    @Override
    public void resize(int width, int height) {

    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.setProjectionMatrix(camera.combined)
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
