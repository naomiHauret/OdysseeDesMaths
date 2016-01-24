package com.odysseedesmaths.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Smart extends Enemy {

    @Override
    public void act() {
        Case cHero = ArriveeGame.get().hero.getCase();
        LinkedList<Case> cheminVersHeros = Pathfinding.astar(ArriveeGame.get().terrain, getCase(), cHero);
        moveTo(cheminVersHeros.getFirst());
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof SuperSmart) {
            ArriveeGame.get().destroy(this);
            ArriveeGame.get().destroy((Enemy) e);
            setAlive(false);
        }

        return continuer && isAlive();
    }
}
