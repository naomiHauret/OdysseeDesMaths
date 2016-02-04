package com.odysseedesmaths.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.odysseedesmaths.minigames.MiniGame;

/**
 * Created by trilunaire on 04/02/16.
 */
public class GameOver extends Menu{
    private TextButton.TextButtonStyle txtButtonStyle;
    private Table tab;
    private TextButton retry;
    private Screen currentGame;
    private InputListener retryListener;

    /**
     * Constructeur appelé quand le joueur a perdu. L'objet game sert à recommencer le jeu
     * @param game
     */
    public GameOver(Screen game){
        super(3,new Color(255f,255f,255f,1),"fonts/pixel-life.TTF");
        this.currentGame = game;
        this.createGUI();
    }

    /**
     * Permet de créer l'interface graphique de l'écran GameOver
     */
    public void createGUI(){
        tab = new Table();
        retryListener = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() == retry) {
                    //si on touche l'écran
                 /*currentGame.retry()*/
                    return true;
                }
                return true;
            }
        };

        txtButtonStyle = new TextButton.TextButtonStyle();
        txtButtonStyle.font = font;

        retry = new TextButton("Game Over", txtButtonStyle);

        tab.setFillParent(true);
        tab.add(retry);
    }
}
