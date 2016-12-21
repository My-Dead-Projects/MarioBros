package com.mdorst.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mdorst.mariobros.util.Constant;

public class Mario extends Sprite {
    private World world;
    public Body body;

    public Mario(World world) {
        this.world = world;

        // Set up Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / Constant.PIXELS_PER_METER, 32 / Constant.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Constant.PIXELS_PER_METER);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
