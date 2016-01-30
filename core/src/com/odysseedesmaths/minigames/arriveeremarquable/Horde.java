package com.odysseedesmaths.minigames.arriveeremarquable;

public class Horde {

    public static final int FAST = 2;
    public static final int NORMAL = 3;
    public static final int SLOW = 4;

    private int front;
    private int vitesse;
    private int count;

    public Horde(int vitesse) {
        front = -1;
        this.vitesse = vitesse;
        count = 0;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int newFront) {
        front = newFront;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void act() {
        count++;
        if ((count%vitesse == 0) && (front < ArriveeRemarquable.get().terrain.getWidth())) {
            front++;
        }
    }
}
