package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Hero;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Character;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Shield;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Enemy extends Character {

    private boolean alive = true;

    public Enemy(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Enemy(ArriveeRemarquable minigame) {
        super(minigame);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void moveTo(Case c) {
        super.moveTo(c);
        if (!isAlive()) setCase(c);
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = true;

        if (e instanceof Hero) {
            if (getMinigame().activeItems.get(Shield.class) == null) {
                ((Hero) e).decreasePDV();
            }
            getMinigame().destroy(this);
            setAlive(false);
        } else if (e instanceof Item) {
            getMinigame().destroy((Item)e);
        }

        return continuer && isAlive();
    }

    public abstract void act();


    /**********
     * STATIC *
     **********/

    private static final int STICKY = 0;
    private static final int SMART = 1;
    private static final int SUPERSMART = 2;
    private static final int GREED = 3;
    private static final int LOST = 4;
    private static final int NB_TYPES = 5;

    public static final int SPAWN_MIN_DISTANCE = 15;
    public static final int SPAWN_MAX_DISTANCE = 20;
    public static final double SPAWN_CHANCE = 0.5;

    private static int[] max;
    private static int[] pop;

    public static void init() {
        pop = new int[NB_TYPES];
        for (int i=0; i < NB_TYPES; i++) {
            pop[i] = 0;
        }

        max = new int[NB_TYPES];
        max[STICKY] = 1;
        max[SMART] = 1;
        max[SUPERSMART] = 0;
        max[GREED] = 1;
        max[LOST] = 10;
    }

    public static boolean popFull(int enemy) {
        return pop[enemy] >= max[enemy];
    }

    public static boolean popFull() {
        boolean res = true;

        for (int i=0; i < NB_TYPES; i++) {
            res = res && popFull(i);
        }

        return res;
    }

    public static void increasePop(Enemy e) {
        if (e instanceof Sticky) pop[STICKY]++;
        else if (e instanceof Smart) pop[SMART]++;
        else if (e instanceof SuperSmart) pop[SUPERSMART]++;
        else if (e instanceof Greed) pop[GREED]++;
        else pop[LOST]++;
    }

    public static void decreasePop(Enemy e) {
        if (e instanceof Sticky) pop[STICKY]--;
        else if (e instanceof Smart) pop[SMART]--;
        else if (e instanceof SuperSmart) pop[SUPERSMART]--;
        else if (e instanceof Greed) pop[GREED]--;
        else pop[LOST]--;
    }

    public static Enemy make(ArriveeRemarquable minigame) {
        Enemy enemy;
        int num;

        do {
            num = MathUtils.random(NB_TYPES - 1);
        } while (popFull(num));

        switch (num) {
        case STICKY:
            enemy = new Sticky(minigame);
            break;
        case SMART:
            enemy = new Smart(minigame);
            break;
        case SUPERSMART:
            enemy = new SuperSmart(minigame);
            break;
        case GREED:
            enemy = new Greed(minigame);
            break;
        default:
            enemy = new Lost(minigame);
            break;
        }

        increasePop(enemy);

        return enemy;
    }
}
