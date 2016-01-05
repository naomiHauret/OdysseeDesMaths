package com.odysseedesmaths.metier;

/**
 * Created by Allan on 03/01/2016.
 */
public class Case {
    private int i;
    private int j;
    private boolean libre;

    public Case(int i, int j, boolean l) {
        this.i = i;
        this.j = j;
        this.libre = l;
    }

    public Case(int i, int j) {
        this(i, j, true);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isLibre() {
        return libre;
    }
}
