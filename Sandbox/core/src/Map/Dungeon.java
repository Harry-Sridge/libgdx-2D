package Map;

import Box2D.Box2DWorld;
import Entities.Entity;
import java.util.Iterator;

/**
 * Created by zliu on 2018-03-08.
 */

public class Dungeon extends World{

    private DungeonGen gen;

    public Dungeon(Box2DWorld box2D, int size, int rooms, int smoothIterations)
    {
        chunk = new Chunk(size, 8);
        gen = new DungeonGen(chunk, size, rooms, smoothIterations);
        Reset(box2D);
    }

    //Regen
    public void Reset(Box2DWorld box2D)
    {
        //clear entities and collision data
        entities.clear();
        box2D.Clear();

        //Generate chunk
        chunk = gen.GenerateDungeon();
        startTile = gen.getStartTile();

        //Post generation procedures
        AssignTileCodes();
        GenerateColliders(box2D);
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
}
