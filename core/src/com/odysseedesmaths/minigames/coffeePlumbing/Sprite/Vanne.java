package com.odysseedesmaths.minigames.coffeePlumbing.Sprite;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.odysseedesmaths.minigames.coffeePlumbing.map.Tuyau;

/**
* Classe qui décrit une vanne. Cette dernière sera associé à un bouton ou heritera de l'interface permettant d'être cliquable, permettant ainsi de modifier le flux des tuyaux
*/
public class Vanne extends Sprite {
    private Tuyau tuyau;
    private Button diminuer;//moitié gauche de la vanne
    private Button augmenter;//moitié droite


    public Vanne(int x, int y, Tuyau tuyau){
        super(x,y);
        this.tuyau = tuyau;
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
}
