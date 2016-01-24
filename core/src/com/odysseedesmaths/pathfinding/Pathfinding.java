package com.odysseedesmaths.pathfinding;

import com.odysseedesmaths.pathfinding.util.PriorityQueue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implémentation basique de quelques algorithmes permettant de calculer des chemins dans un {@link Pathfindable}.
 */
public abstract class Pathfinding {

    /**
     * Algorithme de Dijkstra.<br>
     * Permet de calculer les chemins d'un sommet vers tous les autres sommets du graphe.<br>
     * Les chemins peuvent être extraits de la map des prédécesseurs avec {@link #buildPath(Map, Object, Object) buildPath}.
     * @param <E>       le type des objets contenus dans le Pathfindable
     * @param g         l'ensemble d'objet dans lequel rechercher les chemins
     * @param start     le sommet de départ
     * @param dist      la map des distances relatives au sommet de départ (<strong>contenu précédent perdu</strong>)
     * @param came_from la map des prédécesseurs de chaque sommet sur le chemin pour se rendre au point de départ (<strong>contenu précédent perdu</strong>)
     */
    public static <E> void dijkstraAll(Pathfindable<E> g, E start, Map<E, Integer> dist, Map<E, E> came_from) {
        // Nettoyage et initialisation des map
        dist.clear();
        came_from.clear();
        dist.put(start, 0);
        came_from.put(start, null);

        // Liste priorisée des sommets à traiter, initialisée avec le départ
        PriorityQueue<E> toDo = new PriorityQueue<E>();
        toDo.put(start,0);

        // Calcul du chemin tant qu'il y a des sommets à traiter
        while (!toDo.isEmpty()) {

            // On traite le sommet le plus prometteur
            E current = toDo.get();

            // Calcul des nouveaux coûts et predecesseurs sur ses voisins
            for (E voisin : g.getVoisins(current)) {
                int new_dist = dist.get(current) + g.cost(current,voisin);
                // Si la nouvelle distance est plus petite que l'ancienne on met à jour les map
                if ((dist.get(voisin) == null) || (new_dist < dist.get(voisin))) {
                    dist.put(voisin, new_dist);
                    toDo.put(voisin, new_dist);
                    came_from.put(voisin, current);
                }
            }
        }
    }


    /**
     * Algorithme de Dijkstra ne rendant que la map des prédécesseurs.
     * @param <E>       le type des objets contenus dans le Pathfindable
     * @param g         l'ensemble d'objet dans lequel rechercher les chemins
     * @param start     le sommet de départ
     * @return          la map des prédécesseurs de chaque sommet sur le chemin pour se rendre au sommet de départ
     */
    public static <E> Map<E, E> dijkstraAllPred(Pathfindable<E> g, E start) {
        Map<E, Integer> dist = new HashMap<E, Integer>();
        Map<E, E> came_from = new HashMap<E, E>();
        dijkstraAll(g, start, dist, came_from);
        return came_from;
    }


    /**
     * Algorithme de Dijkstra ne rendant que la map des distances.
     * @param <E>       le type des objets contenus dans le Pathfindable
     * @param g         l'ensemble d'objet dans lequel rechercher les chemins
     * @param start     le sommet de départ
     * @return          la map des distances relatives au sommet de départ
     */
    public static <E> Map<E, Integer> dijkstraAllDist(Pathfindable<E> g, E start) {
        Map<E, Integer> dist = new HashMap<E, Integer>();
        Map<E, E> came_from = new HashMap<E, E>();
        dijkstraAll(g, start, dist, came_from);
        return dist;
    }


    /**
     * Algorithme de Dijkstra appliqué au calcul de chemin.<br>
     * Trouve toujours le chemin le plus court.<br>
     * Attention cependant il est <strong>beaucoup moins optimisé</strong> que A*.
     *
     * @param <E>       le type des objets contenus dans le Pathfindable
     * @param g         l'ensemble d'objet dans lequel rechercher le chemin
     * @param start     le sommet de départ
     * @param goal      le sommet destination
     * @return          le chemin
     */
    public static <E> LinkedList<E> dijkstra(Pathfindable<E> g, E start, E goal) {
        // Map des distances relatives au départ
        Map<E, Integer> dist = new HashMap<E, Integer>();
        dist.put(start, 0);

        // Map des précédents de chacun des sommets (sur le chemin final)
        Map<E, E> came_from = new HashMap<E, E>();
        came_from.put(start, null);

        // Liste priorisée des sommets à traiter, initialisée avec le départ
        PriorityQueue<E> toDo = new PriorityQueue<E>();
        toDo.put(start,0);

        // Calcul du chemin tant qu'il y a des sommets à traiter
        while (!toDo.isEmpty()) {

            // On traite le sommet le plus prometteur
            E current = toDo.get();

            // Si c'est l'arrivée, on a fini
            if (current.equals(goal)) break;

            // Calcul des nouveaux coûts et predecesseurs sur ses voisins
            for (E voisin : g.getVoisins(current)) {
                int new_dist = dist.get(current) + g.cost(current,voisin);
                // Si la nouvelle distance est plus petite que l'ancienne on met à jour les map
                if ((dist.get(voisin) == null) || (new_dist < dist.get(voisin))) {
                    dist.put(voisin, new_dist);
                    toDo.put(voisin, new_dist);
                    came_from.put(voisin, current);
                }
            }
        }

        // Le chemin est calculé, il ne reste plus qu'à le reconstruire
        return buildPath(came_from, start, goal);
    }


