package com.mdorst.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mdorst.mariobros.MarioBros;
import com.mdorst.mariobros.scenes.Hud;
import com.mdorst.mariobros.sprites.Mario;
import com.mdorst.mariobros.util.Constant;

import static sun.audio.AudioPlayer.player;

public class PlayScreen implements Screen {

    private MarioBros game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Mario mario;

    public PlayScreen(MarioBros game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constant.VIEWPORT_WIDTH / Constant.PIXELS_PER_METER,
                                   Constant.VIEWPORT_HEIGHT / Constant.PIXELS_PER_METER, camera);
        hud = new Hud(game.batch);
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constant.PIXELS_PER_METER);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize Box2D
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Create Box2D objects
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        // Iterate over layers 2-5, which are the object layers
        for (int i = 2; i <= 5; i++) {
            // Iterate over every collideable surface in the layer
            for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                // Set bodyDef position to center of object
                bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Constant.PIXELS_PER_METER,
                                     (rect.getY() + rect.getHeight() /2) / Constant.PIXELS_PER_METER);
                body = world.createBody(bodyDef);
                // Shape defined by width from center
                shape.setAsBox(rect.getWidth() / (2 * Constant.PIXELS_PER_METER),
                               rect.getHeight() / (2 * Constant.PIXELS_PER_METER));
                fixtureDef.shape = shape;
                body.createFixture(fixtureDef);
            }
        }

        // Create Mario
        mario = new Mario(world);
    }

    private void update(float deltaTime) {
        // Handle input
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            mario.body.applyLinearImpulse(new Vector2(0, Constant.Mario.JUMP_IMPULSE),
                                          mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.body.getLinearVelocity().x <= 2) {
            mario.body.setLinearVelocity(new Vector2(2, mario.body.getLinearVelocity().y));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.body.getLinearVelocity().x >= -2) {
            mario.body.setLinearVelocity(new Vector2(-2, mario.body.getLinearVelocity().y));
        }

        // Set Box2d physics poll rate
        world.step(1/60f, 6, 2);

        // Make camera follow mario
        camera.position.x = mario.body.getPosition().x;

        // Finalize
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float deltaTime) {
        // Update the game world
        update(deltaTime);

        // Do GL stuff
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the map
        renderer.render();

        // Draw the Box2D Debug lines
        debugRenderer.render(world, camera.combined);

        // Draw the hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
