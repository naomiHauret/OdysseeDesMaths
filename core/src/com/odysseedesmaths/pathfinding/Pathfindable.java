package com.odysseedesmaths.pathfinding;

import java.util.Set;

/**
 * Un Pathfindable est un ensemble d'objets dans lequel il est possible de calculer des chemins.<br>
 * La classe {@link Pathfinding} implémente quelques algorithmes à cet effet.
 */
public interface Pathfindable<E> {

    /**
     * Retourne les voisins de l'objet donné. C'est à dire les objets vers lesquels on peut se rendre à partir de l'objet donné.
     * @param e le sommet dont on veut les voisins
     * @return  les voisins
     */
    Set<E> getVoisins(E e);


    /**
     * Retourne une <strong>estimation</strong> du coût total du chemin de e1 à e2.<br>
     * Une bonne estimation est proche du coût réel (si elle ne le surestime jamais, l'heuristique est dite <strong>admissible</strong>).<br>
     * Peut rendre systématiquement 0 si :
     * <ul>
     *		<li>on ne veut utiliser que l'algorithme de Dijkstra (il n'utilise pas d'heuristique)</li>
     *		<li>on veut que A* se comporte comme l'algorithme de Dijkstra</li>
     * </ul>
     *
     * @param e1 le premier sommet
     * @param e2 le second sommet
     * @return   le coût total pour se rendre de s1 à s2
     */
    int heuristic(E e1, E e2);


    /**
     * Retourne le coût pour passer de e1 à e2, <strong>e2 étant voisin à e1</strong>.<br>
     * Le coût doit être calculé comme suit :
     * <ul>
     *     <li>0 si e1 égal e2</li>
     *     <li>le coût du déplacement sinon</li>
     * </ul>
     * @param e1    le premier sommet
     * @param e2    le second sommet
     * @return      le coût pour passer de e1 à e2
     */
    int cost(E e1, E e2);
}
