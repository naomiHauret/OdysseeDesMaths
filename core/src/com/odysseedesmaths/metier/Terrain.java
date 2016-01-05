package com.odysseedesmaths.metier;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Allan on 03/01/2016.
 */
public class Terrain {

    private static Terrain terrain = null;

    public static final int HAUTEUR = 20;
    public static final int LARGEUR = HAUTEUR * 2;
    private Case[][] cases;

    private Terrain() {
        this.cases = new Case[HAUTEUR][LARGEUR];
    }

    private static void create() {
        terrain = new Terrain();

        int n = 0;
        for (int i=0; i<HAUTEUR; i++) {
            for (int j=0; j<LARGEUR; j++) {
                terrain.cases[i][j] = new Case(i, j, (n%3 != 0));
                n++;
            }
        }
    }

    public Case[][] getCases() {
        return cases;
    }

    public static Terrain get() {
        if (terrain == null) {
            create();
        }
        return terrain;
    }

    public Case getDepart() {
        return cases[10][20];
    }
}
