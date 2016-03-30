package com.odysseedesmaths.minigames.coffeePlumbing;

import com.odysseedesmaths.Musique;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.minigames.MiniGame;

/**
 * Created by trilunaire on 08/02/16.
 */
public class CoffeePlumbing extends MiniGame {
    private String currentLevel;
    private PipesScreen ui;

    public CoffeePlumbing(OdysseeDesMaths game){
        super(game);
        init();
        set_currentLevel("maps/CoffeePlumbing/mapTestNewTextures.tmx");
        setScreen(new PipesScreen(this,currentLevel));
    }

    /**
    * Getter of currentLevel
    * @return the value of currentLevel
    */
    public String get_currentLevel(){
      return this.currentLevel;
    }

    /**
    * Setter of currentLevel
    * @param new_currentLevel: the new value of currentLevel
    */
    public void set_currentLevel(String new_currentLevel){
      this.currentLevel = new_currentLevel;
    }

    public void init(){
        setState(State.RUNNING);
    }

    public void returnToGame() {
        setState(State.RUNNING);
    }

    public void pauseGame(){
        setState(State.PAUSED);
    }

    public void restartGame(){
        ui.dispose();
        getGame().setScreen(new CoffeePlumbing(getGame()));
    }
}
