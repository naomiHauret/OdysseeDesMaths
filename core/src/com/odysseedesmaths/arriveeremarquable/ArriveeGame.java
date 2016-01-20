package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.MiniJeu;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.signes.SigneFactory;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.arriveeremarquable.map.Terrain;
import com.odysseedesmaths.arriveeremarquable.entities.Heros;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Signe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArriveeGame extends MiniJeu {
	private static ArriveeGame instance = null;

	public static final int LIMITE_TEMPS = 10;

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
		Signe.init();

		// Ajout des assets graphiques
		addTexture("heros", new Texture(Gdx.files.internal("heros.png")));
		addTexture("signeEgal", new Texture(Gdx.files.internal("signeEgal.png")));
		addTexture("signeAdd", new Texture(Gdx.files.internal("signeAdd.png")));
		addTexture("signeSoust", new Texture(Gdx.files.internal("signeSoust.png")));
		addTexture("signeMult", new Texture(Gdx.files.internal("signeMult.png")));
		addTexture("signeDiv", new Texture(Gdx.files.internal("signeDiv.png")));
		addTexture("bouclier", new Texture(Gdx.files.internal("bouclier.png")));

		// Ajout des assets sonores
		addPathMusique("musicTest", "Arcade_Machine.ogg");;

		setScreen(new ForetScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	public void playTurn() {
		/* Mise à jour des items actifs
		for (Map.Entry<String, Integer> entry : activeItems.entrySet()) {
			int new_value = entry.getValue()-1;
			if (new_value == 0) {
				activeItems.remove(entry.getKey());
			} else {
				entry.setValue(new_value);
			}
		}*/

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
		List<Signe> toAct = new ArrayList<Signe>();
		toAct.addAll(signes);
		while (!toAct.isEmpty()) {
			Signe s = toAct.get(0);
			if (s.isAlive()) s.act();
			toAct.remove(0);
		}

		// Spawn de signe
		if (!Signe.popFull() && MathUtils.random() < Signe.SPAWN_CHANCE) {
			Signe signe = SigneFactory.makeSigne();
			Case spawn;
			do {
				int i = MathUtils.random(heros.getCase().i, terrain.getWidth() - 1);
				int j = MathUtils.random(terrain.getHeight() - 1);
				spawn = terrain.getCases()[i][j];
			} while (spawn.isObstacle() || spawn.isTaken() || terrain.heuristic(heros.getCase(), spawn) < Signe.SPAWN_RADIUS);
			signe.setCase(spawn);
			signes.add(signe);
		}
	}

	public void destroy(Item e) {
		items.remove(e);
		e.getCase().free();
	}

	public void destroy(Signe s) {
		signes.remove(s);
		s.getCase().free();
		Signe.decreasePop(s);
	}

	@Override
	public void gameOver() {

	}

	public void dispose() {
	}
}
