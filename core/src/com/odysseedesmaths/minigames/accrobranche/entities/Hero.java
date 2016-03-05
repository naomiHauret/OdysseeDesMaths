package com.odysseedesmaths.minigames.accrobranche.entities;

import com.odysseedesmaths.minigames.accrobranche.Accrobranche;

public class Hero extends Character {
    public static final int PDV_MAX = 5;

    private Accrobranche jeu;

    private int pdv = PDV_MAX;

    public Hero(Accrobranche minigame) {
        super(minigame);
        jeu = minigame;
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

        if (e instanceof Gland) {

        }

        return continuer;
    }

    private void move(int di, int dj) {
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

    public Accrobranche getMinigame() {
        return jeu;
    }
}