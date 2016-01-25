package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.MiniJeu;
import com.odysseedesmaths.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.arriveeremarquable.entities.Hero;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.arriveeremarquable.map.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArriveeGame extends MiniJeu {
	private static ArriveeGame instance = null;

	public static final int LIMITE_TEMPS = 5;
	public static final int STOP_SPAWN = 10;

	public Hero hero;
	public Horde horde;
	public Terrain terrain;
	public Set<Enemy> enemies;
	public Set<Item> items;
	public Map<Class<? extends Item>, Integer> activeItems;

	public static ArriveeGame get() {
		return instance;
	}

	public void init() {
		terrain = new Terrain();
		horde = new Horde(Horde.NORMAL);
		hero = new Hero(terrain.getDepart());
		enemies = new HashSet<Enemy>();
		items = new HashSet<Item>();
		activeItems = new HashMap<Class<? extends Item>, Integer>();
	}

	@Override
	public void create() {
		super.create();
		instance = this;

		// Initialisations
		init();
		Enemy.init();
        Item.init();

		// Ajout des assets graphiques
		addTexture("hero", new Texture(Gdx.files.internal("heros.png")));
        addTexture("horde", new Texture(Gdx.files.internal("horde.png")));
		addTexture("signeEgal", new Texture(Gdx.files.internal("signeEgal.png")));
		addTexture("signeAdd", new Texture(Gdx.files.internal("signeAdd.png")));
		addTexture("signeSoust", new Texture(Gdx.files.internal("signeSoust.png")));
		addTexture("signeMult", new Texture(Gdx.files.internal("signeMult.png")));
		addTexture("signeDiv", new Texture(Gdx.files.internal("signeDiv.png")));
		addTexture("shield", new Texture(Gdx.files.internal("itemBouclier.png")));
		addTexture("buffShield", new Texture(Gdx.files.internal("bouclier.png")));
        addTexture("heart", new Texture(Gdx.files.internal("coeur.png")));

		// Ajout des assets sonores
		addPathMusique("musicTest", "Arcade_Machine.ogg");;

		setScreen(new ForetScreen());
	}

	@Override
	public void render() {
		super.render();
		getScreen().render(60f*Gdx.graphics.getDeltaTime());
	}

	public void playTurn() {
		// Tour de la horde
		horde.act();
        int front = horde.getFront();
        for (int i = Math.max(front - 2, 0); i <= front; i++) {
            for (int j=0; j < terrain.getHeight(); j++) {
                Entity e = terrain.getCases()[i][j].getEntity();
                if (e != null) {
                    if (e instanceof Hero) {
                        gameOver();
                    } else if (e instanceof Item) {
                        destroy((Item) e);
                    } else if (e instanceof Enemy) {
                        destroy((Enemy) e);
                    }
                }
            }
        }
        if (front == terrain.getWidth()/2) horde.setVitesse(Horde.FAST);

		// Tour des ennemis
		List<Enemy> toAct = new ArrayList<Enemy>();
		toAct.addAll(enemies);
		while (!toAct.isEmpty()) {
			Enemy s = toAct.get(0);
			if (s.isAlive()) s.act();
			toAct.remove(0);
		}

        // Spawn d'un item
        if (!Item.popFull() && MathUtils.random() < Item.SPAWN_CHANCE && hero.getCase().i < terrain.getWidth()-STOP_SPAWN) {
            Item item = Item.make();
            Case spawn;
            int distance;
            do {
                int i = MathUtils.random(hero.getCase().i - 1, terrain.getWidth() - 2);
                int j = MathUtils.random(terrain.getHeight() - 1);
                spawn = terrain.getCases()[i][j];
                distance = terrain.heuristic(hero.getCase(), spawn);
            } while (spawn.isObstacle() || spawn.isTaken() || distance < Item.SPAWN_MIN_DISTANCE || distance > Item.SPAWN_MAX_DISTANCE);
            item.setCase(spawn);
            items.add(item);
        }

		// Spawn d'un ennemi
		if (!Enemy.popFull() && MathUtils.random() < Enemy.SPAWN_CHANCE && hero.getCase().i < terrain.getWidth()-STOP_SPAWN) {
			Enemy enemy = Enemy.make();
			Case spawn;
			int distance;
			do {
                int i = MathUtils.random(hero.getCase().i - 1, terrain.getWidth() - 2);
				int j = MathUtils.random(terrain.getHeight() - 1);
				spawn = terrain.getCases()[i][j];
				distance = terrain.heuristic(hero.getCase(), spawn);
			} while (spawn.isObstacle() || spawn.isTaken() || distance < Enemy.SPAWN_MIN_DISTANCE || distance > Enemy.SPAWN_MAX_DISTANCE);
			enemy.setCase(spawn);
			enemies.add(enemy);
		}

        // Mise à jour des items actifs
        for (Map.Entry<Class<? extends Item>, Integer> entry : activeItems.entrySet()) {
            int new_value = entry.getValue()-1;
            if (new_value <= 0) {
                activeItems.remove(entry.getKey());
            } else {
                activeItems.put(entry.getKey(), new_value);
            }
        }
	}

	public void destroy(Item e) {
        items.remove(e);
		e.getCase().free();
        Item.decreasePop(e);
	}

	public void destroy(Enemy s) {
		enemies.remove(s);
		s.getCase().free();
		Enemy.decreasePop(s);
	}

	@Override
	public void gameOver() {

	}

	public void dispose() {
	}
}
