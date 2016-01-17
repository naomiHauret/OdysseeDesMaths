package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Egal extends Signe {

    private LinkedList<Case> piste;

    public Egal(Case c) {
        super(c);
        piste = new LinkedList<Case>();
    }

    public Egal() {
        this(null);
    }

    @Override
    public void act() {
        Case cHeros = ArriveeGame.get().heros.getCase();

        if (piste.isEmpty() || ArriveeGame.get().terrain.heuristic(getCase(), cHeros) > 15) {
            piste = Pathfinding.astar(ArriveeGame.get().terrain, getCase(), cHeros);
        } else {
            piste.addLast(cHeros);
        }

        moveTo(piste.getFirst());
        piste.removeFirst();
    }
}
