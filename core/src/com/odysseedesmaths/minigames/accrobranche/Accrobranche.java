package com.odysseedesmaths.minigames.accrobranche;

import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.minigames.MiniGame;

public class Accrobranche extends MiniGame {

    public static final int TIME_LIMIT = 3;

    public Timer timer;

    public Accrobranche(OdysseeDesMaths game) {
        super(game);
        init();
        setScreen(new TreeScreen(this));
    }

    public void init() {
        timer = new Timer(TIME_LIMIT * Timer.ONE_MINUTE);
        setState(State.RUNNING);
    }

    public void pauseGame() {
        setState(State.PAUSED);
        timer.stop();
    }

    public void returnToGame() {
        setState(State.RUNNING);
        timer.start();
    }

    public void restartGame() {
        getGame().setScreen(new Accrobranche(getGame()));
    }

    public void gameOver() {
        setState(State.GAME_OVER);
        timer.stop();
        ((TreeScreen)currentScreen).gameOver();
    }
}
