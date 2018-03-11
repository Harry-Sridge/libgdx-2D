package Map;

import Box2D.Box2DHelper;
import Box2D.Box2DWorld;
import Entities.Entity;
import Entities.Tile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;

/**
 * Created by Southridge on 2018-03-08.
 */
public class Dungeon {

    private DungeonGen gen;

    public Tile startTile;
    public Chunk chunk;

    public ArrayList<Entity> entities = new ArrayList<Entity>();

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

    public Vector3 GetPlayerSpawnPos()
    {
        //currently is in the middle of chunk
        return startTile.pos;
    }

    private void GenerateColliders(Box2DWorld box2D)
    {
        for(Tile[] tiles : chunk.tiles)
        {
            for(Tile tile : tiles)
            {
                //there's no need to generate hit boxes for grass tiles.
                if(!tile.isCollider())
                {
                    if(!tile.isAllWall())
                        Box2DHelper.CreateBody(box2D.world, chunk.tileSize, chunk.tileSize, 0, 0, tile.pos, BodyDef.BodyType.StaticBody);
                }
            }
        }
        System.out.println();
    }

    private void AssignTileCodes()
    {
        for(Tile[] tiles : chunk.tiles){
            for(Tile tile : tiles){

                // Check all surrounding tiles and set 1 for pass 0 for non pass
                // 0 0 0
                // 0 X 0
                // 0 0 0

                int[] rows = {-1, 0, 1};
                int[] cols = {-1, 0, 1};

                for(int r: rows){
                    for(int c: cols){
                        tile.code += chunk.GetTileCode(tile.row + r, tile.col + c);
                    }
                }
            }
        }
    }
}
