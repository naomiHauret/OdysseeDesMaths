package com.odysseedesmaths.minigames.arriveeremarquable.entities;

import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Shield;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

public class Hero extends Character {
    public static final int PDV_MAX = 5;

    private int pdv = PDV_MAX;

    public Hero(ArriveeRemarquable minigame, Case c) {
        super(minigame, c);
    }

    public Hero(ArriveeRemarquable minigame) {
        super(minigame);
    }

    public int getPdv() {
        return pdv;
    }

    public void increasePDV() {
        if (pdv < PDV_MAX) pdv++;
    }

    public void decreasePDV() {
        pdv--;
        if (pdv <= 0) getMinigame().gameOver();
    }

    @Override
    public boolean meet(Entity e) {
        boolean continuer = true;

        if (e instanceof Enemy) {
            if (getMinigame().activeItems.get(Shield.class) == null) {
                decreasePDV();
            }
            getMinigame().destroy((Enemy)e);
        } else if (e instanceof Item) {
            ((Item)e).trigger();
        }

        return continuer;
    }

    private void move(int di, int dj) {
        Case cible = getMinigame().terrain.getCases()[getCase().i + di][getCase().j + dj];
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
