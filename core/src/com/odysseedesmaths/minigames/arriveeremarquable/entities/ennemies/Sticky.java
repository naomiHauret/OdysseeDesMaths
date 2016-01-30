package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
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
        Case cHeros = ArriveeRemarquable.get().hero.getCase();

        if (piste.isEmpty() || ArriveeRemarquable.get().terrain.heuristic(getCase(), cHeros) > 10) {
            piste = Pathfinding.astar(ArriveeRemarquable.get().terrain, getCase(), cHeros);
        } else {
            piste.addLast(cHeros);
        }

        moveTo(piste.getFirst());
        piste.removeFirst();
    }
}
