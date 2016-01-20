package com.odysseedesmaths;

import java.util.TimerTask;

/**
 * Implémentation d'un timer avec minutes et secondes décroissant.
 */
public class Timer {

    public static final int ONE_MINUTE = 60;
    public static final int ONE_SECOND = 1;

    private java.util.Timer timer;
    private int initialTime;
    private int secondsLeft;
    private int speed;

    /**
     * Crée un nouveau timer avec le temps initial et la vitesse d'écoulement spécifiés.
     *
     * @param time  Le temps initial
     * @param speed La vitesse d'écoulement
     */
    public Timer(int time, Speed speed) {
        init(time);
        setSpeed(speed);
    }

    /**
     * Crée un nouveau timer avec le temps initial spécifié.
     * La vitesse d'écoulement est "NORMAL" (x1).
     *
     * @param time Le temps initial
     */
    public Timer(int time) {
        this(time, Speed.NORMAL);
    }

    public int getMinutes() {
        return secondsLeft/60;
    }

    public int getSeconds() {
        return secondsLeft%60;
    }

    /**
     * Règle la vitesse d'écoulement du timer à la vitesse spécifiée.
     *
     * @param speed La nouvelle vitesse
     */
    public void setSpeed(Speed speed) {
        switch (speed) {
            case VERY_HIGH: // Vitesse x3
                this.speed = 1000 / 3;
                break;
            case HIGH:      // Vitesse x2
                this.speed = 1000 / 2;
                break;
            case NORMAL:    // Vitesse x1
                this.speed = 1000;
                break;
            case LOW:       // Vitesse x0.5
                this.speed = 1000 * 2;
                break;
            case VERY_LOW:  // Vitesse x0.33
                this.speed = 1000 * 3;
                break;
        }
        refresh();
    }

    /**
     * Retourne le timer sous la forme d'une chaîne de caractères String.
     * Format : "minutes:secondes"
     *
     * @return La chaîne de caractère représentant le timer
     */
    public String toString() {
        String minutes = String.format("%02d", getMinutes());
        String seconds = String.format("%02d", getSeconds());
        return minutes+":"+seconds;
    }

    /**
     * Met le timer en marche.
     */
    public void start() {
        if (timer == null) {
            timer = new java.util.Timer();
            timer.scheduleAtFixedRate(new UpdateTask(), speed, speed);
        }
    }

    /**
     * Met le timer en pause.
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Retourne vrai si le timer est écoulé, faux sinon.
     *
     * @return Vrai si le timer est écoulé, faux sinon.
     */
    public boolean isFinished() {
        return secondsLeft == 0;
    }

    /**
     * Arrête et repositionne le timer à son temps initial.
     */
    public void reset() {
        reset(initialTime);
    }

    /**
     * Arrête le timer, réinitialise son temps initial à la valeur spécifiée
     * et le repositionne à cette valeur.
     *
     * @param time Le nouveau temps initial
     */
    public void reset(int time) {
        stop();
        init(time);
    }

    /**
     * Incrémente le timer du montant spécifié.
     *
     * @param time Montant à ajouter
     */
    public void add(int time) {
        secondsLeft += time;
    }

    /**
     * Décrémente le timer du montant spécifié.
     * Si le temps est écoulé, le timer est arrêté.
     *
     * @param time Montant à enlever
     */
    public void remove(int time) {
        secondsLeft -= time;
        if (secondsLeft < 0) {
            secondsLeft = 0;
            stop();
        }
    }

    /**
     * Initialise le temps initial du timer à la valeur spécifiée
     * et le positionne à cette valeur.
     *
     * @param time Le temps initial
     */
    private void init(int time) {
        initialTime = time;
        secondsLeft = initialTime;
    }

    /**
     * Rafraichit le timer.
     * Nécessaire pour mettre à jour sa vitesse.
     */
    private void refresh() {
        stop();
        start();
    }

    /**
     * Classe comportant la méthode à exécuter périodiquement par le timer.
     */
    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            secondsLeft--;
            if (secondsLeft == 0) {
                stop();
            }
        }
    }

    private enum Speed {
        VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW
    }
}