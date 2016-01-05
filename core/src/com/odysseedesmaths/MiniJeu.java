package com.odysseedesmaths;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by trilunaire on 04/01/16.
 */
public abstract class MiniJeu {
    public HashMap<String,SoundEffect> effetsSonores;
    public HashMap<String,Texture> graphics;
    public HashMap<String,String> musiques; //deux musiques ne sont pas jouées en même temps, pas besoin d'instancier deux objet (d'ou le String)
    //la gestion de la musique se fait via les méthodes statiques de Musique (classe)
    private String regles; //voir si on garde un String

    //TODO: Implémenter un timer

    public MiniJeu(){

    }

    /**
     * Méthode appelée pour ajouter le chemin d'un fichier de musique dans le HashMap contenant toutes les musiques
     * @param musicName Le nom de la musique
     * @param path Le chemin de la musique
     */
    public void addPathMusique(String musicName, String path){
        this.musiques.put(musicName,path);
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
        if(Musique.audio.isPlaying()){
            Musique.stop();
        }
        Musique.setPath(musiques.get(name));
        Musique.play();
    }
}
