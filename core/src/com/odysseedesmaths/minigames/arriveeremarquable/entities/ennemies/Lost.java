package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Lost extends Enemy {

    private Case lastHeroPos;

    @Override
    public void act() {
        if (com.odysseedesmaths.minigames.arriveeremarquable.ForetScreen.isInHeroSight(getCase())) {
            LinkedList<Case> cheminVersHeros = Pathfinding.greedy(ArriveeRemarquable.get().terrain, getCase(), ArriveeRemarquable.get().hero.getCase());
            moveTo(cheminVersHeros.getFirst());
            lastHeroPos = cheminVersHeros.getLast();
        } else if (lastHeroPos != null) {
            moveTo(Pathfinding.greedy(ArriveeRemarquable.get().terrain, getCase(), lastHeroPos).getFirst());
            if (lastHeroPos == getCase()) {
                lastHeroPos = null;
            }
        } else {
            List<Case> voisins = new ArrayList<Case>();
            voisins.addAll(ArriveeRemarquable.get().terrain.getVoisins(getCase()));
            moveTo(voisins.get(MathUtils.random(voisins.size() - 1)));
        }
    }

    @Override
    public boolean meet(com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Enemy) {
            continuer = false;
            if (e instanceof Elite) {
                ArriveeRemarquable.get().destroy(this);
                setAlive(false);
            }
        }

        return continuer && isAlive();
    }
}
