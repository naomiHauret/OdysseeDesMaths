package com.odysseedesmaths.metier;

/**
 * Created by Allan on 03/01/2016.
 */
public abstract class Personnage extends Entite{

    public Personnage(Case c) {
        super(c);
    }

    public void move(Case c) {

    }

    public abstract void meet(Entite e);
}
