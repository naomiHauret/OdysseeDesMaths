package com.odysseedesmaths;

import com.badlogic.gdx.audio.Music;

public class Musique {
    private static Music currentMusic = null;
    private static String currentFile = null;

    private Musique() {}

    public static Music getCurrent() {
        return currentMusic;
    }

    public static void setCurrent(String fileName) {
        if (currentMusic != null) stop();

        currentFile = fileName;

        if (!Assets.getManager().isLoaded(fileName)) {
            Assets.getManager().load(fileName, Music.class);
            Assets.getManager().finishLoadingAsset(fileName);
        }
        currentMusic = Assets.getManager().get(fileName, Music.class);
    }

    public static void play() {
        currentMusic.play();
        currentMusic.setLooping(true);
    }

    public static void stop() {
        Assets.getManager().unload(currentFile);
    }

    public static boolean isPlaying() {
        return (currentMusic != null) && currentMusic.isPlaying();
    }

    public static void setVolume(int volumePercent) {
        if (volumePercent>100 || volumePercent<0) {
            throw new IllegalArgumentException("Pourcentage incorrect : " + volumePercent);
        } else {
            currentMusic.setVolume(volumePercent / 100f);
        }
    }
}
