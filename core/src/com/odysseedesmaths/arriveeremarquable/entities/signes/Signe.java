package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entite;
import com.odysseedesmaths.arriveeremarquable.entities.Heros;
import com.odysseedesmaths.arriveeremarquable.entities.Personnage;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public abstract class Signe extends Personnage {

    public Signe(Case c) {
        super(c);
    }

    @Override
    public void meet(Entite e) {
        if (e instanceof Heros) ArriveeGame.get().heros.meet(this);
        else if (e instanceof Item) ArriveeGame.get().destroy((Item)e);
    }

    public abstract void act();
}
