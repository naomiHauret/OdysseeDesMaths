package com.odysseedesmaths.minigames.accrobranche;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Hero extends Sprite {
    private static int SIZE_HERO = 26;

    public enum State { FALLING, JUMPING, STANDING, RUNNING };

    public World world;
    public Body b2body;

    private Texture heroStand;
    private Texture heroJump;

    private boolean timeToRedefineHero;

    public Hero(World world, TreeScreen screen) {
        super(new Texture("accrobranche/hero_stand.png"));

        // initialize default vamues
        this.world = world;

        defineHero();

        setSize(SIZE_HERO / TreeScreen.PPM, SIZE_HERO / TreeScreen.PPM);

        heroStand = new Texture("accrobranche/hero_stand.png");
        heroJump = new Texture("accrobranche/hero_jump.png");
    }

    public void update(float dt) {

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if(timeToRedefineHero)
            redefineHero();

    }

    public State getState(){
        if(b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void jump(TreeScreen.Ground ground){
        switch (ground) {
            case STANDARD :
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
                break;
            default :
                System.out.println("Erreur : type de sol inconnu.");
                break;
        }
    }

    public void runRight(TreeScreen.Ground ground){
        switch (ground) {
            case STANDARD :
                b2body.applyLinearImpulse(new Vector2(0.1f, 0), b2body.getWorldCenter(), true);
                break;
            default :
                System.out.println("Erreur : type de sol inconnu.");
                break;
        }
    }

    public void runLeft(TreeScreen.Ground ground){
        switch (ground) {
            case STANDARD :
                b2body.applyLinearImpulse(new Vector2(-0.1f, 0), b2body.getWorldCenter(), true);
                break;
            default :
                System.out.println("Erreur : type de sol inconnu.");
                break;
        }
    }

    public void redefineHero(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / TreeScreen.PPM);
        fdef.filter.categoryBits = TreeScreen.HERO_BIT;
        fdef.filter.maskBits = TreeScreen.GROUND_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / TreeScreen.PPM, 6 / TreeScreen.PPM), new Vector2(2 / TreeScreen.PPM, 6 / TreeScreen.PPM));
        fdef.filter.categoryBits = TreeScreen.HERO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefineHero = false;

    }

    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / TreeScreen.PPM, 32 / TreeScreen.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(SIZE_HERO / 2 / TreeScreen.PPM);
        fdef.filter.categoryBits = TreeScreen.HERO_BIT;
        fdef.filter.maskBits = TreeScreen.GROUND_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / TreeScreen.PPM, 6 / TreeScreen.PPM), new Vector2(2 / TreeScreen.PPM, 6 / TreeScreen.PPM));
        fdef.filter.categoryBits = TreeScreen.HERO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}