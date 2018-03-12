package Map;

import Entities.Tile;
import com.sandbox.game.Asset;
import com.sandbox.game.Enums;

import java.util.ArrayList;
import java.util.List;

public class IslandGen {


    private Chunk map;
    private int chunkSize;
    private int tileSize;
    private int iterations;
    private int centerRow;
    private int centerCol;

    public IslandGen(int chunkSize, int tileSize, int iterations)
    {
        map = new Chunk(chunkSize, tileSize);
        this.chunkSize = chunkSize;
        this.tileSize = tileSize;
        this.iterations = iterations;
    }

    public Chunk GenerateIsland()
    {
        //get reference positions
        centerRow = (int)(chunkSize / 2);
        centerCol = (int)(chunkSize / 2);

        // Loop through the chunk and add tiles
        for(int row = 0; row < chunkSize; row ++)
        {
            for(int col = 0; col < chunkSize; col ++)
            {
                //Wall - 0
                //Path - 1
                //default tile is water
                Tile tile = new Tile(col, row, tileSize, Enums.tileType.Wall, Asset.GetRandomWaterTexture());
                map.tiles[row][col] = tile;
            }
        }

        //Store initial seeds
        List<Tile> seeds = new ArrayList<Tile>();

        //Manually add seeds here
        //TODO: We can think of a better way to add seeds
        seeds.add(map.tiles[centerRow][centerCol]);
        seeds.add(map.tiles[centerRow+5][centerCol]);
        seeds.add(map.tiles[centerRow-9][centerCol]);

        //Generate map using seeds
        return(SmoothMap(seeds, iterations));
    }

    private Chunk SmoothMap (List<Tile> seeds, int iterations)
    {
        //Store the newest seeds (the ones that had just been generated)
        //to avoid repeatedly checking older tiles
        List<Tile> nextSeeds = new ArrayList<Tile>();
        List<Tile> tempSeeds = new ArrayList<Tile>();
        //Add first seed to list
        for(Tile t : seeds)
            nextSeeds.add(t);

        for(int i = 0; i < iterations; i++)
        {
            //System.out.println("Iteration " + i);
            for(int j = 0; j < nextSeeds.size(); j++)
            {
                //current seed
                Tile seed = nextSeeds.get(j);
                //get max possible spread from current seed
                List<Tile> freeTiles = GetFreeTiles(seed.row, seed.col);

                int spread = 0;
                for(Tile t : freeTiles)
                {
                    //Each free water tile surrounding the current seed tile
                    //will have a 50% of becoming a grass tile.
                    if(Math.random() > 0.5f)
                    {
                        t.type = Enums.tileType.Path;
                        t.texture = Asset.GetRandomGrassTexture();
                        map.tiles[t.row][t.col] = t;
                        //the new grass tile is then will become next batch of seeds.
                        tempSeeds.add(t);
                        spread++;
                    }
                }

                //If the current seed does not spread, keep that seed.
                if(spread == 0)
                    tempSeeds.add(nextSeeds.get(j));
            }
            //Update seed queue
            nextSeeds.clear();
            for(Tile t : tempSeeds)
                nextSeeds.add(t);
            tempSeeds.clear();
        }

        return map;
    }

    private List<Tile> GetFreeTiles(int r, int c)
    {
        int[] adjacentRows = {-1, 0, 1};
        int[] adjacentCols = {-1, 0, 1};
        List<Tile> freeTiles = new ArrayList<Tile>();

        //check adjacent row and column separately; we do not want seeds to spread diagonally.
        //first check up and down
        //are the tiles above and below free?
        for(int row : adjacentRows)
        {
            if(r+row >= 0)
            {
                if(r+row < chunkSize)
                {
                    if(map.tiles[r+row][c].type == Enums.tileType.Wall)
                        freeTiles.add(map.tiles[r+row][c]);
                }
            }
        }

        //Check left and right
        //are the tiles on both sides free?
        for(int col : adjacentCols)
        {
            if(c+col >= 0)
            {
                if(c+col < chunkSize)
                {
                    if(map.tiles[r][c+col].type == Enums.tileType.Wall)
                        freeTiles.add(map.tiles[r][c+col]);
                }
            }
        }

        return freeTiles;
    }


    public Tile GetCentreTile()
    {
        return map.tiles[centerRow][centerCol];
    }
}

