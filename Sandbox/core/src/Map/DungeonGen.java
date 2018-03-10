package Map;

/**
 * Created by Southridge on 2018-03-08.
 */
import Entities.Tile;
import com.sandbox.game.Asset;
import com.sandbox.game.Enums;

import java.util.*;

class Cell
{
    char value;
    int row, col;
    boolean visited;

    Cell(int r, int c)
    {
        row = r;
        col = c;
    }
}

class Room
{
    int width, height;
    int row, col;
    int doorR, doorC;

    Room(int w, int h)
    {
        width = w;
        height = h;
    }
}

public class DungeonGen
{
    private Cell[][] map;
    private Chunk chunk;

    private int height;
    private int width;
    private int border;

    private char path = ' ';
    private char space = 'X';
    private char roomPath = 'O';

    private static ArrayList<Room> rooms = new ArrayList<Room>();

    public DungeonGen(Chunk chunk, int size)
    {
        this.chunk = chunk;
        width = size;
        height = size;
        border = 1;
    }

    public Chunk GenerateDungeon() {

        //Fill map
        map = new Cell[height + border][width + border];

        for(int row = 0; row < height + border; row++)
        {
            for(int col = 0; col < width + border; col++)
            {
                Cell cell = new Cell(row, col);
                cell.value = space;
                map[row][col] = cell;
            }
        }

        //Carve out map
        for(int row = border; row < height; row++)
        {
            for(int col = border; col < width; col++)
            {
                map[row][col].value = space;
            }
        }

        addRooms(20);
        addDoors();
        removeEnds(50);
        return(mapToChunk());
    }

    private void addRooms(int roomsToAdd)
    {
        for(int i = 0; i < roomsToAdd; i++)
        {
            //How do you make sure the space between rooms are odd?
            //Calculate distance from sides to sides, if space is even, shift.

            //First make sure all room sizes are odd.
            int roomWidth = getRandom(5, 9);
            int roomHeight = getRandom(5, 9);

            while(roomWidth % 2 == 0)
                roomWidth = getRandom(5, 9);
            while(roomHeight % 2 == 0)
                roomHeight = getRandom(5, 9);

            Room room = new Room(roomWidth, roomHeight);
            boolean canPlaceRoom = true;

            //Make sure the rooms spawn at odd intervals
            int roomR = getRandom(border, height - room.height);
            int roomC = getRandom(border, width - room.width);

            while(roomR % 2 != 0)
                roomR = getRandom(border, height - room.height);

            while(roomC % 2 != 0)
                roomC = getRandom(border, width - room.width);

            //Check if the room can be placed according to its current dimensions
            for(int row = roomR - 1; row <= roomR + room.height; row++)
            {
                for(int col = roomC - 1; col <= roomC + room.width; col++)
                {
                    if(map[row][col].value != space)
                        canPlaceRoom = false;
                }
            }

            if(canPlaceRoom)
            {
                room.row = roomR;
                room.col = roomC;
                rooms.add(room);

                for(int row = roomR; row < roomR + room.height; row++)
                {
                    for(int col = roomC; col < roomC + room.width; col++)
                    {
                        if((row == roomR) || (row == (roomR + room.height) - 1) || (col == roomC) || (col == (roomC + room.width) - 1))
                            map[row][col].value = roomPath;
                        else
                            map[row][col].value = roomPath;
                    }
                }
            }
        }

        int startRow = 2;
        int startCol = 2;

        generatePath(startRow, startCol);
    }

    private void generatePath(int r, int c)
    {
        int[] dir = getRandomDir();

        for(int i = 0; i < dir.length; i++)
        {
            switch(dir[i])
            {
                case 1: //up
                    //check if upwards is out of bound
                    if(r - 2 <= border)
                        continue;
                    if(map[r - 2][c].value == space)
                    {
                        map[r - 2][c].value = path;
                        map[r - 1][c].value = path;
                        generatePath(r - 2, c);
                    }
                    break;

                case 2: //right
                    //check if right side is out of bound
                    if(c + 2 >= width - border)
                        continue;
                    if(map[r][c + 2].value == space)
                    {
                        map[r][c + 2].value = path;
                        map[r][c + 1].value = path;
                        generatePath(r, c + 2);
                    }
                    break;

                case 3: //down
                    //check if bottom is out of bound
                    if(r + 2 >= height - border)
                        continue;
                    if(map[r + 2][c].value == space)
                    {
                        map[r + 2][c].value = path;
                        map[r + 1][c].value = path;
                        generatePath(r + 2, c);
                    }
                    break;

                case 4: //left
                    //check if right side is out of bound
                    if(c - 2 <= border)
                        continue;
                    if(map[r][c - 2].value == space)
                    {
                        map[r][c - 2].value = path;
                        map[r][c - 1].value = path;
                        generatePath(r, c - 2);
                    }
                    break;
            }
        }
    }

