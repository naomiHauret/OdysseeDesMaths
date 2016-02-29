package com.odysseedesmaths.minigames.coffeePlumbing.Sprite;

/**
* Class that define a Sprite (Koffee Meter or Valve)
*/
public class Sprite{
    private int posX;
    private int posY;

    public Sprite(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
    }

    /**
    * Getter of posY
    * @return the value of posY
    */
    public int get_posY(){
      return this.posY;
    }

    /**
    * Setter of posY
    * @param new_posY: the new value of posY
    */
    public void set_posY(int new_posY){
      this.posY = new_posY;
    }

    /**
    * Getter of posX
    * @return the value of posX
    */
    public int get_posX(){
      return this.posX;
    }

    /**
    * Setter of posX
    * @param new_posX: the new value of posX
    */
    public void set_posX(int new_posX){
      this.posX = new_posX;
    }
}
