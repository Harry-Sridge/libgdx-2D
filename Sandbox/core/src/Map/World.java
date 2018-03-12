package Map;

import Box2D.Box2DHelper;
import Box2D.Box2DWorld;
import Entities.Enemy;
import Entities.Entity;
import Entities.Tile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;

/**
 * Created by zliu on 2018-03-11.
 */

public class World {

    int chunkSize;
    public Chunk chunk;
    public Tile startTile; //For player spawn reference

    public ArrayList<Entity> entities;
    public ArrayList<Enemy> enemies;

    public World()
    {
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
    }

    public void AssignTileCodes()
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

    public void GenerateColliders(Box2DWorld box2D)
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

    public Vector3 GetPlayerSpawnPos()
    {
        return startTile.pos;
    }

    public void Reset(Box2DWorld box2D)
    {

    }

    public void ClearRemovedEntities(Box2DWorld box2D)
    {

    }

    public void addEntities(Box2DWorld box2D)
    {

    }
}
