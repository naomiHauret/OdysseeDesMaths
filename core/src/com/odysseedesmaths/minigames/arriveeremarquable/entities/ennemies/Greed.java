package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Greed extends Elite {

    @Override
    public void act() {
        LinkedList<Case> cheminVersHeros = Pathfinding.greedy(ArriveeRemarquable.get().terrain, getCase(), ArriveeRemarquable.get().hero.getCase());
        moveTo(cheminVersHeros.getFirst());
    }
}
