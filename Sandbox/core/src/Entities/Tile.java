package Entities;

/**
 * Created by zliu on 2018-02-16.
 */

import com.sandbox.game.Enums.tileType;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Tile extends Entity{

    //tile size
    public int size;

    //tile position in array
    public int row;
    public int col;

    //general tile information
    public String code = "";
    public Texture texture;
    public List<Texture> secondaryTextures = new ArrayList<Texture>();
    public tileType type;

    public boolean occupied;

    public Tile(float row, float col, int size, tileType type, Texture texture)
    {
        super();

        pos.x = row*size;
        pos.y = col*size;

        this.size = size;
        this.type = type;
        this.texture = texture;

        this.col = (int)row;
        this.row = (int)col;
    }

    public boolean isPath() {
        return type == tileType.Path;
    }

    public boolean isWall() {
        return type == tileType.Wall;
    }

    public boolean isCliff() {
        return type == tileType.Cliff;
    }

    public boolean isAllWall()
    {
        return (code.equals("000000000"));
    }

    public boolean isCollider() {
        return (!isWall() && !isCliff());
    }
}
