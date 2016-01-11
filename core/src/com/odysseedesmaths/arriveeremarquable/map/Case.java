package com.odysseedesmaths.arriveeremarquable.map;

import com.odysseedesmaths.arriveeremarquable.entities.Entite;

public class Case {
    public final int i;
    public final int j;
    private final boolean obstacle;
    private Entite entite;

    public Case(int i, int j, boolean obstacle) {
        this.i = i;
        this.j = j;
        this.obstacle = obstacle;
        this.entite = null;
    }

    public Case(int i, int j) {
        this(i, j, true);
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public Entite getEntite() {
        return entite;
    }

    public void setEntite(Entite entite) {
        this.entite = entite;
    }

    public boolean isTaken() {
        return (entite != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
