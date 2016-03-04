package com.odysseedesmaths.minigames.coffeePlumbing.map;

import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;

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
    private HashSet<int[]> cases;

    public Tuyau(int capacite){
        this.ouvert= false; //fermé par defaut
        this.capacite = capacite;
        this.fluxCourant = 0; //si fermé rien dedans
        this.tuyauxSuivants = new HashSet<Tuyau>();
        this.cases = new HashSet<int[]>();
    }

    public void augmenterFlux(){
        this.fluxCourant++;
    }

    public void diminuerFlux(){
        if(fluxCourant>0)
            this.fluxCourant--;
    }

    public HashSet<Tuyau> getAllSuccessor(){
        HashSet<Tuyau> allSuccessors = (HashSet<Tuyau>) this.tuyauxSuivants.clone(); //de base on met nos successeurs (clone de l'objet (sinon problème au niveau de l'itérateur))
        //pour chacuns des tuyaux:
        if(!this.tuyauxSuivants.isEmpty()){
            HashSet<Tuyau> succ;
            Iterator<Tuyau> it,itbis;

            it = tuyauxSuivants.iterator();
            while(it.hasNext()){
                succ= ((Tuyau)it.next()).getAllSuccessor(); //on prend pour chacuns des tuyaux suivant leurs successeurs
                itbis = succ.iterator(); //pour tous les tuyaux qu'on a trouvé
                while(itbis.hasNext()){//on ajoute dans notre tableau contenant tous les tuyaux
                    allSuccessors.add(itbis.next());
                }
                succ.clear();
                itbis=null;
            }
        }
        return allSuccessors;
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
        String posCases = "";
        Iterator<int[]> it = cases.iterator();
        int[] posTmp = new int[2];

        while(it.hasNext()){
            posTmp = it.next();
            posCases+="x="+posTmp[0]+"|| y="+posTmp[1]+"\n";
        }

        return posCases+"Flux courant: "+this.fluxCourant+"||Capacité: "+this.capacite+"||État: "+this.ouvert+"\n";
    }

    /**
    * Getter of cases
    * @return the value of cases
    */
    public HashSet<int[]> get_cases(){
      return this.cases;
    }

    /**
    * Setter of cases
    * @param new_cases: the new value of cases
    */
    public void set_cases(HashSet<int[]> new_cases){
      this.cases = new_cases;
    }

    public void addCase(int[] new_case){
        this.cases.add(new_case);
    }

    public void removeCase(int[] old_case){
        this.cases.remove(old_case);
    }
}
