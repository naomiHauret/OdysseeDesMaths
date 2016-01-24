package com.odysseedesmaths.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Sticky extends Elite {

    private LinkedList<Case> piste;

    public Sticky(Case c) {
        super(c);
        piste = new LinkedList<Case>();
    }

    public Sticky() {
        this(null);
    }

    @Override
    public void act() {
        Case cHeros = ArriveeGame.get().hero.getCase();

        if (piste.isEmpty() || ArriveeGame.get().terrain.heuristic(getCase(), cHeros) > 10) {
            piste = Pathfinding.astar(ArriveeGame.get().terrain, getCase(), cHeros);
        } else {
            piste.addLast(cHeros);
        }

        moveTo(piste.getFirst());
        piste.removeFirst();
    }
}
