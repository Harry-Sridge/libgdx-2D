package Entities;

/**
 * Created by zliu on 2018-02-16.
 */

import Box2D.Box2DWorld;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sandbox.game.Enums.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sandbox.game.Inventory;

public class Entity implements Comparable<Entity> {

    //Collision
    public Body body;

    public int hashcode;
    public Body sensor;
    public boolean removed;

    public entityType type;
    public Vector3 pos;

    public Texture texture;

    public Inventory inventory;

    public float width;
    public float height;

    float xDir = 0;
    float yDir = 0;
    float speed = 0;

    public Entity()
    {
        pos = new Vector3();
    }

    public void Draw(SpriteBatch batch)
    {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public int compareTo(Entity entity)
    {
        float tempY = entity.pos.y;
        float compareY = pos.y;
        return (tempY < compareY) ? -1 : (tempY > compareY) ? 1 : 0;
    }

    public void Collision(Entity e, boolean isInTrigger)
    {

    }

    public void Interact(Entity entity)
    {

    }

    public void RemoveBodies(Box2DWorld box2D)
    {
        if (sensor != null)
            box2D.world.destroyBody(sensor);
        if (body != null)
            box2D.world.destroyBody(body);
    }
}
