package com.odysseedesmaths.arriveeremarquable.entities;

import com.odysseedesmaths.arriveeremarquable.ArriveeGame;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Signe;
import com.odysseedesmaths.arriveeremarquable.map.Case;

public class Heros extends Personnage {
    public final int PDV_MAX = 5;

    private int pdv;

    public Heros(Case c) {
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
    public void meet(Entite e) {
        if (e instanceof Signe) {
            if (ArriveeGame.get().activeItems.get("pi") == null) decreasePDV();
            ArriveeGame.get().destroy((Signe)e);
        } else if (e instanceof Item) {
            ((Item)e).trigger();
        }
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
