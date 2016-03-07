package com.odysseedesmaths.minigames.coffeePlumbing.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.Vanne;

import java.util.HashSet;
import java.util.Iterator;

import static java.lang.Thread.sleep;

/**
 * Created by trilunaire on 20/02/16.
 */
/*TODO: - Faire une fonction permettant de prendre tous les tuyaux (voir les layers de la carte, et les propriétés des objets) - Faire une fonction permettant de trouver les vannes
*/
public class CoffeeLevel {
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private TiledMapTileLayer vannes;
    private TiledMapTileLayer tuyaux;
    private TiledMapTileLayer koffeeMeters;
    private TiledMapTileLayer liaisons;
    /**
     * Représente la largeur en pixel de la map
     */
    private int mapWidthPixel;
    /**
     * Représente la hauteur en pixel de la map
     */
    private int mapHeightPixel;
    private int mapWidthTiled;
    private int mapHeightTiled;
    private int tileWidth;
    private int tileHeight;
    private int[] mapStart;
    private int[] mapEnd;
    private Sprite[][] sprites;
    private HashSet<Tuyau> canalisation;


    public CoffeeLevel(String mapPath) {
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);

        //configuration des variables de tailles
        this.tileWidth = ((Integer) this.map.getProperties().get("tilewidth"));
        this.tileHeight = ((Integer) this.map.getProperties().get("tileheight"));

        this.mapWidthTiled = (Integer) this.map.getProperties().get("width");
        this.mapHeightTiled = (Integer) this.map.getProperties().get("height");

        this.mapWidthPixel = mapWidthTiled * tileWidth;
        this.mapHeightPixel = mapHeightTiled * tileHeight;

        //points de départ et de fin des niveaux
        String[] sStart = ((String)this.map.getProperties().get("start")).split(",");
        String[] sEnd = ((String)this.map.getProperties().get("end")).split(",");

        this.mapStart = new int[2];
        this.mapEnd = new int[2];

        this.mapStart[0] = Integer.valueOf(sStart[0]);
        this.mapStart[1] = Integer.valueOf(sStart[1]);
        this.mapEnd[0] = Integer.valueOf(sEnd[0]);
        this.mapEnd[1] = Integer.valueOf(sEnd[1]);


        //configuration des layers
        this.vannes = (TiledMapTileLayer) map.getLayers().get("vannes");
        this.tuyaux = (TiledMapTileLayer) map.getLayers().get("tuyaux");
        this.koffeeMeters = (TiledMapTileLayer) map.getLayers().get("koffeeMeters");
        this.liaisons = (TiledMapTileLayer) map.getLayers().get("liaisons");

        this.sprites=new Sprite[mapWidthTiled][mapHeightTiled];

        this.canalisation = new HashSet<Tuyau>();

