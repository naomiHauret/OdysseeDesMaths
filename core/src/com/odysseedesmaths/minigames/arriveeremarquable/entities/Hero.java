package com.odysseedesmaths.minigames.arriveeremarquable.entities;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public class Hero extends Character {
    public static final int PDV_MAX = 5;

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
        if (pdv <= 0) ArriveeRemarquable.get().gameOver();
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = true;

        if (e instanceof com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Enemy) {
            if (ArriveeRemarquable.get().activeItems.get(com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Shield.class) == null) {
                decreasePDV();
            }
            ArriveeRemarquable.get().destroy((com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Enemy)e);
        } else if (e instanceof com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Item) {
            ((com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Item)e).trigger();
        }

        return continuer;
    }

    private void move(int di, int dj) {
        Case cible = ArriveeRemarquable.get().terrain.getCases()[getCase().i + di][getCase().j + dj];
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
