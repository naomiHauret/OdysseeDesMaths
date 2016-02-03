package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Sticky extends Elite {

    private LinkedList<Case> piste = new LinkedList<Case>();

    public Sticky(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Sticky(ArriveeRemarquable minigame) {
        super(minigame);
    }

    @Override
    public void act() {
        Case cHeros = getMinigame().hero.getCase();

        if (piste.isEmpty() || getMinigame().terrain.heuristic(getCase(), cHeros) > 10) {
            piste = Pathfinding.astar(getMinigame().terrain, getCase(), cHeros);
        } else {
            piste.addLast(cHeros);
        }

        moveTo(piste.getFirst());
        piste.removeFirst();
    }
}
