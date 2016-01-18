package com.odysseedesmaths;

import java.util.TimerTask;

public class Timer {

    private java.util.Timer timer;
    private int secondsLeft;

    public Timer(int seconds) {
        secondsLeft = seconds;
    }

    public String toString() {
        return secondsLeft/60 + ":" + secondsLeft%60;
    }

    public void start() {
        if (timer == null) {
            timer = new java.util.Timer();
            timer.scheduleAtFixedRate(new Update(), 1000, 1000);
        }
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public boolean isFinished() {
        return secondsLeft == 0;
    }

    public void add(int seconds) {
        secondsLeft += seconds;
    }

    private class Update extends TimerTask {
        public void run() {
            secondsLeft--;
            if (secondsLeft == 0) stop();
        }
    }
}