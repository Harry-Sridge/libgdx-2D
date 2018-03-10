package Map;

import Box2D.Box2DWorld;
import Entities.Entity;
import Entities.Tile;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Southridge on 2018-03-08.
 */
public class Dungeon {

    private DungeonGen gen;

    public Tile startTile;
    public Chunk dungeon;

    public ArrayList<Entity> entities = new ArrayList<Entity>();

    public Dungeon(Box2DWorld box2D, int size)
    {
        dungeon = new Chunk(size, 8);
        gen = new DungeonGen(dungeon, size);

        Reset(box2D);
    }

    //Regen
    public void Reset(Box2DWorld box2D)
    {
        //clear entities and collision data
        entities.clear();
        box2D.Clear();

        //Generate chunk
        dungeon = gen.GenerateDungeon();
        startTile = gen.getStartTile();

        //Post generation procedures
//        AssignTileCodes();
//        GenerateColliders(box2D);
//        AddEntities(box2D);
    }

    public Vector3 GetPlayerSpawnPos()
    {
        //currently is in the middle of chunk
        return startTile.pos;
    }
}
