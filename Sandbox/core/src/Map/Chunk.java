package Map;

import Entities.Tile;

/**
 * Created by zliu on 2018-02-16.
 */

//Stores an array of tiles
public class Chunk {

    //Size of chunk
    private int size;

    //Size of tiles.
    //Defaults to 8x8, the size of the sprites.
    int tileSize;

    public Tile[][] tiles;

    public Chunk(int size, int tileSize)
    {
        this.size = size;
        this.tileSize = tileSize;
        tiles = new Tile[size][size];
    }

    public String GetTileCode(int r, int c)
    {
        if(r >= 0 && c >= 0)
        {
            if(r < size && c < size)
                return tiles[r][c].isPath()? "1" : "0";
            else
                return "0";
        }
        else
            return "0";
    }
}
