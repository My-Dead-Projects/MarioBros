package com.mdorst.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mdorst.mariobros.MarioBros;
import com.mdorst.mariobros.scenes.Hud;
import com.mdorst.mariobros.util.Constant;

public class PlayScreen implements Screen {

    private MarioBros game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(MarioBros game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constant.VIEWPORT_WIDTH, Constant.VIEWPORT_HEIGHT, camera);
        hud = new Hud(game.batch);
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    private void update(float deltaTime) {
        // Handle input
        if (Gdx.input.isTouched()) {
            camera.position.x += 100 * deltaTime;
        }

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
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        // Draw the map
        renderer.render();

        // Draw the hud
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
