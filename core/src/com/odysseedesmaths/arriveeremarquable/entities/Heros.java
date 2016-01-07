package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

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
        // TODO : Gérer la rencontre du héros avec une autre entite
        // instanceof ?
    }

    public void increasePDV() {
        pdv++;
    }

    public void decreasePDV() {
        pdv--;
    }
}