        this.createGUI();
    }

    public void createGUI() {
    /*
    * TODO: prendre les tuyaux, leurs capacités, et les vannes
    */
        boolean[][] posTuyaux = getItemsPosition(this.tuyaux);
        boolean[][] posVannes = getItemsPosition(this.vannes);
        boolean[][] posLiaison = getItemsPosition(this.liaisons);
        boolean[][] posKoffeeMeter = getItemsPosition(this.koffeeMeters);

        this.findPipes(posLiaison, posTuyaux);
    }

    public void findPipes(boolean[][] posLiaison, boolean[][] posTuyaux){
        boolean[][] liaisonAlreadyTest = new boolean[mapWidthTiled][mapHeightTiled];
        int[] coordonneesPrecedentes = new int[2];
        int[] coordonneesCourantes=new int[2];
        int[] coordonneesOrigines=new int[2];
        Tuyau tuyauTmp;

        // displayTab(posLiaison);
        // displayTab(posTuyaux);
        // displayTab(posKoffeeMeter);
    }

    /**
    * Fonction de parcours recursif de canalisation
    * @param posOrigine: la position à partir de laquelle on va parcourir la canalisation (toujours une liaison)
    * @return les tuyaux directs qu'il a trouvé
    */
    public Tuyau buildCanalisation(int[] posOrigine){
        int[] posCourante = new int[2];
        int[] posPrec = new int[2];

        if(posOrigine[0]!=mapEnd[0] || posOrigine[1]!=mapEnd[1]){
            String typeLiaison = (String)liaisons.getCell(posOrigine[0],posOrigine[1]).getTile().getProperties().get("name");
            System.out.println(typeLiaison);

            posCourante[0]=posOrigine[0];
            posCourante[1]=posOrigine[1];

            //on prends le début de notre tuyau
            if(typeLiaison.equals("extrémité_horizontale")){ //si c'est horizontal, on teste à droite et à gauche si on trouve un morceau de tuyau
                if(tuyaux.getCell(posCourante[0]+1,posCourante[1])!=null){//on teste à droite
                    posCourante[0]++;
                }
                else{
                    posCourante[0]--;
                }
                posPrec[0]=posOrigine[0];//et on mémorise pour pas repasser dessus
                posPrec[1]=posOrigine[1];
            }
            else{ //sinon c'est vertical
                if(tuyaux.getCell(posCourante[0],posCourante[1]+1)!=null){ //on teste en haut
                    posCourante[1]++;
                }
                else{
                    posCourante[1]--;
                }
                posPrec[0]=posOrigine[0];//et on mémorise pour pas repasser dessus
                posPrec[1]=posOrigine[1];
            }

            //TODO: testet via des propriétés si l'on peut parcourir le tuyau (propriété de la case?)
            Tuyau tuyauTmp = new Tuyau(0);
            do{//une fois qu'on a le tuyau, on le parcours
                String s = (String) tuyaux.getCell(posCourante[0], (mapHeightTiled-1)-posCourante[1]).getTile().getProperties().get("name");

                if (s.equals("horizontal")) {
                    if((posPrec[0]+1)==posCourante[0]){//si on était à gauche
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[0]++; //on va à droite
                    }
                    else{ //sinon on va à gauche
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[0]--;
                    }
                    System.out.println("Horizontal");

                } else if (s.equals("vertical")) {
                    if((posPrec[1]+1)==posCourante[0]){//si on était en bas
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[1]--;
                    }
                    else{ //sinon on va en haut
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[1]++;
                    }
                    System.out.println("Vertical");

                } else if (s.equals("left_top")) {
                    if ((posPrec[0] + 1) == posCourante[0]) { //si on viens de la droite
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[1]--; //on monte
                    } else {//sinon on viens du haut
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[0]--; //pour aller à gauche
                    }
                    System.out.println("Courbe gauche_haut");

                } else if (s.equals("left_bottom")) {
                    if ((posPrec[0] + 1) == posCourante[0]) { //si on viens de la gauche
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[1]++; //on descends
                    } else {//sinon on viens du haut
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[0]--; //pour aller à gauche
                    }
                    System.out.println("Courbe gauche_bas");

                } else if (s.equals("top_right")) {
                    if ((posPrec[0] - 1) == posCourante[0]) { //si on viens de la droite
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[1]--; //on monte
                    } else {//sinon on viens du haut
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[0]++; //pour aller à droite
                    }
                    System.out.println("Courbe gauche_haut");

                } else if (s.equals("bottom_right")) {
                    if ((posPrec[0] - 1) == posCourante[0]) { //si on vient de la droite
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[1]++; //on descends
                    } else {//sinon on viens du bas
                        posPrec[0]=posCourante[0];
                        posPrec[1]=posCourante[1];
                        posCourante[0]++; //pour aller à droite
                    }
                    System.out.println("Courbe bas_droite");

                } else {
                    System.out.println("Je connais pas");
                }
                tuyauTmp.addCase(posPrec);
            }while(liaisons.getCell(posCourante[0],posCourante[1])==null);//tant qu'on ne trouve pas de liaison

            //ensuite on une extremité de tuyau, on veut toutes les extremités successives
            HashSet<int[]> extremitesSucc = parcoursLiaison(posCourante,posPrec);
            Iterator it = extremitesSucc.iterator();


            return tuyauTmp;
        }
        else{
            return null;
        }
    }


    public HashSet<int[]> parcoursLiaison(int[] posOrigine, int[] posPrec){
        //une fois qu'on a fini notre tuyau, il faut trouver les autres extrémités
        HashSet<int[]> extremites = new HashSet();
        if(liaisons.getCell(posOrigine[0],posOrigine[1])!=null){//si on est sur une liaison
            String typeLiaison = (String)liaisons.getCell(posOrigine[0],posOrigine[1]).getTile().getProperties().get("name");
            System.out.println(typeLiaison);
            int[] posSuivanteGauche = new int[2];
            int[] posSuivanteBas = new int[2];
            int[] posSuivanteDroite = new int[2];
            int[] posSuivanteHaut = new int[2];

            posSuivanteGauche[0] = posOrigine[0]--;
            posSuivanteGauche[1] = posOrigine[1];
            posSuivanteDroite[0] = posOrigine[0]++;
            posSuivanteDroite[1] = posOrigine[1];
            posSuivanteBas[0] = posOrigine[0];
            posSuivanteBas[1] = posOrigine[1]--;
            posSuivanteHaut[0] = posOrigine[0];
            posSuivanteHaut[1] = posOrigine[1]++;

            switch(typeLiaison){
                case "extrémité_verticale":
                    if((posPrec[1]-1)==posOrigine[1]){//si on était en haut, on descends
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        posOrigine = posSuivanteBas;
                    }else{
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        posOrigine = posSuivanteHaut;
                    }
                    extremites = parcoursLiaison(posOrigine,posPrec);
                    break;
                case "extrémité_horizontale":
                    if((posPrec[0]-1)==posOrigine[0]){//si on était à droite
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        posOrigine = posSuivanteGauche;
                    }else{
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        posOrigine = posSuivanteDroite;
                    }
                    extremites = parcoursLiaison(posOrigine,posPrec);
                    break;
                case "3way_bottom":
                    if((posPrec[0]-1)==posOrigine[0]){//si on était à droite
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        //aller à gauche et en bas
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                    }else if((posPrec[1]+1)==posOrigine[1]){//si on était en bas
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        //aller à droite et à gauche
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                    }else{
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        //aller en bas et à droite
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }
                    break;
                case "3way_top":
                    if((posPrec[0]-1)==posOrigine[0]){//si on viens de la droite
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                    }else if((posPrec[0]+1)==posOrigine[0]){//si on viens de la gauche
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }else{//sinon on viens du haut
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }
                    break;
                case "3way_left":
                    if((posPrec[0]+1)==posOrigine[0]){//si on viens de la gauche
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }else if((posPrec[1]-1)==posOrigine[1]){//si on viens du haut
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                    }
                    else{//on viens du bas
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }
                    break;
                case "3way_right":
                    if((posPrec[0]-1)==posOrigine[0]){//si on viens de la droite
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                    }else if((posPrec[1]-1)==posOrigine[1]){//si on viens du haut
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }
                    else{//on viens du bas
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }
                    break;
                case "4way":
                    if((posPrec[0]-1)==posOrigine[0]){//si on viens de la droite
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                    }else if((posPrec[0]+1)==posOrigine[0]){//si on viens de la gauche
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                    }else if((posPrec[1]-1)==posOrigine[1]){//si on viens du haut
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteBas,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                    }
                    else{//on viens du bas
                        posPrec[0]=posOrigine[0];
                        posPrec[1]=posOrigine[1];
                        extremites.addAll(parcoursLiaison(posSuivanteDroite,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteHaut,posPrec));
                        extremites.addAll(parcoursLiaison(posSuivanteGauche,posPrec));
                    }
                    break;
            }
        }
        else{//si on arrive à la fin de la liaison
            extremites.add(posPrec);
        }
        return extremites;
    }

    /**
     * Return a boolean tab that symbolise the presence of items in the layer
     * @param tiMaTileLayer
     * @return a two dimention boolean tab
     */
    public boolean[][] getItemsPosition(TiledMapTileLayer tiMaTileLayer) {
        boolean[][] pos = new boolean[this.mapWidthTiled][this.mapHeightTiled];
        for (int i = 0; i < this.mapWidthTiled; i++) {
            for (int j = 0; j < this.mapHeightTiled; j++) {
                pos[i][j] = (tiMaTileLayer.getCell(i, (mapHeightTiled-1)-j) != null); //le (mapHeightTiled-1)-j est pour prendre d'abord les lignes du haut, le (0,0) de la map étant en bas à gauche, on a ainsi un tableau dans le sens de lecture
            }
        }
        return pos;
    }

    /**
     * Getter of mapHeightPixel
     *
     * @return the value of mapHeightPixel
     */
    public int get_mapHeightPixel() {
        return this.mapHeightPixel;
    }

    /**
     * Setter of mapHeightPixel
     *
     * @param new_mapHeightPixel: new value of mapHeightPixel
     */
    public void set_mapHeightPixel(int new_mapHeightPixel) {
        this.mapHeightPixel = new_mapHeightPixel;
    }

    /**
     * Getter of mapWidthPixel
     *
     * @return the value of mapWidthPixel
     */
    public int get_mapWidthPixel() {
        return this.mapWidthPixel;
    }

    /**
     * Setter of mapWidthPixel
     *
     * @param new_mapWidthPixel: new value of mapWidthPixel
     */
    public void set_mapWidthPixel(int new_mapWidthPixel) {
        this.mapWidthPixel = new_mapWidthPixel;
    }

    /**
     * Getter of mapRenderer
     *
     * @return the value of mapRenderer
     */
    public TiledMapRenderer get_mapRenderer() {
        return this.mapRenderer;
    }

    /**
     * Setter of mapRenderer
     *
     * @param new_mapRenderer: new value of mapRenderer
     */
    public void set_mapRenderer(TiledMapRenderer new_mapRenderer) {
        this.mapRenderer = new_mapRenderer;
    }

    /**
     * Getter of map
     *
     * @return the value of map
     */
    public TiledMap get_map() {
        return this.map;
    }

    /**
     * Setter of map
     *
     * @param new_map: new value of map
     */
    public void set_map(TiledMap new_map) {
        this.map = new_map;
    }

    /**
     * Getter of tileHeight
     *
     * @return the value of tileHeight
     */
    public int get_tileHeight() {
        return this.tileHeight;
    }

    /**
     * Setter of tileHeight
     *
     * @param new_tileHeight: new value of tileHeight
     */
    public void set_tileHeight(int new_tileHeight) {
        this.tileHeight = new_tileHeight;
    }

    /**
     * Getter of tileWeight
     *
     * @return the value of tileWeight
     */
    public int get_tileWidth() {
        return this.tileWidth;
    }

    /**
     * Setter of tileWeight
     *
     * @param new_tileWidth: new value of tileWeight
     */
    public void set_tileWidth(int new_tileWidth) {
        this.tileWidth = new_tileWidth;
    }

    /**
     * Getter of vannes
     *
     * @return the value of vannes
     */
    public TiledMapTileLayer get_vannes() {
        return this.vannes;
    }

    /**
     * Setter of vannes
     *
     * @param new_vannes: the new value of vannes
     */
    public void set_vannes(TiledMapTileLayer new_vannes) {
        this.vannes = new_vannes;
    }

    /**
     * Getter of tuyaux
     *
     * @return the value of tuyaux
     */
    public TiledMapTileLayer get_tuyaux() {
        return this.tuyaux;
    }

    /**
     * Setter of tuyaux
     *
     * @param new_tuyaux: the new value of tuyaux
     */
    public void set_tuyaux(TiledMapTileLayer new_tuyaux) {
        this.tuyaux = new_tuyaux;
    }

    /**
     * Getter of koffeeMeters
     *
     * @return the value of koffeeMeters
     */
    public TiledMapTileLayer get_koffeeMeters() {
        return this.koffeeMeters;
    }

    /**
     * Setter of koffeeMeters
     *
     * @param new_koffeeMeters: the new value of koffeeMeters
     */
    public void set_koffeeMeters(TiledMapTileLayer new_koffeeMeters) {
        this.koffeeMeters = new_koffeeMeters;
    }

    /**
     * Getter of mapWidthTiled
     *
     * @return the value of mapWidthTiled
     */
    public int get_mapWidthTiled() {
        return this.mapWidthTiled;
    }

    /**
     * Setter of mapWidthTiled
     *
     * @param new_mapWidthTiled: the new value of mapWidthTiled
     */
    public void set_mapWidthTiled(int new_mapWidthTiled) {
        this.mapWidthTiled = new_mapWidthTiled;
    }

    /**
     * Getter ofmapHeightTiled
     *
     * @return the value ofmapHeightTiled
     */
    public int get_mapHeightTiled() {
        return this.mapHeightTiled;
    }

    /**
     * Setter ofmapHeightTiled
     *
     * @param new_mapHeightTiled: the new value ofmapHeightTiled
     */
    public void set_mapHeightTiled(int new_mapHeightTiled) {
        this.mapHeightTiled = new_mapHeightTiled;
    }

    /**
    * Getter of sprite
    * @return the value of sprite
    */
    public Sprite[][] get_sprites(){
      return this.sprites;
    }

    /**
    * Setter of sprite
    * @param new_sprites: the new value of sprite
    */
    public void set_sprites(Sprite[][] new_sprites){
      this.sprites = new_sprites;
    }

    /**
    * Getter of canalisation
    * @return the value of canalisation
    */
    public HashSet<Tuyau> get_canalisation(){
      return this.canalisation;
    }

    /**
    * Setter of canalisation
    * @param new_canalisation: the new value of canalisation
    */
    public void set_canalisation(HashSet<Tuyau> new_canalisation){
      this.canalisation = new_canalisation;
    }

    /**
    * Getter of liaisons
    * @return the value of liaisons
    */
    public TiledMapTileLayer get_liaisons(){
      return this.liaisons;
    }

    /**
    * Setter of liaisons
    * @param new_liaisons: the new value of liaisons
    */
    public void set_liaisons(TiledMapTileLayer new_liaisons){
      this.liaisons = new_liaisons;
    }

    /**
    * Getter of mapEnd
    * @return the value of mapEnd
    */
    public int[] get_mapEnd(){
      return this.mapEnd;
    }

    /**
    * Setter of mapEnd
    * @param new_mapEnd: the new value of mapEnd
    */
    public void set_mapEnd(int[] new_mapEnd){
      this.mapEnd = new_mapEnd;
    }

    /**
    * Getter of mapStart
    * @return the value of mapStart
    */
    public int[] get_mapStart(){
      return this.mapStart;
    }

    /**
    * Setter of mapStart
    * @param new_mapStart: the new value of mapStart
    */
    public void set_mapStart(int[] new_mapStart){
      this.mapStart = new_mapStart;
    }

    /**
    * Fonction utilisée pour faire les débugs des tableaux de booléens
    */
    public void displayTab(boolean[][] tab){
        for(int j=0;j<mapHeightTiled;j++){//on prends une ligne (un étage)
            for(int i=0;i<mapWidthTiled; i++){//On affiche toutes les colonnes de cette ligne
                System.out.print("|"+tab[i][j]+"|");
            }
            System.out.print("\n");
        }
    }

    public void dispose() {
        map.dispose();
    }
}
