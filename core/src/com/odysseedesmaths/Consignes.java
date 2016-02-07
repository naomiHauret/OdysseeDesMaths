package com.odysseedesmaths;

/**
 * Classe abstraite permettant de faire apparaitre les consignes pour les mini-jeux.
 * Le mini-jeu doit être en fond avec une sorte de fenêtre comme pour un type de menu apparaissant par-dessus le jeu avec les consignes.
 * Le mini-jeu ne doit pas encore se lancer. Il se lance une fois que le joueur en a finit avec les consignes.
 *
 * Chacune des consignes des mini-jeux va hériter des méthodes au sein des différents mini-jeux.
 */
public class Consignes {

    private String consigne;

    public Consignes() {

    }

    public void setConsigne(String regle) { this.consigne = regle; }

    public String getConsigne() { return this.consigne; }
}