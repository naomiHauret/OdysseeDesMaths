package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.badlogic.gdx.math.MathUtils;

public class SigneFactory {

    private static final int EGAL = 0;
    private static final int ADD = 1;
    private static final int DIV = 2;
    private static final int MULT = 3;
    private static final int SOUST = 4;
    private static final int NB_SIGNE = 5;

    public static Signe makeSigne() {
        Signe signe;

        switch (MathUtils.random(NB_SIGNE-1)) {
        case ADD    : signe = new Add(); break;
        case DIV    : signe = new Div(); break;
        case MULT   : signe = new Mult(); break;
        case SOUST  : signe = new Soust(); break;
        default     : signe = new Egal(); break;
        }

        return signe;
    }
}
