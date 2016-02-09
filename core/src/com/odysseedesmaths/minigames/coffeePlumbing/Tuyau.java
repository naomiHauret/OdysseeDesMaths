package com.odysseedesmaths.minigames.coffeePlumbing;

import java.util.Vector;

/**
 * Created by trilunaire on 08/02/16.
 */
public class Tuyau {
    private boolean ouvert;
    private int capacite;
    private int fluxCourant;
    private Vector<Tuyau> tuyauxSuivants;

    public Tuyau(int capacite){
        this.ouvert= false; //fermé par defaut
        this.capacite = capacite;
        this.fluxCourant = 0; //si fermé rien dedans
        this.tuyauxSuivants = new Vector<Tuyau>();
    }

    public void augmenterFlux(){
        this.fluxCourant++;
    }

    public void diminuerFlux(){
        this.fluxCourant--;
    }

    public boolean sousPression(){
        boolean b = false;
        if(fluxCourant-capacite > 0){
            b = true;
        }
        return b;
    }

}