    private int[] getRandomDir()
    {
        int[] dir = {1, 2, 3, 4};
        int n = dir.length;

        Random random = new Random();
        random.nextInt();

        for(int i = 0; i < n; i++)
        {
            int change = i + random.nextInt(n - i);
            int temp = dir[i];
            dir[i] = dir[change];
            dir[change] = temp;
        }

        return dir;
    }

    private void addDoors()
    {
        //add a door at every room
        for(Room r : rooms)
        {
            //loop through every tile of the room PLUS one extra layer around it
            for(int row = r.row - 1; row <= (r.row + r.height); row++)
            {
                for(int col = r.col - 1; col <= (r.col + r.width); col++)
                {
                    //doors can only be opened on the space between the path and room
                    //There can be more than one doors from each room
                    if(map[row][col].value == space)
                    {
                        if(map[row + 1][col].value == path)
                        {
                            if(map[row - 1][col].value == roomPath)
                            {
                                if(getRandom(1, 40) == 1)
                                {
                                    map[row][col].value = path;
                                }
                            }
                        }

                        if(map[row + 1][col].value == roomPath)
                        {
                            if(map[row - 1][col].value == path)
                            {
                                if(getRandom(1, 40) == 10)
                                {
                                    map[row][col].value = path;
                                }
                            }
                        }

                        if(map[row][col + 1].value == roomPath)
                        {
                            if(map[row][col - 1].value == path)
                            {
                                if(getRandom(1, 40) == 10)
                                {
                                    map[row][col].value = path;
                                }
                            }
                        }

                        if(map[row][col + 1].value == path)
                        {
                            if(map[row][col - 1].value == roomPath)
                            {
                                if(getRandom(1, 40) == 10)
                                {
                                    map[row][col].value = path;
                                }
                            }
                        }

                        if(map[row][col + 1].value == roomPath)
                        {
                            if(map[row][col - 1].value == roomPath)
                            {
                                if(getRandom(1, 40) == 10)
                                {
                                    map[row][col].value = path;
                                }
                            }
                        }

                        if(map[row + 1][col].value == roomPath)
                        {
                            if(map[row + 1][col].value == roomPath)
                            {
                                if(getRandom(1, 40) == 10)
                                {
                                    map[row][col].value = path;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void removeEnds(int iterations)
    {
        //Loop through every tile in map
        //if a path has, excluding diagonals, 3 or more neighboring spaces, fill it up.
        //check for a certain amount of times

        for(int i = 0; i < iterations; i++)
        {
            for (int r = border; r < height - border; r++)
            {
                for (int c = border; c < width - border; c++)
                {
                    if (map[r][c].value == ' ')
                    {
                        if (getSurroundingSpaces(r, c) >= 3)
                        {
                            map[r][c].value = space;
                        }
                    }
                }
            }
        }
    }

    private int getSurroundingSpaces(int r, int c)
    {
        int spaces = 0;
        int[] adjacentRows = {-1, 0, 1};
        int[] adjacentCols = {-1, 0, 1};

        for(int pos : adjacentRows)
        {
            if(map[r + pos][c].value == space)
                spaces++;
        }

        for(int pos : adjacentCols)
        {
            if(map[r][c + pos].value == space)
                spaces++;
        }

        //System.out.println(spaces);
        return spaces;
    }

    private int getRandom(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    private Chunk mapToChunk()
    {
        for(int r = 0; r < height; r++)
        {
            for(int c = 0; c < width; c++)
            {
                Tile t;

                if(map[r][c].value == space)
                {
                    t = new Tile(r, c, 8, Enums.tileType.Water, Asset.water_01);
                    chunk.tiles[r][c] = t;
                }
                else if(map[r][c].value == path || map[r][c].value == roomPath)
                {
                    t = new Tile(r, c, 8, Enums.tileType.Grass, Asset.grass_01);
                    chunk.tiles[r][c] = t;
                }
            }
        }

        return chunk;
    }

    public Tile getStartTile()
    {
        return chunk.tiles[height/2][width/2];
    }
}

