package Map;

/**
 * Created by zliu on 2018-02-16.
 */

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;

import Box2D.Box2DWorld;
import Entities.Entity;
import Entities.Tile;
import Entities.Tree;

public class Island extends World{

    private IslandGen islandGen;

    public Island(Box2DWorld box2D, int islandSize, int iterations)
    {
        super();
        chunkSize = islandSize;
        chunk = new Chunk(islandSize, 8);
        islandGen = new IslandGen(islandSize, chunk.tileSize, iterations);
        Reset(box2D);
    }

    public void Reset(Box2DWorld box2D)
    {
        //clear entities and collision data
        entities.clear();
        box2D.Clear();

        //Generate chunk
        chunk = islandGen.GenerateIsland();
        startTile = islandGen.GetCentreTile();

        //Post generation procedures
        AssignTileCodes();
        GenerateColliders(box2D);
        addEntities(box2D);
    }


    @Override
    public void ClearRemovedEntities(Box2DWorld box2D)
    {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext())
        {
            Entity e = it.next();
            if(e.removed)
            {
                e.RemoveBodies(box2D);
                box2D.RemoveEntityFromMap(e);
                it.remove();
            }
        }
    }

    //TODO: Maybe add a more elaborate way to populate the island?
    @Override
    public void addEntities(Box2DWorld box2D)
    {
        for(Tile[] tiles: chunk.tiles)
        {
            for(Tile tile : tiles)
            {
                if(tile.isPath())
                {
                    if(MathUtils.random(100) > 90)
                    {
                        if(!tile.occupied)
                        {
                            entities.add(new Tree(tile.pos, box2D));
                            tile.occupied = true;
                        }
                    }
                }
            }
        }
    }
}
