package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entite;
import com.odysseedesmaths.arriveeremarquable.entities.Heros;
import com.odysseedesmaths.arriveeremarquable.entities.Personnage;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Signe extends Personnage {

    public Signe(Case c) {
        super(c);
    }

    public Signe() {
        this(null);
    }

    @Override
    public boolean meet(Entite e) {
        boolean alive = true;

        if (e instanceof Heros) {
            if (ArriveeGame.get().activeItems.get("pi") == null) ((Heros) e).decreasePDV();
            ArriveeGame.get().destroy(this);
            alive = false;
        } else if (e instanceof Item) {
            ArriveeGame.get().destroy((Item)e);
            alive = true;
        } else if (e instanceof Signe) {
            // temp
            alive = false;
        }

        return alive;
    }

    public abstract void act();


    /**********
     * STATIC *
     **********/

    public static final int EGAL = 0;
    public static final int ADD = 1;
    public static final int DIV = 2;
    public static final int MULT = 3;
    public static final int SOUST = 4;
    public static final int NB_TYPE = 5;

    private static int[] max;
    private static int[] pop;

    public static void init() {
        max = new int[NB_TYPE];
        pop = new int[NB_TYPE];
        for (int i=0; i < pop.length; i++) {
            pop[i] = 0;
        }
        max[EGAL] = 0;
        max[ADD] = 0;
        max[SOUST] = 0;
        max[MULT] = 0;
        max[DIV] = 0;
    }

    public static boolean popFull(int signeNum) {
        return pop[signeNum] == max[signeNum];
    }

    public static boolean popFull() {
        boolean res = true;

        for (int i=0; i < NB_TYPE; i++) {
            res = res && popFull(i);
        }

        return res;
    }

    public static void increasePop(Signe s) {
        if (s instanceof Egal) pop[EGAL]++;
        else if (s instanceof Add) pop[ADD]++;
        else if (s instanceof Soust) pop[SOUST]++;
        else if (s instanceof Mult) pop[MULT]++;
        else if (s instanceof Div) pop[DIV]++;
    }

    public static void decreasePop(Signe s) {
        if (s instanceof Egal) pop[EGAL]--;
        else if (s instanceof Add) pop[ADD]--;
        else if (s instanceof Soust) pop[SOUST]--;
        else if (s instanceof Mult) pop[MULT]--;
        else if (s instanceof Div) pop[DIV]--;
    }
}
