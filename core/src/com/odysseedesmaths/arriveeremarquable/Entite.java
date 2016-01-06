package com.odysseedesmaths.arriveeremarquable;

/**
 * Created by Allan on 03/01/2016.
 */
public abstract class Entite {
    private Case maCase;

    public Entite(Case c) {
        this.maCase = c;
    }

    public Case getCase() {
        return maCase;
    }
}
