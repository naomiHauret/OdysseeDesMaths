package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.entities.enemies.Signe;
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

    public void increasePDV() {
        pdv++;
    }

    public void decreasePDV() {
        pdv--;
    }

    @Override
    public void meet(Entite e) {
        if (e instanceof Signe) {
            decreasePDV();
        }
    }
}
