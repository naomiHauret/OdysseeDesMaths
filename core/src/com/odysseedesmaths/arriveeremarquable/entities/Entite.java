package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Entite {
    private Case maCase;

    public Entite(Case c) {
        this.maCase = c;
    }

    public Case getCase() {
        return maCase;
    }

    public void setCase(Case c) {
        maCase = c;
    }
}