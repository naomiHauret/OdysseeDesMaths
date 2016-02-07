package com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies;

import com.badlogic.gdx.math.MathUtils;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;
import com.odysseedesmaths.pathfinding.Pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Lost extends Enemy {

    private Case lastHeroPos;

    public Lost(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Lost(ArriveeRemarquable minigame) {
        super(minigame);
    }

    @Override
    public void act() {
        if (getMinigame().terrain.seeEachOther(getMinigame().hero.getCase(), getCase())) {
            LinkedList<Case> cheminVersHeros = Pathfinding.greedy(getMinigame().terrain, getCase(), getMinigame().hero.getCase());
            moveTo(cheminVersHeros.getFirst());
            lastHeroPos = cheminVersHeros.getLast();
        } else if (lastHeroPos != null) {
            moveTo(Pathfinding.greedy(getMinigame().terrain, getCase(), lastHeroPos).getFirst());
            if (lastHeroPos == getCase()) {
                lastHeroPos = null;
            }
        } else {
            List<Case> voisins = new ArrayList<Case>();
            voisins.addAll(getMinigame().terrain.getVoisins(getCase()));
            moveTo(voisins.get(MathUtils.random(voisins.size() - 1)));
        }
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = super.meet(e);

        if (e instanceof Enemy) {
            continuer = false;
            if (e instanceof Elite) {
                getMinigame().destroy(this);
                setAlive(false);
            }
        }

        return continuer && isAlive();
    }
}
