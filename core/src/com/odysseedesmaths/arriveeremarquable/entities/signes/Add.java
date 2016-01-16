package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entite;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Add extends Signe {

    public Add(Case c) {
        super(c);
    }

    @Override
    public void act() {
        LinkedList<Case> cheminVersObjectif = Pathfinding.astar(ArriveeGame.get().terrain, getCase(), ArriveeGame.get().heros.getCase());
        moveTo(cheminVersObjectif.getFirst());
    }

    @Override
    public void meet(Entite e) {
        super.meet(e);
        if (e instanceof Soust) {
            ArriveeGame.get().destroy(this);
            ArriveeGame.get().destroy((Soust)e);
        }
    }
}
