package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by trilunaire on 04/01/16.
 */
public class Musique {
    public static com.badlogic.gdx.audio.Music audio = null;

    public static void setPath(String path){
        audio = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public static void play(){
        audio.play();
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
