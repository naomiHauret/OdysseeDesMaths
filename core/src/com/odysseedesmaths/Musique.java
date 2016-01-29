package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class Musique {
    public static Music audio = null;

    private Musique() {}

    public static void set(String path){
        audio = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public static void play(){
        audio.play();
        audio.setLooping(true);
    }

    public static void stop(){
        audio.dispose();
    }

    public static boolean isPlaying() {
        return (audio != null) && audio.isPlaying();
    }

    public static void setVolume(int volumePercent){
        if(volumePercent>100 || volumePercent<0){
            System.out.println("Valeur invalide");
        }else {
            audio.setVolume((float)(volumePercent / 100));
        }
    }
}
