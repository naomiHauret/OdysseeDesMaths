package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Gère les paramètres de jeu réglables par l'utilisateur.
 */
public class Settings {

    private Preferences settings;

    private boolean musicMuted;
    private boolean effectsMuted;

    public Settings() {
        this.settings = Gdx.app.getPreferences("settings");
        if (!settings.contains("musicMuted")) init();
        load();
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public boolean areEffectsMuted() {
        return effectsMuted;
    }

    public void setMusicMuted(boolean muted) {
        this.musicMuted = muted;
        settings.putBoolean("musicMuted", muted);
        settings.flush();
    }

    public void setEffectsMuted(boolean muted) {
        this.effectsMuted = muted;
        settings.putBoolean("effectsMuted", muted);
        settings.flush();
    }

    private void init() {
        settings.putBoolean("musicMuted", false);
        settings.putBoolean("effectsMuted", false);
        settings.flush();
    }

    private void load() {
        musicMuted = settings.getBoolean("musicMuted");
        effectsMuted = settings.getBoolean("effectsMuted");
    }
}
