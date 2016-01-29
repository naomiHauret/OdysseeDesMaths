package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

public abstract class MiniJeu extends Game {

    private String regles; //voir si on garde un String
    private Timer timer;



    @Override
    public void create() {

    }

    @Override
    public void render() {
        super.render();
    }

    public void dispose() {

    }

    public void initTimer(int delay){
        timer = new Timer(delay, new TimeOutListener());
    }

    public void setDelay(int newDelay){
        timer.setDelay(newDelay);
    }

    public void addTimerAction(ActionListener newAL){
        timer.addActionListener(newAL);
    }

    public void removeTimerAction(ActionListener oldAL) {
        timer.removeActionListener(oldAL);
    }

    public abstract void gameOver();

    private class TimeOutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //gameOver(); fonction game Over appellée à la fin du compte à rebours
        }
    }
}
