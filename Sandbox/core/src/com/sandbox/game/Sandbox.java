package com.sandbox.game;

import Box2D.Box2DWorld;
import Entities.Player;
import Map.Dungeon;
import Map.Island;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


public class Sandbox extends ApplicationAdapter {

    private SpriteBatch batch;
	private OrthographicCamera camera;
	private Control control;
	private Box2DWorld box2D;

	private int displayW;
	private int displayH;

	public Player player;

	private WorldManager islandManager;
    private Island island;
    private Dungeon dungeon;

    private Matrix4 screenMatrix;
    
    private FrameRate fps;

	@Override
	public void create ()
    {
		batch = new SpriteBatch();
        box2D = new Box2DWorld();

		displayW = Gdx.graphics.getWidth();
		displayH = Gdx.graphics.getHeight();

		int h = (int)(displayH / Math.floor(displayH / 160));
		int w = (int)(displayW / (displayH / (displayH / Math.floor(displayH / 160))));

		camera = new OrthographicCamera(w,h);
		camera.zoom = 1f;

		//Set controller
		control = new Control(displayW, displayH, camera);
		Gdx.input.setInputProcessor(control);

        Asset.Load();

<<<<<<< HEAD
        island = new Island(box2D, 50, 5);
        dungeon = new Dungeon(box2D, 20, 3, 20);
=======
        island = new Island(box2D, 100, 40);
        dungeon = new Dungeon(box2D, 100, 5, 20);
>>>>>>> 53c6ea074fadcc494e72ee9ae33a76b50f26b17d
        islandManager = new WorldManager(box2D, island);
        player = new Player(islandManager.world.GetPlayerSpawnPos(), box2D);
        reset();

        screenMatrix = new Matrix4(batch.getProjectionMatrix());
        fps = new FrameRate();
	}

	@Override
	public void render ()
    {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Pre render

        //CONTROLS
        if(control.reset)
			reset();

        if(control.inventory)
        {
            player.inventory.Print();
            control.inventory = false;
        }

		player.update(control);
        camera.position.lerp(new Vector3(player.pos.x + player.width / 2, player.pos.y + player.height / 2, 0), 0.1f);
		camera.update();

        islandManager.sortEntities();

        batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//Render
		//TODO: current draw is not efficient, maybe use occlusion culling?

		batch.begin();

		islandManager.drawWorld(batch);
		islandManager.drawEntities(batch);
        islandManager.wakeEnemies();

        //UI
        batch.setProjectionMatrix(screenMatrix);

		batch.end();

		//Post render
		box2D.tick(camera, control);
		islandManager.world.ClearRemovedEntities(box2D);
		
		fps.update();
		fps.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void reset()
    {
        islandManager.resetWorld();
        player.Reset(box2D, islandManager.world.GetPlayerSpawnPos());
        islandManager.world.entities.add(player);
        box2D.PopulateEntityMap(island.entities);
        control.reset = false;
    }
}
