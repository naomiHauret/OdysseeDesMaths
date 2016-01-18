package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entite;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Mult extends Signe {

    @Override
    public void act() {
        Case cHero = ArriveeGame.get().heros.getCase();
        LinkedList<Case> cheminVersHeros = Pathfinding.astar(ArriveeGame.get().terrain, getCase(), cHero);
        moveTo(cheminVersHeros.getFirst());
    }

    @Override
    public boolean meet(Entite e) {
        boolean continuer = super.meet(e);

        if (e instanceof Div) {
            ArriveeGame.get().destroy(this);
            ArriveeGame.get().destroy((Signe) e);
            setAlive(false);
        }

        return continuer && isAlive();
    }
}
