package Entities;

import Box2D.Box2DHelper;
import Box2D.Box2DWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Created by Southridge on 2018-03-03.
 */

public class StaticObject extends Entity{

    public StaticObject(int size, Texture texture, Vector3 pos, Box2DWorld box2D)
    {
        super();
        width = size;
        height = size;
        this.pos = pos;
        this.texture = texture;
        body = Box2DHelper.CreateBody(box2D.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);

        //Since this is only a static object, it is not going to contain a interact method as they differ
        //according to the object. It is not wise to generalize the interact method as different objects
        //interact differently. An interactive object will have their own class.
    }
}
