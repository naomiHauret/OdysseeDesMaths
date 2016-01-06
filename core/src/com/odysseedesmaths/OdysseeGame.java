package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.odysseedesmaths.metier.Heros;
import com.odysseedesmaths.metier.Horde;
import com.odysseedesmaths.metier.Signe;
import com.odysseedesmaths.metier.Terrain;

public class OdysseeGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

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
		terrain = Terrain.get();
	//	heros = new Heros(terrain.getDepart(), 5);

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new ArriveeScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
