package com.odysseedesmaths.minigames.coffeePlumbing.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.KoffeeMeter;
import com.odysseedesmaths.minigames.coffeePlumbing.Sprite.Vanne;

import java.util.HashSet;
import java.util.Iterator;

public class CoffeeLevel {
    private TiledMap map;
    /**
     * Représente la largeur en pixel de la map
     */
    private static int mapWidthPixel;
    /**
     * Représente la hauteur en pixel de la map
     */
    private static int mapHeightPixel;
    private int mapWidthTiled;
    private int mapHeightTiled;
    private int tileWidth;
    private int tileHeight;
    private Canalisation canalisation;
    private static HashSet<Vanne> vannes;
    private static HashSet<KoffeeMeter> indicateurs;
    private Table valveButtons;
    private Label comptAR;

    public CoffeeLevel(String mapPath) {
        this.map = new TmxMapLoader().load(mapPath);

        //configuration des variables de tailles
        this.tileWidth = ((Integer) this.map.getProperties().get("tilewidth"));
        this.tileHeight = ((Integer) this.map.getProperties().get("tileheight"));

        mapWidthTiled = (Integer) this.map.getProperties().get("width");
        mapHeightTiled = (Integer) this.map.getProperties().get("height");

        this.mapWidthPixel = mapWidthTiled * tileWidth;
        this.mapHeightPixel = mapHeightTiled * tileHeight;

        vannes = new HashSet<Vanne>();
        indicateurs = new HashSet<KoffeeMeter>();

        this.canalisation = new Canalisation(map,mapWidthTiled,mapHeightTiled);
    }

    public void buildLevel(){
        canalisation.createCanalisation();
    }

    /*
    public void compteARebours(){
        if(this.comptAR==null){
            FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Assets.KENPIXEL_BLOCKS);
            FreeTypeFontGenerator.FreeTypeFontParameter ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
            ftfp.size = 24; //the size can be changed later
            ftfp.color = new Color(0.42f,0.64f,0.62f,1);
            BitmapFont font = ftfg.generateFont(ftfp);

            Label.LabelStyle style = new Label.LabelStyle();
            style.font=font;

            this.comptAR = new Label("3",font);
            //TODO: find the way to middle align the number in the middle of the screen
        }else{
            //TODO: do nothing or decrease the countDown
        }
    }*/

    /**
     * Getter of mapHeightPixel
     * @return the value of mapHeightPixel
     */
    public static int get_mapHeightPixel() {
        return mapHeightPixel;
    }

    /**
     * Setter of mapHeightPixel
     * @param new_mapHeightPixel: new value of mapHeightPixel
     */
    public static void set_mapHeightPixel(int new_mapHeightPixel) {
        mapHeightPixel = new_mapHeightPixel;
    }

    /**
     * Getter of mapWidthPixel
     * @return the value of mapWidthPixel
     */
    public static int get_mapWidthPixel() {
        return mapWidthPixel;
    }

    /**
     * Setter of mapWidthPixel
     * @param new_mapWidthPixel: new value of mapWidthPixel
     */
    public static void set_mapWidthPixel(int new_mapWidthPixel) {
        mapWidthPixel = new_mapWidthPixel;
    }

    /**
     * Getter of map
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
    * Getter of canalisation
    * @return the value of canalisation
    */
    public Canalisation get_canalisation(){
      return this.canalisation;
    }

    /**
    * Setter of canalisation
    * @param new_canalisation: the new value of canalisation
    */
    public void set_canalisation(Canalisation new_canalisation){
      this.canalisation = new_canalisation;
    }

    public void dispose() {
        map.dispose();
    }

    public HashSet<Vanne> get_vannes(){
        return vannes;
    }

    public static void addVanne(Vanne new_Vanne){
        vannes.add(new_Vanne);
    }

    public static void delVanne(Vanne old_vanne){
        vannes.remove(old_vanne);
    }

    /**
    * Getter of indicateurs
    * @return the value of indicateurs
    */
    public HashSet<KoffeeMeter> get_indicateurs(){
      return indicateurs;
    }

    /**
    * Setter of indicateurs
    * @param new_indicateurs: the new value of indicateurs
    */
    public void set_indicateurs(HashSet<KoffeeMeter> new_indicateurs){
      indicateurs = new_indicateurs;
    }

    public static void addKoffeeMeter(KoffeeMeter newKFM){
        indicateurs.add(newKFM);
    }

    public static void delKFM(KoffeeMeter oldKFM){
        indicateurs.remove(oldKFM);
    }
}
