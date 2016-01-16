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
    public Map<String,SoundEffect> effetsSonores;
    public Map<String,Texture> graphics;
    public Map<String,String> musiques; //deux musiques ne sont pas jouées en même temps, pas besoin d'instancier deux objet (d'ou le String)
    //la gestion de la musique se fait via les méthodes statiques de Musique (classe)
    private String regles; //voir si on garde un String
    private Timer timer;

    public SpriteBatch batch;
    public BitmapFont font;

    protected MiniJeu() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        effetsSonores = new HashMap<String, SoundEffect>();
        graphics = new HashMap<String, Texture>();
        musiques = new HashMap<String, String>();
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Méthode appelée pour ajouter le chemin d'un fichier de musique dans le HashMap contenant toutes les musiques
     * @param musicName Le nom de la musique
     * @param path Le chemin de la musique
     */
    public void addPathMusique(String musicName, String path){
        this.musiques.put(musicName, path);
    }

    /**
     * Suppression d'une musique
     * @param name
     */
    public void delMusique(String name){
        this.musiques.remove(name);
    }

    public void addSound(String name, SoundEffect newSound){
        this.effetsSonores.put(name, newSound);
    }

    public void delSound(String name){
        this.effetsSonores.remove(name);
    }

    public void addTexture(String name, Texture newTexture){
        this.graphics.put(name, newTexture);
    }

    public void delTexture(String name){
        this.graphics.remove(name);
    }

    public void playMusic(String name){
        if (Musique.isPlaying()) {
            Musique.stop();
        }
        Musique.setPath(musiques.get(name));
        Musique.play();
    }

    public void dispose() {
        //dipose all the texture
        graphics.clear();
        effetsSonores.clear();
        musiques.clear();
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

    private class TimeOutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //gameOver(); fonction game Over appellée à la fin du compte à rebours
        }
    }
}
