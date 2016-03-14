package com.odysseedesmaths.minigames.coffeePlumbing.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.Vanne;

import java.util.HashSet;
import java.util.Iterator;

public class Canalisation{
    private TiledMap map;
    private TiledMapTileLayer vannesLayer;
    private TiledMapTileLayer tuyauxLayer;
    private TiledMapTileLayer koffeeMetersLayer;
    private TiledMapTileLayer liaisonsLayer;

    private int[] mapStart;
    private int[] mapEnd;
    private HashSet<Tuyau> allPipes;
    /**
    * Tableau qui permet de gérer les sens de passage au niveau des flux
    */
    private boolean[][] securiteTuyau;
    private int mapWidthTiled;
    private int mapHeightTiled;

    public Canalisation(TiledMap map,int mapWidthTiled, int mapHeightTiled){
        //configuration des variables de tailles
        this.map = map;
        this.mapWidthTiled = mapWidthTiled;
        this.mapHeightTiled = mapHeightTiled;

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
        this.vannesLayer = (TiledMapTileLayer) map.getLayers().get("vannes");
        this.tuyauxLayer = (TiledMapTileLayer) map.getLayers().get("tuyaux");
        this.koffeeMetersLayer = (TiledMapTileLayer) map.getLayers().get("koffeeMeters");
        this.liaisonsLayer = (TiledMapTileLayer) map.getLayers().get("liaisons");

        this.allPipes = new HashSet<Tuyau>();
    }

    public void createCanalisation() {
    /*
    * TODO: prendre les tuyauxLayer, leurs capacités, et les vannesLayer
    */
        this.buildSecurity();
        this.buildCanalisation(mapStart);
    }

