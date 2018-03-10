package com.sandbox.game;

import Box2D.Box2DWorld;
import Entities.Entity;
import Entities.Player;
import Entities.Tile;
import Map.Dungeon;
import Map.Island;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import java.util.Collections;

public class Sandbox extends ApplicationAdapter {

    private SpriteBatch batch;
	private OrthographicCamera camera;
	private Control control;
	private Box2DWorld box2D;

	private int displayW;
	private int displayH;

	//World
	private Island island;
	private Dungeon dungeon;

	private Player player;

	//TODO: Use a better way to spawn different worlds.

	@Override
	public void create () {

		batch = new SpriteBatch();
        box2D = new Box2DWorld();

        //Set window
		displayW = Gdx.graphics.getWidth();
		displayH = Gdx.graphics.getHeight();

		int h = (int)(displayH / Math.floor(displayH / 160));
		int w = (int)(displayW / (displayH / (displayH / Math.floor(displayH / 160))));

		camera = new OrthographicCamera(w,h);
		camera.zoom = 0.6f;

		//Set controller
		control = new Control(displayW, displayH, camera);
		Gdx.input.setInputProcessor(control);

        Asset.Load();

        island = new Island(box2D, 20, 5);
        player = new Player(island.GetPlayerSpawnPos(), box2D);
        resetWorld();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Pre render
        if(control.reset)
        {
			resetWorld();
            control.reset = false;
        }

        if(control.inventory)
        {
            player.inventory.Print();
            control.inventory = false;
        }

		player.update(control);
        camera.position.lerp(player.pos, 0.1f);
		camera.update();

        Collections.sort(island.entities);
        //Collections.sort(dungeon.entities);

        batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//Render
		//TODO: maybe draw tiles differently from entities?
		//TODO: Add a better draw function
		batch.begin();

        // Draw all tiles in the chunk
        for(Tile[] tiles : island.chunk.tiles)
        {
            for(Tile tile : tiles)
            {
                batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
                if (tile.secondaryTextures != null)
				{
				    for(Texture texture : tile.secondaryTextures)
						batch.draw(texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
				}
            }
        }

        //Draw entities on island
        for(Entity e : island.entities)
            e.Draw(batch);

		batch.end();

		//Post render
		box2D.tick(camera, control);

		island.ClearRemovedEntities(box2D);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void resetWorld()
    {
        island = new Island(box2D, 50, 15);
        player.Reset(box2D, island.GetPlayerSpawnPos());
        island.entities.add(player);
        box2D.PopulateEntityMap(island.entities);

//            player.Reset(box2D, dungeon.GetPlayerSpawnPos());
//            dungeon = new dungeon(box2D, 40);
//            dungeon.entities.add(player);
//            box2D.PopulateEntityMap(dungeon.entities);
    }
}
