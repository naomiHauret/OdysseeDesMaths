package com.odysseedesmaths.minigames.coffeePlumbing;

import java.util.Vector;

/**
 * Created by trilunaire on 08/02/16.
 */
public class Tuyau {
    private boolean ouvert;
    private int capacite;
    private int fluxCourant;
    /**
     * Le vecteur de tuyaux contient seulement les voisins les plus proches
     */
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
        if(fluxCourant>0)
            this.fluxCourant--;
    }

    public Vector<Tuyau> getAllSuccessor(){
        Vector<Tuyau> allTuyaux = this.tuyauxSuivants; //de base on met nos successeurs
        //pour chacuns des tuyaux:
        if(!this.tuyauxSuivants.isEmpty()){
            Vector<Tuyau> newTuyaux;
            //pour tous les tuyaux suivant (déja présents dans tuyauxSuivants)
            for (int i = 0; i < tuyauxSuivants.size(); i++) {
                //on prends pour chacuns des tuyaux suivant leurs successeurs
                newTuyaux= tuyauxSuivants.get(i).getAllSuccessor();
                for(int j = 0; j<newTuyaux.size();j++){
                    allTuyaux.add(newTuyaux.get(j));
                }
            }
        }
        return allTuyaux;
    }

    public void addJunction(Tuyau jonction){
        this.tuyauxSuivants.add(jonction);
    }

    public boolean sousPression(){
        boolean b = false;
        if(fluxCourant-capacite > 0){
            b = true;
        }
        return b;
    }

    public String toString(){
        return "Flux courant: "+this.fluxCourant+"||Capacité: "+this.capacite+"||État: "+this.ouvert+"\n";
    }

}