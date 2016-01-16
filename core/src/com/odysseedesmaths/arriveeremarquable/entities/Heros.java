package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Signe;
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
            if (ArriveeGame.get().activeItems.get("pi") == null) decreasePDV();
            ArriveeGame.get().destroy((Signe)e);
        } else if (e instanceof Item) {
            ((Item)e).trigger();
        }
    }
}
