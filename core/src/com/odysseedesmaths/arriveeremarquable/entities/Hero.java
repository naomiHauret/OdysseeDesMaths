package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.items.Shield;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public class Hero extends Character {
    public final int PDV_MAX = 5;

    private int pdv;

    public Hero(Case c) {
        super(c);
        pdv = PDV_MAX;
    }

    public int getPdv() {
        return pdv;
    }

    public void increasePDV() {
        if (pdv < PDV_MAX) pdv++;
    }

    public void decreasePDV() {
        pdv--;
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = true;

        if (e instanceof Enemy) {
            if (ArriveeGame.get().activeItems.get(Shield.class) == null) decreasePDV();
            ArriveeGame.get().destroy((Enemy)e);
            continuer = pdv > 0;
        } else if (e instanceof Item) {
            ((Item)e).trigger();
            ArriveeGame.get().destroy(((Item)e));
            continuer = true;
        }

        return continuer;
    }

    private void move(int di, int dj) {
        Case cible = ArriveeGame.get().terrain.getCases()[getCase().i + di][getCase().j + dj];
        if (!cible.isObstacle()) moveTo(cible);
    }

    public void moveLeft() {
        move(-1, 0);
    }

    public void moveRight() {
        move(1, 0);
    }

    public void moveUp() {
        move(0, 1);
    }

    public void moveDown() {
        move(0, -1);
    }
}
