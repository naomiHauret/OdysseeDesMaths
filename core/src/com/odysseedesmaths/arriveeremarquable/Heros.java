package com.odysseedesmaths.arriveeremarquable;

public class Heros extends Personnage {
    private int pdv;

    public Heros(com.odysseedesmaths.arriveeremarquable.Case c, int pdv) {
        super(c);
        this.pdv = pdv;
    }

    public int getPdv() {
        return pdv;
    }

    @Override
    public void meet(com.odysseedesmaths.arriveeremarquable.Entite e) {

    }

    public void increasePDV() {
        pdv++;
    }

    public void decreasePDV() {
        pdv--;
    }
}
