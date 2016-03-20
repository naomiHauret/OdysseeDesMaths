package com.odysseedesmaths.minigames.accrobranche;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.odysseedesmaths.Assets;

public class Hero extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD };
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion heroStand;
    private Animation heroRun;
    private TextureRegion heroJump;
    private TextureRegion heroDead;

    private float stateTimer;
    private boolean heroIsDead;
    private boolean timeToRedefineHero;

    public Hero(World world, TreeScreen screen) {
        // initialize default vamues
        this.world = world;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        /*
        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("hero"), i * 16, 0, 16, 16));
        heroRun = new Animation(0.1f, frames);

        frames.clear();
        */

        /*
        //get jump animation frames and add them to heroJump Animation
        heroJump = new TextureRegion(screen.getAtlas().findRegion("hero"), 80, 0, 16, 16);

        //create texture region for hero standing
        heroStand = new TextureRegion(screen.getAtlas().findRegion("hero"), 0, 0, 16, 16);

        //create dead hero texture region
        marioDead = new TextureRegion(screen.getAtlas().findRegion("hero"), 96, 0, 16, 16);
        */

        defineHero();

        setBounds(0, 0, 16 / TreeScreen.PPM, 16 / TreeScreen.PPM);
        //setRegion(heroStand);
        setTexture(new Texture(Assets.ICON_HERO));
    }

    public void update(float dt) {

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //setRegion(getFrame(dt));
        if(timeToRedefineHero)
            redefineHero();

    }

    /*public TextureRegion getFrame(float dt){
        //get hero current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = heroDead;
                break;
            case JUMPING:
                region = heroJump;
                break;
            case RUNNING:
                region = heroRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = heroStand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }*/

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if hero is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(heroIsDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
            //if negative in Y-Axis hero is falling
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if hero is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
            //if none of these return then he must be standing
        else
            return State.STANDING;
    }

    public void die() {

        if (!isDead()) {
            heroIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = TreeScreen.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return heroIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void jump(){
        if ( getState() != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public void runRight(){
        b2body.applyLinearImpulse(new Vector2(0.01f, 0), b2body.getWorldCenter(), true);
    }

    public void runLeft(){
        b2body.applyLinearImpulse(new Vector2(-0.01f, 0), b2body.getWorldCenter(), true);
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
        bdef.position.set(32 / TreeScreen.PPM, 32 / TreeScreen.PPM);
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
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}