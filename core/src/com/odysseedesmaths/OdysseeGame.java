package com.odysseedesmaths;

<<<<<<< HEAD
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
=======
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
>>>>>>> 7a5711d61ebb4eb50e2e179db49fc90cfd718f96
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

<<<<<<< HEAD
public class OdysseeGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Music musicTest;
	
=======
>>>>>>> 7a5711d61ebb4eb50e2e179db49fc90cfd718f96
	@Override
	public void create () {
		terrain = Terrain.get();
		heros = new Heros(terrain.getDepart(), 5);

		batch = new SpriteBatch();
<<<<<<< HEAD
		img = new Texture("badlogic.jpg");
		musicTest = Gdx.audio.newMusic(Gdx.files.internal("Arcade_Machine.ogg"));
=======
		font = new BitmapFont();
		this.setScreen(new ArriveeScreen(this));
>>>>>>> 7a5711d61ebb4eb50e2e179db49fc90cfd718f96
	}

	@Override
	public void render () {
<<<<<<< HEAD
        musicTest.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(img, 0, 0);
		batch.end();
=======
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
>>>>>>> 7a5711d61ebb4eb50e2e179db49fc90cfd718f96
	}
}
