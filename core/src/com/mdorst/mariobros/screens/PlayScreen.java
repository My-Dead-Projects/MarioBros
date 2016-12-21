package com.mdorst.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mdorst.mariobros.MarioBros;
import com.mdorst.mariobros.util.Constant;

public class PlayScreen implements Screen {

    private MarioBros game;
    Texture texture;
    private OrthographicCamera camera;
    private Viewport viewport;

    public PlayScreen(MarioBros game) {
        this.game = game;
        texture = new Texture("badlogic.jpg");
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constant.VIEWPORT_WIDTH, Constant.VIEWPORT_HEIGHT, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        game.batch.end();
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
