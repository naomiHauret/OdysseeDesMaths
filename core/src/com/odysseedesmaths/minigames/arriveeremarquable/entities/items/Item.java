package com.odysseedesmaths.minigames.arriveeremarquable.entities.items;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public abstract class Item extends Entity {

    public Item(Case c) {
        super(c);
    }

    public Item() {
        this(null);
    }

    public void trigger() {
        ArriveeRemarquable.get().destroy(this);
    }


    /**********
     * STATIC *
     **********/

    private static final int SHIELD = 0;
    private static final int HEART = 1;
    private static final int FREEZE = 2;
    private static final int SUPERFREEZE = 3;
    private static final int NB_TYPES = 4;

    public static final int SPAWN_MIN_DISTANCE = 10;
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
        max[SHIELD] = 2;
        max[HEART] = 2;
        max[FREEZE] = 0;
        max[SUPERFREEZE] = 0;
    }

    public static boolean popFull(int item) {
        return pop[item] >= max[item];
    }

    public static boolean popFull() {
        boolean res = true;

        for (int i=0; i < NB_TYPES; i++) {
            res = res && popFull(i);
        }

        return res;
    }

    public static void increasePop(Item e) {
        if (e instanceof Shield) pop[SHIELD]++;
        else if (e instanceof Heart) pop[HEART]++;
        else if (e instanceof Freeze) pop[FREEZE]++;
        else pop[SUPERFREEZE]++;
    }

    public static void decreasePop(Item e) {
        if (e instanceof Shield) pop[SHIELD]--;
        else if (e instanceof Heart) pop[HEART]--;
        else if (e instanceof Freeze) pop[FREEZE]--;
        else pop[SUPERFREEZE]--;
    }

    public static Item make() {
        Item item;
        int num;

        do {
            num = MathUtils.random(NB_TYPES - 1);
        } while (popFull(num));

        switch (num) {
        case SHIELD:
            item = new Shield();
            break;
        case HEART:
            item = new Heart();
            break;
        case FREEZE:
            item = new Freeze();
            break;
        default:
            item = new SuperFreeze();
            break;
        }

        increasePop(item);

        return item;
    }
}
