package com.odysseedesmaths.minigames.coffeePlumbing.Sprite;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.odysseedesmaths.minigames.coffeePlumbing.map.Tuyau;

/**
* Classe qui décrit une vanne. Cette dernière sera associé à un bouton ou heritera de l'interface permettant d'être cliquable, permettant ainsi de modifier le flux des tuyaux
*/
public class Vanne {
    private Tuyau tuyau;
    private Button diminuer;//moitié gauche de la vanne
    private Button augmenter;//moitié droite
    private Stage test;
    private int x;
    private int y;

    public Vanne(int x, int y, Tuyau tuyau){
        this.x = x;
        this.y = y;
        this.tuyau = tuyau;
        this.diminuer = new Button();
        this.augmenter = new Button();
        this.init();
    }

    public void init(){
        augmenter.addListener(new VanneButton());
        diminuer.addListener(new VanneButton());
        test = new Stage();

        Table table = new Table();
        table.add(diminuer);
        table.add(augmenter);

        table.setPosition(x * 64, y * 64);
        test.addActor(table);
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
