package com.odysseedesmaths.arriveeremarquable.entities.signes;

import com.badlogic.gdx.math.MathUtils;

public class SigneFactory {

    public static Signe makeSigne() {
        Signe signe;
        int signeNum;
        do {
            signeNum = MathUtils.random(Signe.NB_TYPE-1);
        } while (Signe.popFull(signeNum));

        switch (signeNum) {
        case Signe.ADD    : signe = new Add(); break;
        case Signe.DIV    : signe = new Div(); break;
        case Signe.MULT   : signe = new Mult(); break;
        case Signe.SOUST  : signe = new Soust(); break;
        default           : signe = new Egal();
        }

        Signe.increasePop(signe);

        return signe;
    }
}
