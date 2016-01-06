package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.odysseedesmaths.arriveeremarquable.Heros;
import com.odysseedesmaths.arriveeremarquable.Horde;
import com.odysseedesmaths.arriveeremarquable.Signe;
import com.odysseedesmaths.arriveeremarquable.Terrain;

public class ArriveeGame extends MiniJeu {
	private Heros heros;
	private Horde horde;
	private Terrain terrain;
	private Array<Signe> signes;

	public Heros getHeros() {
		return heros;
	}

	public Horde getHorde() {
		return horde;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public Array<Signe> getSignes() {
		return signes;
	}

	@Override
	public void create() {
		super.create();
		terrain = Terrain.get();
	//	heros = new Heros(terrain.getDepart(), 5);

		addTexture("heros", new Texture(Gdx.files.internal("heros64.png")));
		addTexture("signe", new Texture(Gdx.files.internal("signe64.png")));
		addPathMusique("musicTest", "Arcade_Machine.ogg");;
		setScreen(new ArriveeScreen(this));
	}

	@Override
	public void render() {
        playMusic(musiques.get("musicTest"));
		super.render();
	}

	public void dispose() {

	}
}