    /**
    * Fonction de parcours recursif de allPipes
    * @param posOrigine: la position à partir de laquelle on va parcourir la allPipes (toujours une liaison)
    * @return les tuyauxLayer directs qu'il a trouvé
    */
    public Tuyau buildCanalisation(int[] posOrigine){
        int[] posCourante = new int[2];
        int[] posPrec = new int[2];

        //System.out.println("x: "+posOrigine[0]+" y: "+posOrigine[1]);

        if(posOrigine[0]!=mapEnd[0] || posOrigine[1]!=mapEnd[1]){
            String typeLiaison = (String) liaisonsLayer.getCell(posOrigine[0],posOrigine[1]).getTile().getProperties().get("name");
            //System.out.println(typeLiaison);

            posCourante[0]=posOrigine[0];
            posCourante[1]=posOrigine[1];

            //on prends le début de notre tuyau
            if(typeLiaison.equals("extrémité_horizontale")){ //si c'est horizontal, on teste à droite et à gauche si on trouve un morceau de tuyau
                if(tuyauxLayer.getCell(posCourante[0]+1,posCourante[1])!=null){//on teste à droite
                    posCourante[0]++;
                }
                else{
                    posCourante[0]--;
                }
                posPrec[0]=posOrigine[0];//et on mémorise pour pas repasser dessus
                posPrec[1]=posOrigine[1];
            }
            else{ //sinon c'est vertical
                if(tuyauxLayer.getCell(posCourante[0],posCourante[1]+1)!=null){ //on teste en haut
                    posCourante[1]++;
                }
                else{
                    posCourante[1]--;
                }
                posPrec[0]=posOrigine[0];//et on mémorise pour pas repasser dessus
                posPrec[1]=posOrigine[1];
            }
            System.out.println("x: "+posCourante[0]+" y: "+posCourante[1]);

            if(!securiteTuyau[posCourante[0]][posCourante[1]]){
                Tuyau tuyauTmp = new Tuyau(0);
                do{//une fois qu'on a le tuyau, on le parcours
                    tuyauTmp.addCase(posCourante.clone());
                    System.out.println("x: " + posCourante[0] + " y: " + posCourante[1]);
                    String s = (String) tuyauxLayer.getCell(posCourante[0],posCourante[1]).getTile().getProperties().get("name");
                    if(vannesLayer.getCell(posCourante[0],posCourante[1])!=null){
                        new Vanne(posCourante[0],posCourante[1],tuyauTmp);
                    }

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
                            posCourante[1]++;
                        }
                        else{ //sinon on va en bas
                            posPrec[0]=posCourante[0];
                            posPrec[1]=posCourante[1];
                            posCourante[1]--;
                        }
                        System.out.println("Vertical");

                    } else if (s.equals("left_top")) {
                        if ((posPrec[0] + 1) == posCourante[0]) { //si on viens de la droite
                            posPrec[0]=posCourante[0];
                            posPrec[1]=posCourante[1];
                            posCourante[1]++; //on monte
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
                            posCourante[1]--; //on descends
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
                            posCourante[1]++; //on monte
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
                            posCourante[1]--; //on descends
                        } else {//sinon on viens du bas
                            posPrec[0]=posCourante[0];
                            posPrec[1]=posCourante[1];
                            posCourante[0]++; //pour aller à droite
                        }
                        System.out.println("Courbe bas_droite");

                    } else {
                        System.out.println("Je connais pas");
                    }
                }while(liaisonsLayer.getCell(posCourante[0],posCourante[1])==null);//tant qu'on ne trouve pas de liaison

                //ensuite on une extremité de tuyau, on veut toutes les extremités successives
                HashSet<int[]> extremitesSucc = parcoursLiaison(posCourante,posPrec);
                Iterator it = extremitesSucc.iterator();

                while(it.hasNext()){
                    Tuyau tmp = this.buildCanalisation((int[])it.next());
                    if(tmp!=null){
                        tuyauTmp.addJunction(tmp);
                    }
                }
                this.allPipes.add(tuyauTmp);

                return tuyauTmp;
            }
            else{ //si on a pas le droit de parcourir, on retourne rien du tout
                return null;
            }
        }
        else{
            return null;//pas de successeurs si on est à la fin
        }
    }

    public HashSet<int[]> parcoursLiaison(int[] posOrigine, int[] posPrec){
        //une fois qu'on a fini notre tuyau, il faut trouver les autres extrémités
        HashSet<int[]> extremites = new HashSet();
        if(liaisonsLayer.getCell(posOrigine[0],posOrigine[1])!=null){//si on est sur une liaison
            String typeLiaison = (String) liaisonsLayer.getCell(posOrigine[0],posOrigine[1]).getTile().getProperties().get("name");
            System.out.println(typeLiaison);
            int[] posSuivanteGauche = new int[2];
            int[] posSuivanteBas = new int[2];
            int[] posSuivanteDroite = new int[2];
            int[] posSuivanteHaut = new int[2];

            posSuivanteGauche[0] = posOrigine[0]-1;
            posSuivanteGauche[1] = posOrigine[1];
            posSuivanteDroite[0] = posOrigine[0]+1;
            posSuivanteDroite[1] = posOrigine[1];
            posSuivanteBas[0] = posOrigine[0];
            posSuivanteBas[1] = posOrigine[1]-1;
            posSuivanteHaut[0] = posOrigine[0];
            posSuivanteHaut[1] = posOrigine[1]+1;

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


    public void buildSecurity(){
        this.securiteTuyau = new boolean[mapWidthTiled][mapHeightTiled];
        for(int i = 0; i<mapWidthTiled; i++){
            for(int j = 0; j<mapHeightTiled; j++){
                securiteTuyau[i][j] = false;
            }
        }
        //System.out.println((String)this.tuyauxLayer.getProperties().get("bloque"));
        String[] tmp = ((String)this.tuyauxLayer.getProperties().get("bloque")).split(" ");
        int x,y;
        for(int i=0; i<tmp.length; i++){
            String[] tmpBis = tmp[i].split(",");
            System.out.println(tmp[i]);
            x = Integer.parseInt(tmpBis[0]);
            y = Integer.parseInt(tmpBis[1]);
            this.securiteTuyau[x][y] = true;
        }
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
     * Getter of vannesLayer
     *
     * @return the value of vannesLayer
     */
    public TiledMapTileLayer get_vannesLayer() {
        return this.vannesLayer;
    }

    /**
     * Setter of vannesLayer
     *
     * @param new_vannes: the new value of vannesLayer
     */
    public void set_vannesLayer(TiledMapTileLayer new_vannes) {
        this.vannesLayer = new_vannes;
    }

    /**
     * Getter of tuyauxLayer
     *
     * @return the value of tuyauxLayer
     */
    public TiledMapTileLayer get_tuyauxLayer() {
        return this.tuyauxLayer;
    }

    /**
     * Setter of tuyauxLayer
     *
     * @param new_tuyaux: the new value of tuyauxLayer
     */
    public void set_tuyauxLayer(TiledMapTileLayer new_tuyaux) {
        this.tuyauxLayer = new_tuyaux;
    }

    /**
     * Getter of koffeeMetersLayer
     *
     * @return the value of koffeeMetersLayer
     */
    public TiledMapTileLayer get_koffeeMetersLayer() {
        return this.koffeeMetersLayer;
    }

    /**
     * Setter of koffeeMetersLayer
     *
     * @param new_koffeeMeters: the new value of koffeeMetersLayer
     */
    public void set_koffeeMetersLayer(TiledMapTileLayer new_koffeeMeters) {
        this.koffeeMetersLayer = new_koffeeMeters;
    }

    /**
    * Getter of allPipes
    * @return the value of allPipes
    */
    public HashSet<Tuyau> get_allPipes(){
      return this.allPipes;
    }

    /**
    * Setter of allPipes
    * @param new_canalisation: the new value of allPipes
    */
    public void set_allPipes(HashSet<Tuyau> new_canalisation){
      this.allPipes = new_canalisation;
    }

    /**
    * Getter of liaisonsLayer
    * @return the value of liaisonsLayer
    */
    public TiledMapTileLayer get_liaisonsLayer(){
      return this.liaisonsLayer;
    }

    /**
    * Setter of liaisonsLayer
    * @param new_liaisons: the new value of liaisonsLayer
    */
    public void set_liaisonsLayer(TiledMapTileLayer new_liaisons){
      this.liaisonsLayer = new_liaisons;
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
    * Getter of securiteTuyau
    * @return the value of securiteTuyau
    */
    public boolean[][] get_securiteTuyau(){
      return this.securiteTuyau;
    }

    /**
    * Setter of securiteTuyau
    * @param new_securiteTuyau: the new value of securiteTuyau
    */
    public void set_securiteTuyau(boolean[][] new_securiteTuyau){
      this.securiteTuyau = new_securiteTuyau;
    }
}
