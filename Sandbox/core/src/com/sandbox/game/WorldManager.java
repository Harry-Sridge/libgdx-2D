package com.sandbox.game;

import Box2D.Box2DWorld;
import Entities.Enemy;
import Entities.Entity;
import Entities.Tile;
import Map.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Collections;

/**
 * Created by zliu on 2018-03-11.
 */

public class WorldManager {

    private Box2DWorld box2D;

    World world;

    public WorldManager(Box2DWorld box2D, World world)
    {
        this.box2D = box2D;
        this.world = world;
    }

    public void drawWorld(SpriteBatch batch)
    {
        for(Tile[] tiles : world.chunk.tiles)
        {
            for(Tile tile : tiles)
            {
                batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);

                if (tile.secondaryTextures != null)
                {
                    for(Texture texture : tile.secondaryTextures)
                    {
                        batch.draw(texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
                    }
                }
            }
        }
    }

    public void drawEntities(SpriteBatch batch)
    {
        for(Entity e : world.entities)
        {
            e.Draw(batch);
        }
    }

    public void sortEntities()
    {
        Collections.sort(world.entities);
    }

    public void wakeEnemies()
    {
        for(Enemy e : world.enemies)
        {
            e.start();
        }
    }

    public void resetWorld()
    {
        world.Reset(box2D);
    }
}
