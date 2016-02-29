package com.odysseedesmaths.minigames.coffeePlumbing.map;

public class Case {
    public final int posX;
    public final int posY;
    private final boolean obstacle;

    public Case(int posX, int posY, boolean obstacle) {
        this.posX = posX;
        this.posY = posY;
        this.obstacle = obstacle;
    }

    public boolean isObstacle() {
        return obstacle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Case aCase = (Case) o;

        if (posX != aCase.posX) return false;
        return posY == aCase.posY;

    }

    @Override
    public int hashCode() {
        int result = posX;
        result = 31 * result + posY;
        return result;
    }
}
