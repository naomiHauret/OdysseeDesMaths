package com.odysseedesmaths.metier;

/**
 * Created by Allan on 03/01/2016.
 */
public class Heros extends Personnage {
    private int pdv;

    public Heros(Case c, int pdv) {
        super(c);
        this.pdv = pdv;
    }

    public int getPdv() {
        return pdv;
    }

    @Override
    public void meet(Entite e) {

    }

    public void increasePDV() {
        pdv++;
    }

    public void decreasePDV() {
        pdv--;
    }
}