    /**
     * Algorithme A*.<br>
     * Son comportement dépend de l'heuristique définie dans le Pathfindable donné.
     * <ul>
     *      <li>Si l'heuristique retourne toujours 0, A* devient l'algorithme de Dijkstra.</li>
     *      <li>Si l'heuristique ne surestime jamais le coût réel, A* trouvera toujours le chemin le plus court.</li>
     *      <li>Si l'heuristique surestime parfois le coût réel, A* peut ne pas trouver le chemin le plus court, mais reste rapide.</li>
     *      <li>Si l'heuristique surestime souvent et de manière importante le coût réel, A* devient l'algorithme "greedy".</li>
     * </ul>
     *
     * @param <E>       le type des objets contenus dans le Pathfindable
     * @param g         l'ensemble d'objet dans lequel rechercher le chemin
     * @param start     le sommet de départ
     * @param goal      le sommet destination
     * @return          le chemin
     */
    public static <E> LinkedList<E> astar(Pathfindable<E> g, E start, E goal) {
        // Map des distances relatives au départ
        Map<E, Integer> dist = new HashMap<E, Integer>();
        dist.put(start, 0);

        // Map des précédents de chacun des sommets (sur le chemin final)
        Map<E, E> came_from = new HashMap<E, E>();
        came_from.put(start, null);

        // Liste priorisée des sommets à traiter, initialisée avec le départ
        PriorityQueue<E> toDo = new PriorityQueue<E>();
        toDo.put(start,0);

        // Calcul du chemin tant qu'il y a des sommets à traiter
        while (!toDo.isEmpty()) {

            // On traite le sommet le plus prometteur
            E current = toDo.get();

            // Si c'est l'arrivée, on a fini
            if (current.equals(goal)) break;

            // Calcul des nouveaux coûts et predecesseurs sur ses voisins
            for (E voisin : g.getVoisins(current)) {
                int new_dist = dist.get(current) + g.cost(current,voisin);
                // Si la nouvelle distance est plus petite que l'ancienne on met à jour les map
                if ((dist.get(voisin) == null) || (new_dist < dist.get(voisin))) {
                    dist.put(voisin, new_dist);
                    int priority = new_dist + g.heuristic(voisin, goal);
                    toDo.put(voisin, priority);
                    came_from.put(voisin, current);
                }
            }
        }

        // Le chemin est calculé, il ne reste plus qu'à le reconstruire
        return buildPath(came_from, start, goal);
    }


    /**
     * Algorithme "Greedy Best First Search".<br>
     * Très rapide mais ne trouve pas toujours le meilleur chemin.<br>
     * Se contente de se rapprocher le plus possible de la cible, en se basant sur l'heuristique définie dans le Pathfindable donné.<br>
     * Utile quand il n'y a aucun obstacle entre les deux sommets.
     *
     * @param <E>       le type des objets contenus dans le Pathfindable
     * @param g         l'ensemble d'objet dans lequel rechercher le chemin
     * @param start     le sommet de départ
     * @param goal      le sommet destination
     * @return          le chemin
     */
    public static <E> LinkedList<E> greedy(Pathfindable<E> g, E start, E goal) {
        // Map des précédents de chacun des sommets (sur le chemin final)
        Map<E, E> came_from = new HashMap<E, E>();
        came_from.put(start, null);

        // Liste priorisée des sommets à traiter, initialisée avec le départ
        PriorityQueue<E> toDo = new PriorityQueue<E>();
        toDo.put(start,0);

        // Calcul du chemin tant qu'il y a des sommets à traiter
        while (!toDo.isEmpty()) {

            // On traite le sommet le plus prometteur
            E current = toDo.get();

            // Si c'est l'arrivée, on a fini
            if (current.equals(goal)) break;

            // Calcul des nouveaux coûts et predecesseurs sur ses voisins
            for (E voisin : g.getVoisins(current)) {
                // Si la nouvelle distance est plus petite que l'ancienne on met à jour les map
                if (came_from.get(voisin) == null) {
                    came_from.put(voisin, current);
                    int priority = g.heuristic(voisin,goal);
                    toDo.put(voisin, priority);
                }
            }
        }

        // Le chemin est calculé, il ne reste plus qu'à le reconstruire
        return buildPath(came_from, start, goal);
    }


    /**
     * Construit un chemin entre deux sommets à partir d'une map de prédécesseurs
     * @param <E>           le type des objets contenus dans la map des prédécesseurs
     * @param came_from     la map indiquant les prédécesseurs de chacun des sommets
     * @param s1            sommet de départ
     * @param s2            sommet d'arrivé
     * @return              le chemin entre les deux sommets
     */
    public static <E> LinkedList<E> buildPath(Map<E,E> came_from, E s1, E s2) {
        LinkedList<E> chemin = new LinkedList<E>();

        if (came_from.get(s2) != null) {
            E s = s2;
            while (s != s1) {
                chemin.addFirst(s);
                s = came_from.get(s);
            }
        }

        return chemin;
    }

}
