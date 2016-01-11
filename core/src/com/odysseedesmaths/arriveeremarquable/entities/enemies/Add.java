package com.odysseedesmaths.arriveeremarquable.entities.enemies;

import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.arriveeremarquable.map.Terrain;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.LinkedList;

public class Add extends Signe {

    public Add(Case c) {
        super(c);
    }

    @Override
    public void move() {
        //TODO: Donner l'accès à l'état du jeu aux entites (?)
    //    LinkedList<Case> cheminVersObjectif = Pathfinding.astar(Terrain.get(), getCase(), );
   //     moveTo(cheminVersObjectif.getFirst());
    }
}
