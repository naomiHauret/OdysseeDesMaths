package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.MiniJeu;
import com.odysseedesmaths.arriveeremarquable.entities.Entite;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Add;
import com.odysseedesmaths.arriveeremarquable.entities.signes.SigneFactory;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.arriveeremarquable.map.Terrain;
import com.odysseedesmaths.arriveeremarquable.entities.Heros;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Signe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArriveeGame extends MiniJeu {
	private static ArriveeGame instance = null;

	public Heros heros;
	public Horde horde;
	public Terrain terrain;
	public Set<Signe> signes;
	public Set<Item> items;
	public Map<String,Integer> activeItems;

	public static ArriveeGame get() {
		return instance;
	}

	public void init() {
		terrain = new Terrain();
		horde = new Horde(Horde.EASY);
		heros = new Heros(terrain.getDepart());
		signes = new HashSet<Signe>();
		items = new HashSet<Item>();
		activeItems = new HashMap<String, Integer>();
	}

	@Override
	public void create() {
		super.create();
		instance = this;

		// Initialisations
		init();

		// Ajout des assets graphiques
		addTexture("heros", new Texture(Gdx.files.internal("heros.png")));
		addTexture("signe", new Texture(Gdx.files.internal("signe.png")));
		addTexture("bouclier", new Texture(Gdx.files.internal("bouclier.png")));

		// Ajout des assets sonores
		addPathMusique("musicTest", "Arcade_Machine.ogg");;

		setScreen(new ArriveeScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	public void playTurn() {
		// Mise à jour des items actifs
		for (Map.Entry<String, Integer> entry : activeItems.entrySet()) {
			int new_value = entry.getValue()-1;
			if (new_value == 0) {
				activeItems.remove(entry.getKey());
			} else {
				entry.setValue(new_value);
			}
		}

		/* Tour de la horde
		horde.act();
		for (int i=0; i < terrain.getHeight(); i++) {
			Entite e = terrain.getCases()[i][horde.getFront()].getEntite();
			if (e != null) {
				if (e instanceof Heros) {
					gameOver();
				} else if (e instanceof Item) {
					destroy((Item)e);
				}
			}
		}*/

		// Tour des signes
        for (Signe s : signes) {
			s.act();
		}

		// Spawn de signe
		if (MathUtils.random() < 0.2) {
			Signe signe = SigneFactory.makeSigne();
			Case spawn;
			do {
				spawn = terrain.getCases()[MathUtils.random(terrain.getWidth() - 1)][MathUtils.random(terrain.getHeight()-1)];
			} while (spawn.isObstacle() || ArriveeScreen.isVisible(spawn));
			signe.setCase(spawn);
			signes.add(signe);
		}
	}

	public void destroy(Item e) {
		items.remove(e);
	}

	public void destroy(Signe s) {
		signes.remove(s);
	}

	public void gameOver() {
	}

	public void dispose() {
	}
}
