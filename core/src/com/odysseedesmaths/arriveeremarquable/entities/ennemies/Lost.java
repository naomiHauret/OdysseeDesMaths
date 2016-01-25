package com.odysseedesmaths.arriveeremarquable.entities.ennemies;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.ForetScreen;
import com.odysseedesmaths.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Lost extends Enemy {

    private Case lastHeroPos;

    @Override
    public void act() {
        if (ForetScreen.isInHeroSight(getCase())) {
            LinkedList<Case> cheminVersHeros = Pathfinding.greedy(ArriveeGame.get().terrain, getCase(), ArriveeGame.get().hero.getCase());
            moveTo(cheminVersHeros.getFirst());
            lastHeroPos = cheminVersHeros.getLast();
        } else if (lastHeroPos != null) {
            moveTo(Pathfinding.greedy(ArriveeGame.get().terrain, getCase(), lastHeroPos).getFirst());
            if (lastHeroPos == getCase()) lastHeroPos = null;
        } else {
            List<Case> voisins = new ArrayList<Case>();
            voisins.addAll(ArriveeGame.get().terrain.getVoisins(getCase()));
            moveTo(voisins.get(MathUtils.random(voisins.size() - 1)));
        }
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Enemy) {
            continuer = false;
            if (e instanceof Elite) {
                ArriveeGame.get().destroy(this);
                setAlive(false);
            }
        }

        return continuer && isAlive();
    }
}
