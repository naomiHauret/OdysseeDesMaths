package com.odysseedesmaths.minigames.arriveeremarquable.map;

import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;

public class Case {
    public final int i;
    public final int j;
    private final boolean obstacle;
    private Entity entity;

    public Case(int i, int j, boolean obstacle) {
        this.i = i;
        this.j = j;
        this.obstacle = obstacle;
        this.entity = null;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void free() {
        setEntity(null);
    }

    public boolean isTaken() {
        return entity != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Case aCase = (Case) o;

        if (i != aCase.i) return false;
        return j == aCase.j;

    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }
}
