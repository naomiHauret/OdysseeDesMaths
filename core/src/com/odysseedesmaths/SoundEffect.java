package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
/*
        Classe type pour les bruitages
 */

public class SoundEffect {
    private Sound sE = null;
    private long idSE = 0;

    public SoundEffect(String path){
        this.sE = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public void play(){
        this.idSE = this.sE.play();
    }

    public void dispose(){
        this.sE.dispose();
    }

    public void setVolume(int volumePercent){
        if(volumePercent>100 || volumePercent<0){
            System.out.println("Valeur invalide");
        }else{
            this.sE.setVolume(this.idSE,(float)volumePercent/100);
        }
    }

    public void repeat(boolean b){
        if(b){
            this.sE.loop();
        }else{
            //arreter loop
        }
    }
}
