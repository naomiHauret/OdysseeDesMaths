package com.odysseedesmaths.minigames.coffeePlumbing.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.minigames.coffeePlumbing.map.Tuyau;

/**
* Classe qui décrit une vanne. Cette dernière sera associé à un bouton ou heritera de l'interface permettant d'être cliquable, permettant ainsi de modifier le flux des tuyaux
*/
public class Vanne {
    private Tuyau tuyau;
    private Button diminuer;//moitié gauche de la vanne
    private Button augmenter;//moitié droite
    private int x;
    private int y;
    private Table table;

    public Vanne(int x, int y, Tuyau tuyau){
        this.x = x;
        this.y = y;
        this.tuyau = tuyau;
        this.diminuer = new Button();
        this.augmenter = new Button();
        this.table = new Table();
        this.init();
    }

    public void init(){
        Drawable imageButton = new NinePatchDrawable(new NinePatch(Assets.getManager().get(Assets.VANNEBUTTON, Texture.class)));
        Button.ButtonStyle bts = new Button.ButtonStyle(imageButton,imageButton,imageButton);
        augmenter.setStyle(bts);
        augmenter.setSize(32, 64);
        augmenter.addListener(new VanneButton());
        diminuer.setStyle(bts);
        diminuer.setSize(32, 64);
        diminuer.addListener(new VanneButton());

        table.add(diminuer);
        table.add(augmenter);

        table.setPosition(x*64+32,y*64+32); //les 2 (+32) sont la pour être bien aligné avec le centre de la vanne
    }

    /**
    * Getter of tuyau
    * @return the value of tuyau
    */
    public Tuyau get_tuyau(){
      return this.tuyau;
    }

    /**
    * Setter of tuyau
    * @param new_tuyau: the new value of tuyau
    */
    public void set_tuyau(Tuyau new_tuyau){
      this.tuyau = new_tuyau;
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

    public class VanneButton extends InputListener{
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();
            if(source == diminuer){
                tuyau.diminuerFlux();
            }
            else if(source == augmenter){
                tuyau.augmenterFlux();
            }
            return true;
        }
    }
}
