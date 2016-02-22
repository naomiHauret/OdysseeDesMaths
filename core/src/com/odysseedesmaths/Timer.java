package com.odysseedesmaths;

import java.util.Observable;
import java.util.TimerTask;

/**
 * Implémentation d'un timer avec minutes et secondes décroissant.
 */
public class Timer extends Observable {

    public static final int ONE_MINUTE = 60;
    public static final int ONE_SECOND = 1;

    private java.util.Timer taskScheduler;
    private int initialTime;
    private int secondsLeft;
    private int delayForOneSecond;

    /**
     * Crée un nouveau timer avec le temps initial et la vitesse d'écoulement spécifiés.
     *
     * @param aTime  Le temps initial
     * @param aSpeed La vitesse d'écoulement
     */
    public Timer(int aTime, Speed aSpeed) {
        init(aTime);
        setSpeed(aSpeed);
    }

    /**
     * Crée un nouveau timer avec le temps initial spécifié.
     * La vitesse d'écoulement est "NORMAL" (x1).
     *
     * @param aTime Le temps initial
     */
    public Timer(int aTime) {
        this(aTime, Speed.NORMAL);
    }

    /**
     * Retourne le nombre actuel de minutes du timer.
     *
     * @return Le nombre de minutes
     */
    public int getMinutes() {
        return secondsLeft/60;
    }

    /**
     * Retourne le nombre actuel de secondes du timer.
     *
     * @return Le nombre de secondes
     */
    public int getSeconds() {
        return secondsLeft%60;
    }

    /**
     * Retourne le nombre total de secondes restantes au timer.
     *
     * @return Le nombre de secondes
     */
    public int getSecondsLeft() {
        return secondsLeft;
    }

    /**
     * Règle la vitesse d'écoulement du timer à la vitesse spécifiée.
     *
     * @param aSpeed La nouvelle vitesse
     */
    public void setSpeed(Speed aSpeed) {
        switch (aSpeed) {
            case VERY_HIGH: // Vitesse x3
                this.delayForOneSecond = 1000 / 3;
                break;
            case HIGH:      // Vitesse x2
                this.delayForOneSecond = 1000 / 2;
                break;
            case NORMAL:    // Vitesse x1
                this.delayForOneSecond = 1000;
                break;
            case LOW:       // Vitesse x0.5
                this.delayForOneSecond = 1000 * 2;
                break;
            case VERY_LOW:  // Vitesse x0.33
                this.delayForOneSecond = 1000 * 3;
                break;
            default:
                // Erreur vitesse
                break;
        }
        if (taskScheduler != null) {
            refresh();
        }
    }

    /**
     * Retourne le timer sous la forme d'une chaîne de caractères String.
     * Format : "minutes:secondes".
     *
     * @return La chaîne de caractère représentant le timer
     */
    @Override
    public String toString() {
        String minutes = String.format("%02d", getMinutes());
        String seconds = String.format("%02d", getSeconds());
        return minutes+":"+seconds;
    }

    /**
     * Met le timer en marche.
     */
    public void start() {
        if (taskScheduler == null && !isFinished()) {
            taskScheduler = new java.util.Timer();
            taskScheduler.scheduleAtFixedRate(new UpdateTask(), delayForOneSecond, delayForOneSecond);
        }
    }

    /**
     * Met le timer en pause.
     */
    public void stop() {
        if (taskScheduler != null) {
            taskScheduler.cancel();
            taskScheduler = null;
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
     * @param aTime Le nouveau temps initial
     */
    public void reset(int aTime) {
        stop();
        init(aTime);
    }

    /**
     * Incrémente le timer du montant spécifié.
     *
     * @param aTime Montant à ajouter
     */
    public void add(int aTime) {
        secondsLeft += aTime;
    }

    /**
     * Décrémente le timer du montant spécifié.
     * Si le temps est écoulé, le timer est arrêté.
     *
     * @param aTime Montant à enlever
     */
    public void remove(int aTime) {
        secondsLeft -= aTime;
        if (secondsLeft < 0) {
            secondsLeft = 0;
            stop();
        }
    }

    public enum Speed {
        VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW
    }

    /**
     * Initialise le temps initial du timer à la valeur spécifiée
     * et le positionne à cette valeur.
     *
     * @param aTime Le temps initial
     */
    private void init(int aTime) {
        initialTime = aTime;
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
            setChanged();
            notifyObservers();
            if (secondsLeft == 0) stop();
        }
    }
}