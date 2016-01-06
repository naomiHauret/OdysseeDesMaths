package com.odysseedesmaths.arriveeremarquable;

/**
 * Created by Allan on 03/01/2016.
 */
public abstract class Signe extends Personnage {

    public Signe(Case c) {
        super(c);
    }

    @Override
    public void meet(Entite e) {

    }

    public abstract void move();
}
