package com.odysseedesmaths.minigames.coffeePlumbing.Sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.odysseedesmaths.Assets;

public class KoffeeMeter{
    private Label capacity;
    private Label currentFlow;
    private int posX;
    private int posY;
    private Table table;

    public KoffeeMeter(int x, int y){
        this.posX = x;
        this.posY = y;

        init();
    }

    public void init(){
        table = new Table();
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Assets.KENPIXEL_BLOCKS);
        FreeTypeFontGenerator.FreeTypeFontParameter ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.size = 24; //the size can be changed later
        ftfp.color = new Color(0.42f,0.64f,0.62f,1);
        BitmapFont font = ftfg.generateFont(ftfp);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font=font;

        capacity = new Label("0",style);
        currentFlow = new Label("0",style);

        table.add(currentFlow).padRight(2f).center();
        table.add(capacity).padLeft(2f).center();

        table.setPosition(posX*64+32,posY*64+32);
    }

    /**
    * Getter of currentFlow
    * @return the value of currentFlow
    */
    public Label get_currentFlow(){
      return this.currentFlow;
    }

    /**
    * Setter of currentFlow
    * @param new_currentFlow: the new value of currentFlow
    */
    public void set_currentFlow(Label new_currentFlow){
      this.currentFlow = new_currentFlow;
    }

    public void set_currentFlow(int new_currentFlow){
      this.currentFlow.setText(new_currentFlow+"");
    }

    /**
    * Getter ofcapacity
    * @return the value ofcapacity
    */
    public Label get_capacity(){
      return this.capacity;
    }

    /**
    * Setter ofcapacity
    * @param new_capacity: the new value ofcapacity
    */
    public void set_capacity(Label new_capacity){
      this.capacity = new_capacity;
    }

    public void set_capacity(int new_capacity){
        this.capacity.setText(new_capacity+"");
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

    /**
    * Getter of table
    * @return the value of table
    */
    public Table get_table(){
      return this.table;
    }

    /**
    * Setter of table
    * @param new_table: the new value of table
    */
    public void set_table(Table new_table){
      this.table = new_table;
    }
}
