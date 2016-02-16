package com.odysseedesmaths.minigames.coffeePlumbing;

import java.util.HashSet;
import java.util.Iterator;

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
    private HashSet<Tuyau> tuyauxSuivants;

    public Tuyau(int capacite){
        this.ouvert= false; //fermé par defaut
        this.capacite = capacite;
        this.fluxCourant = 0; //si fermé rien dedans
        this.tuyauxSuivants = new HashSet<Tuyau>();
    }

    public void augmenterFlux(){
        this.fluxCourant++;
    }

    public void diminuerFlux(){
        if(fluxCourant>0)
            this.fluxCourant--;
    }

    public HashSet<Tuyau> getAllSuccessor(){
        //FIXME: Fonction lorsqu'un tuyau à un successeur direct, mais donne une exception lorsqu'il en a plusieurs
        HashSet<Tuyau> allTuyaux = this.tuyauxSuivants; //de base on met nos successeurs
        //pour chacuns des tuyaux:
        if(!this.tuyauxSuivants.isEmpty()){
            HashSet<Tuyau> newTuyaux;
            Iterator<Tuyau> it,itbis;

            it = tuyauxSuivants.iterator();
            while(it.hasNext()){
                newTuyaux= ((Tuyau)it.next()).getAllSuccessor(); //on prends pour chacuns des tuyaux suivant leurs successeurs
                System.out.print(this.toString());
                itbis = newTuyaux.iterator(); //pour tous les tuyaux qu'on a trouvé
                while(itbis.hasNext()){//on ajoute dans notre tableau contenant tous les tuyaux
                    allTuyaux.add(itbis.next());
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
