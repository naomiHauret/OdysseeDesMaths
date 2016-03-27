package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.minigames.accrobranche.Accrobranche;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;


//FIXME: doesn't take the good dimensions
public class GameChoiceMenu implements Screen {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    private OdysseeDesMaths jeu;

    private FreeTypeFontGenerator ftfg;
    private FreeTypeFontGenerator.FreeTypeFontParameter ftfp;
    private Label chooseGame;
    private Label.LabelStyle lbs;
    private Skin skin;
    private BitmapFont font;

    private Stage stage;

    private Table tableau;
    private Button arrivee_remarquable_button, accrobranche_button, coffee_button;
    //private Viewport viewport;

    public GameChoiceMenu(OdysseeDesMaths jeu){
        this.jeu = jeu;

        //this.viewport = new StretchViewport(WIDTH, HEIGHT);
        this.stage = new Stage();

        this.ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.font = new BitmapFont();
        this.lbs = new Label.LabelStyle();

        this.skin = new Skin();
        this.tableau = new Table();

        this.createGUI();
    }


    public void createGUI(){
        this.ftfp.size = HEIGHT / 5;
        this.ftfp.color = new Color(0,123f,253f,1f);
        this.ftfg = new FreeTypeFontGenerator(Assets.KENPIXEL_BLOCKS);
        this.font = ftfg.generateFont(ftfp);

        this.lbs.font = font;
        this.chooseGame = new Label("Choose your game", lbs);

        this.skin.add("arriveeRemarquableArcade", Assets.getManager().get(Assets.ARRIVEE_REMARAQUABLE_ARCADE_MACHINE, Texture.class));
        this.skin.add("accrobrancheArcade", Assets.getManager().get(Assets.ACCROBRANCHE_ARCADE_MACHINE, Texture.class));
        this.skin.add("coffeePlumbingArcade", Assets.getManager().get(Assets.COFFEE_PLUMBING_ARCADE_MACHINE, Texture.class));
        this.skin.add("background", Assets.getManager().get(Assets.ARCADE_ROOM_BACKGROUND, Texture.class));

        this.arrivee_remarquable_button = this.setArcadeButton(this.skin.getDrawable("arriveeRemarquableArcade"));
        this.accrobranche_button = this.setArcadeButton(this.skin.getDrawable("accrobrancheArcade"));
        this.coffee_button = this.setArcadeButton(this.skin.getDrawable("coffeePlumbingArcade"));
        this.setListener(new ArcadeListener());

        this.tableau.setFillParent(true);
        this.tableau.setBackground(skin.getDrawable("background"));


        this.tableau.add(chooseGame).center();
        this.tableau.row();
        this.tableau.add(arrivee_remarquable_button).center();
        this.tableau.add(accrobranche_button).center();
        this.tableau.add(coffee_button).center();

        this.stage.addActor(this.tableau);
        this.stage.setDebugAll(true);
    }

    public Button setArcadeButton(Drawable d){
        Button.ButtonStyle bts = new Button.ButtonStyle(d,d,d);
        Button arcadeButton = new Button(bts);
        arcadeButton.setSize(WIDTH/4, (HEIGHT/4));
        return arcadeButton;
    }

    public void setListener(ArcadeListener listener){
        arrivee_remarquable_button.addListener(listener);
        accrobranche_button.addListener(listener);
        coffee_button.addListener(listener);
    }

    /**
    * Getter of jeu
    * @return the value of jeu
    */
    public OdysseeDesMaths get_jeu(){
      return this.jeu;
    }

    /**
    * Setter of jeu
    * @param new_jeu: the new value of jeu
    */
    public void set_jeu(OdysseeDesMaths new_jeu){
      this.jeu = new_jeu;
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        ftfg.dispose();
        font.dispose();
    }

    private class ArcadeListener extends InputListener {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();
            if (source.equals(arrivee_remarquable_button)) {
                jeu.setScreen(new ArriveeRemarquable(jeu));

            } else if (source.equals(accrobranche_button)) {
                jeu.setScreen(new Accrobranche(jeu));

            } else if (source.equals(coffee_button)) {//jeu.setScreen(new CoffeePlumbing(jeu));

            } else {//lol nope

            }
            return true;
        }
    }
}
