package com.odysseedesmaths.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Greed extends Elite {

    @Override
    public void act() {
        LinkedList<Case> cheminVersHeros = Pathfinding.greedy(ArriveeGame.get().terrain, getCase(), ArriveeGame.get().hero.getCase());
        moveTo(cheminVersHeros.getFirst());
    }
}
