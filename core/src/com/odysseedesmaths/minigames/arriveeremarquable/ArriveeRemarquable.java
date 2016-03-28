package com.odysseedesmaths.minigames.arriveeremarquable;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.dialogs.EndButtonsListener;
import com.odysseedesmaths.dialogs.SimpleDialog;
import com.odysseedesmaths.minigames.MiniGame;
import com.odysseedesmaths.minigames.accrobranche.Accrobranche;
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

// TODO: Bugfix: Héros invincible si ne bouge pas au début
public class ArriveeRemarquable extends MiniGame {

	public static final int TIME_LIMIT = 3;
	public static final int STOP_SPAWN = 10;

	public Hero hero;
	public Horde horde;
	public Terrain terrain;
	public Set<Enemy> enemies;
	public Set<Enemy> deadpool;
	public Set<Item> items;
	public Map<Class<? extends Item>, Integer> activeItems;
    public Timer timer;

    public ArriveeRemarquable(final OdysseeDesMaths game) {
        super(game);
        init();
        setScreen(new ForetScreen(this));
    }

	public void init() {
		terrain = new Terrain();
		horde = new Horde(this, Horde.NORMAL);
		hero = new Hero(this, terrain.getDepart());
		enemies = new HashSet<>();
        deadpool = new HashSet<>();
		items = new HashSet<>();
		activeItems = new HashMap<>();
        timer = new Timer(TIME_LIMIT * Timer.ONE_MINUTE);
        Enemy.init();
        Item.init();
        setState(State.RUNNING);
    }

	public void playTurn() {
		playHorde();
        playEnemies();
        trySpawnItem();
        trySpawnEnemy();
        updateActiveItems();
        if (hero.getCase().equals(terrain.getFin())) win();
    }

    public void pauseGame() {
        setState(State.PAUSED);
        timer.stop();
    }

    public void returnToGame() {
        setState(State.RUNNING);
        timer.start();
    }

    public void restartGame() {
        getGame().setScreen(new ArriveeRemarquable(getGame()));
    }

    public void gameOver() {
        setState(State.GAME_OVER);
        timer.stop();
        ((ForetScreen)currentScreen).gameOver();
    }

    public void win() {
        setState(State.WIN);
        game.getSavesManager().getCurrentSave().setLevel1Finished(true);
        timer.stop();
        ((ForetScreen)currentScreen).win();
    }

    public void afterWin() {
        final OdysseeDesMaths gameReference = game;
        setScreen(new SimpleDialog(game, Assets.DLG_ARRIVEE_2, new EndButtonsListener() {
            @Override
            public void buttonPressed(String buttonName) {
                switch (buttonName) {
                    case "continue":
                        gameReference.setScreen(new Accrobranche(gameReference));
                        break;
                }
            }
        }));
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

    private void playHorde() {
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

    private void playEnemies() {
        List<Enemy> toAct = new ArrayList<Enemy>();
        toAct.addAll(enemies);
        while (!toAct.isEmpty()) {
            Enemy s = toAct.get(0);
            if (s.isAlive()) s.act();
            toAct.remove(0);
        }
    }

    private void trySpawnItem() {
        if (!Item.popFull() && MathUtils.random() < Item.SPAWN_CHANCE && hero.getCase().i < terrain.getWidth()-STOP_SPAWN) {
            Item item = Item.make(this);
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

    private void trySpawnEnemy() {
        if (!Enemy.popFull() && (MathUtils.random() < Enemy.SPAWN_CHANCE) && (hero.getCase().i < terrain.getWidth()-STOP_SPAWN)) {
            Enemy enemy = Enemy.make(this);
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

    private void updateActiveItems() {
        for (Map.Entry<Class<? extends Item>, Integer> entry : activeItems.entrySet()) {
            int newValue = entry.getValue()-1;
            if (newValue <= 0) {
                activeItems.remove(entry.getKey());
            } else {
                activeItems.put(entry.getKey(), newValue);
            }
        }
    }
}
