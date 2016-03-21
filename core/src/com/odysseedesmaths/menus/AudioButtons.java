package com.odysseedesmaths.menus;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;

public class AudioButtons extends Table {

    private Skin skin;
    private Button music;
    private Button sounds;

    public AudioButtons() {
        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));

        ButtonStyle musicButtonStyle = new ButtonStyle();
        musicButtonStyle.up = skin.getDrawable("music_on");
        skin.add("music", musicButtonStyle);

        ButtonStyle soundsButtonStyle = new ButtonStyle();
        soundsButtonStyle.up = skin.getDrawable("sounds_on");
        skin.add("sounds", soundsButtonStyle);

        music = new Button(skin, "music");
        sounds = new Button(skin, "sounds");

        add(music);
        add(sounds);

        AudioButtonsListener listener = new AudioButtonsListener();
        music.addListener(listener);
        sounds.addListener(listener);
    }

    private class AudioButtonsListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == music) {
                if (Musique.isPlaying()) {
                    Musique.pause();
                    music.getStyle().up = skin.getDrawable("music_off");
                } else {
                    Musique.play();
                    music.getStyle().up = skin.getDrawable("music_on");
                }
            } else if (source == sounds) {
                if (Musique.isPlaying()) {
                    Musique.pause();
                    sounds.getStyle().up = skin.getDrawable("sounds_off");
                } else {
                    Musique.play();
                    sounds.getStyle().up = skin.getDrawable("sounds_on");
                }
            }

            return true;
        }
    }
}