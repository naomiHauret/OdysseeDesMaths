package com.odysseedesmaths.minigames.coffeePlumbing.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.KoffeeMeter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by trilunaire on 08/02/16.
 */
public class Tuyau {
    private int capacite;
    private int fluxCourant;
    private int fluxEntrant; //TODO: réussir à partager le flux entrant entre les tuyaux
    /**
     * Le vecteur de tuyaux contient seulement les voisins les plus proches
     */
    private HashSet<Tuyau> tuyauxSuivants;
    private HashSet<int[]> cases;
    private KoffeeMeter indicateurs;
    private List<Sprite> koffeeInPipe; //TODO: find a more realistic way to represent the level of coffee in the pipe.


    public Tuyau(int capacite){
        this.capacite = capacite;
        this.fluxCourant = 0; //si fermé rien dedans
        this.fluxEntrant = 0;
        this.tuyauxSuivants = new HashSet<Tuyau>();
        this.cases = new HashSet<int[]>();
        this.koffeeInPipe = new ArrayList<Sprite>();
    }

    public void augmenterFlux(){
        if(fluxEntrant>0){
            this.fluxCourant++;
            this.indicateurs.set_currentFlow(this.fluxCourant);
            System.out.println("Flux courant" + this.fluxCourant);
            if(sousPression()){
                //TODO: mettre un compte à rebours de 3 secondes
               // CoffeeLevel.compteARebours();
            }
            majFluxSortant(1);
            fluxEntrant--;
            if(fluxCourant==1){
                for(Sprite dropOfKoffee : koffeeInPipe){ //empty the pipe
                    CoffeeLevel.get_mapRenderer().addSomeKoffee(dropOfKoffee);
                }
            }
        }
    }

    public void diminuerFlux(){
        if(fluxCourant>0){
            this.fluxCourant--;
            this.indicateurs.set_currentFlow(this.fluxCourant);
            System.out.println("Flux courant" + this.fluxCourant);
            if(sousPression()){
                //TODO: mettre un compte à rebours de 3 secondes
                //CoffeeLevel.compteARebours();
            }
            majFluxSortant(-1);
            fluxEntrant++;
            if(fluxCourant==0){
                for(Sprite dropOfKoffee : koffeeInPipe){ //empty the pipe
                    CoffeeLevel.get_mapRenderer().removeSomeKoffee(dropOfKoffee);
                }
            }
        }
    }

    public void majFluxSortant(int fluxToAdd){
        Iterator<Tuyau> it = tuyauxSuivants.iterator();
        while(it.hasNext()){
            it.next().add_fluxEntrant(fluxToAdd);
        }
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
        return (fluxCourant-capacite>0) ? true : false;
    }

    public String toString(){
        String posCases = "";
        Iterator<int[]> it = cases.iterator();
        int[] posTmp = new int[2];

        while(it.hasNext()){
            posTmp = it.next();
            posCases+="x="+posTmp[0]+"|| y="+posTmp[1]+"\n";
        }

        return posCases+"Flux courant: "+this.fluxCourant+"||Capacité: "+this.capacite+"\n";
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

    public void addCase(int[] new_case, String typeOfPipe){
        this.cases.add(new_case);
        Sprite someKoffee = ((TextureAtlas)Assets.getManager().get(Assets.KOFFEE, TextureAtlas.class)).createSprite(typeOfPipe);
        someKoffee.setPosition(new_case[0]*64,new_case[1]*64);
        this.koffeeInPipe.add(someKoffee);
    }

    public void removeCase(int[] old_case){
        this.cases.remove(old_case);
    }

    /**
    * Getter of indicateurs
    * @return the value of indicateurs
    */
    public KoffeeMeter get_indicateurs(){
      return this.indicateurs;
    }

    /**
    * Setter of indicateurs
    * @param new_indicateurs: the new value of indicateurs
    */
    public void set_indicateurs(KoffeeMeter new_indicateurs){
      this.indicateurs = new_indicateurs;
    }

    /**
    * Getter of capacite
    * @return the value of capacite
    */
    public int get_capacite(){
      return this.capacite;
    }

    /**
    * Setter of capacite
    * @param new_capacite: the new value of capacite
    */
    public void set_capacite(int new_capacite){
      this.capacite = new_capacite;
      this.indicateurs.set_capacity(this.capacite);
    }


    /**
    * Getter of fluxEntrant
    * @return the value of fluxEntrant
    */
    public int get_fluxEntrant(){
      return this.fluxEntrant;
    }

    /**
    * Setter of fluxEntrant
    * @param new_fluxEntrant: the new value of fluxEntrant
    */
    public void set_fluxEntrant(int new_fluxEntrant){
      this.fluxEntrant = new_fluxEntrant;
    }

    public void add_fluxEntrant(int fluxSupp){
        System.out.println("Flux supp: "+fluxSupp);
        System.out.println("Flux entrant "+fluxEntrant);
        this.fluxEntrant = this.fluxEntrant+fluxSupp;
        System.out.println("Flux entrant "+fluxEntrant);
        if(fluxEntrant<0){
            this.diminuerFlux();
        }
    }

    /**
    * Getter of fluxCourant
    * @return the value of fluxCourant
    */
    public int get_fluxCourant(){
      return this.fluxCourant;
    }

    /**
    * Setter of fluxCourant
    * @param new_fluxCourant: the new value of fluxCourant
    */
    public void set_fluxCourant(int new_fluxCourant){
      this.fluxCourant = new_fluxCourant;
      this.indicateurs.set_currentFlow(new_fluxCourant);
        if(fluxCourant>0){
            for(Sprite dropOfKoffee : koffeeInPipe){ //empty the pipe
                CoffeeLevel.get_mapRenderer().addSomeKoffee(dropOfKoffee);
            }
        }else{
            for(Sprite dropOfKoffee : koffeeInPipe){ //empty the pipe
                CoffeeLevel.get_mapRenderer().removeSomeKoffee(dropOfKoffee);
            }
        }
    }
}
