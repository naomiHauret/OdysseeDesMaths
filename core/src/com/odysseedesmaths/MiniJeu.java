package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public abstract class MiniJeu extends Game {
    private Map<String,Texture> graphics;
    private Map<String,SoundEffect> effetsSonores;
    private Map<String,String> musiques; //deux musiques ne sont pas jouées en même temps, pas besoin d'instancier deux objet (d'ou le String)
    //la gestion de la musique se fait via les méthodes statiques de Musique (classe)

    public SpriteBatch batch;
    public BitmapFont font;

    public Map<String,Texture> getGraphics() {
        return graphics;
    }

    public Map<String, SoundEffect> getEffetsSonores() {
        return effetsSonores;
    }

    public Map<String, String> getMusiques() {
        return musiques;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        effetsSonores = new HashMap<String, SoundEffect>();
        graphics = new HashMap<String, Texture>();
        musiques = new HashMap<String, String>();
    }

    /**
     * Méthode appelée pour ajouter le chemin d'un fichier de musique dans le HashMap contenant toutes les musiques
     * @param musicName Le nom de la musique
     * @param path Le chemin de la musique
     */
    public void addMusique(String musicName, String path){
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
        Musique.set(musiques.get(name));
        Musique.play();
    }

    public void dispose() {
        graphics.clear();
        effetsSonores.clear();
        musiques.clear();
        batch.dispose();
        font.dispose();
    }

    public abstract void gameOver();
}
