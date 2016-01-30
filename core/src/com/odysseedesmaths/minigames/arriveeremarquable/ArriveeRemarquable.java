package com.odysseedesmaths.minigames.arriveeremarquable;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.minigames.MiniGame;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Hero;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArriveeRemarquable extends MiniGame {
	private static ArriveeRemarquable instance = null;

	public static final int TIME_LIMIT = 5;
	public static final int STOP_SPAWN = 10;

	public Hero hero;
	public Horde horde;
	public Terrain terrain;
	public Set<Enemy> enemies;
	public Set<Enemy> deadpool;
	public Set<Item> items;
	public Map<Class<? extends Item>, Integer> activeItems;

    public ArriveeRemarquable(OdysseeDesMaths game) {
        super(game);
        instance = this;

        // Initialisations
        init();
        Enemy.init();
        Item.init();

        setScreen(new ForetScreen());
    }
    
	public static ArriveeRemarquable get() {
		return instance;
	}

	public void init() {
		terrain = new Terrain();
		horde = new Horde(Horde.NORMAL);
		hero = new Hero(terrain.getDepart());
		enemies = new HashSet<Enemy>();
        deadpool = new HashSet<Enemy>();
		items = new HashSet<Item>();
		activeItems = new HashMap<Class<? extends Item>, Integer>();
    }

	public void playTurn() {
		playHorde();
        playEnemies();
        trySpawnItem();
        trySpawnEnemy();
        updateActiveItems();
	}

    public void playHorde() {
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
        if (front == terrain.getWidth()/2) {
            horde.setVitesse(Horde.FAST);
        }
    }

    public void playEnemies() {
        List<Enemy> toAct = new ArrayList<Enemy>();
        toAct.addAll(enemies);
        while (!toAct.isEmpty()) {
            Enemy s = toAct.get(0);
            if (s.isAlive()) s.act();
            toAct.remove(0);
        }
    }

    public void trySpawnItem() {
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
            spawn.setEntity(item);
            items.add(item);
        }
    }

    public void trySpawnEnemy() {
        if (!Enemy.popFull() && (MathUtils.random() < Enemy.SPAWN_CHANCE) && (hero.getCase().i < terrain.getWidth()-STOP_SPAWN)) {
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
            spawn.setEntity(enemy);
            enemies.add(enemy);
        }
    }

    public void updateActiveItems() {
        for (Map.Entry<Class<? extends Item>, Integer> entry : activeItems.entrySet()) {
            int newValue = entry.getValue()-1;
            if (newValue <= 0) {
                activeItems.remove(entry.getKey());
            } else {
                activeItems.put(entry.getKey(), newValue);
            }
        }
    }

	public void destroy(Item aItem) {
        items.remove(aItem);
		aItem.getCase().free();
        Item.decreasePop(aItem);
	}

	public void destroy(Enemy aEnemy) {
		enemies.remove(aEnemy);
		aEnemy.getCase().free();
		Enemy.decreasePop(aEnemy);
        deadpool.add(aEnemy);
	}

	@Override
	public void gameOver() {

	}

}