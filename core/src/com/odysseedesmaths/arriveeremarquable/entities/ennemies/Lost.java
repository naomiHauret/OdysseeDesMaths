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

    @Override
    public void act() {
        if (ForetScreen.isInHeroSight(getCase())) {
            Case cHero = ArriveeGame.get().hero.getCase();
            LinkedList<Case> cheminVersHeros = Pathfinding.greedy(ArriveeGame.get().terrain, getCase(), cHero);
            moveTo(cheminVersHeros.getFirst());
        } else {
            List<Case> voisins = new ArrayList<Case>();
            voisins.addAll(ArriveeGame.get().terrain.getVoisins(getCase()));
            moveTo(voisins.get(MathUtils.random(voisins.size() - 1)));
        }
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Greed) {
            ArriveeGame.get().destroy(this);
            ArriveeGame.get().destroy((Enemy) e);
            setAlive(false);
        }

        return continuer && isAlive();
    }
}
