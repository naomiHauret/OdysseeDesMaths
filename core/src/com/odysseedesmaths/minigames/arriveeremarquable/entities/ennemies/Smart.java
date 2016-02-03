package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Smart extends Elite {

    public Smart(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Smart(ArriveeRemarquable minigame) {
        super(minigame);
    }

    @Override
    public void act() {
        LinkedList<Case> cheminVersHeros = Pathfinding.astar(getMinigame().terrain, getCase(), getMinigame().hero.getCase());
        moveTo(cheminVersHeros.getFirst());
    }
}
